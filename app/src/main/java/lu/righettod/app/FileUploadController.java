package lu.righettod.app;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class FileUploadController {

    private static final Map<String, FileInformation> MEMORY_STORAGE = new HashMap<>();

    @GetMapping(produces = "application/json", value = "/files/{fileIdentifier}")
    public ResponseEntity<FileInformation> retrieveFile(@PathVariable String fileIdentifier) {
        if (MEMORY_STORAGE.containsKey(fileIdentifier)) {
            FileInformation data = MEMORY_STORAGE.get(fileIdentifier);
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json", value = "/files")
    public ResponseEntity<String> upload(@RequestBody FileInformation fileInformation) {
        /* Additional validations */
        try {
            additionalValidationAfterRound1(fileInformation);
            additionalValidationAfterRound2(fileInformation);
            additionalValidationAfterRound3(fileInformation);
            //Code for the round 4 triggering
            //triggeringRound4(fileInformation);
            additionalValidationAfterRound4(fileInformation);
            //additionalValidationAfterRound5(fileInformation);
            additionalValidationAfterRound6(fileInformation);
        } catch (SecurityException e) {
            return ResponseEntity.badRequest().body("File rejected.");
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().body("Code for the round 4 triggering error!");
        }

        /* File is accepted */
        try {
            String fileIdentifier = UUID.randomUUID().toString();
            URI fileLocation = new URI("/files/" + fileIdentifier);
            MEMORY_STORAGE.put(fileIdentifier, fileInformation);
            return ResponseEntity.created(fileLocation).body("File stored.");
        } catch (URISyntaxException e) {
            return ResponseEntity.internalServerError().body("URI creation error!");
        }
    }

    private void additionalValidationAfterRound1(FileInformation fileInformation) throws SecurityException {
        try {
            try (PDDocument document = Loader.loadPDF(fileInformation.getDecodedContent())) {
            }
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    private void additionalValidationAfterRound2(FileInformation fileInformation) throws SecurityException {
        try {
            try (PDDocument document = Loader.loadPDF(fileInformation.getDecodedContent())) {
                PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
                PDDocumentNameDictionary namesDictionary = new PDDocumentNameDictionary(documentCatalog);
                if (namesDictionary.getJavaScript() != null) {
                    throw new SecurityException("JS code detected!");
                }
            }
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    private void additionalValidationAfterRound3(FileInformation fileInformation) throws SecurityException {
        try {
            try (PDDocument document = Loader.loadPDF(fileInformation.getDecodedContent())) {
                PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
                PDDocumentNameDictionary namesDictionary = new PDDocumentNameDictionary(documentCatalog);
                if (namesDictionary.getEmbeddedFiles() != null) {
                    throw new SecurityException("Attached files detected!");
                }
            }
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    private void additionalValidationAfterRound4(FileInformation fileInformation) throws SecurityException {
        try {
            try (PDDocument document = Loader.loadPDF(fileInformation.getDecodedContent())) {
                PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
                PDAcroForm acroForm = documentCatalog.getAcroForm();
                boolean hasForm = (acroForm != null && acroForm.getXFA() != null);
                if (hasForm) {
                    throw new SecurityException("XFA form detected!");
                }
            }
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    private void triggeringRound4(FileInformation fileInformation) throws IllegalStateException {
        try {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();
            PDFParser pdfparser = new PDFParser();
            pdfparser.parse(new ByteArrayInputStream(fileInformation.getDecodedContent()), handler, metadata, context);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void additionalValidationAfterRound5(FileInformation fileInformation) throws SecurityException {
        final AtomicInteger invalidLinkCounter = new AtomicInteger();
        final List<String> allowedHosts = List.of("company.com");
        try {
            try (PDDocument document = Loader.loadPDF(fileInformation.getDecodedContent())) {
                PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
                documentCatalog.getPages().forEach(page -> {
                    try {
                        page.getAnnotations().forEach(pdAnnotation -> {
                            if (pdAnnotation instanceof PDAnnotationLink) {
                                PDAnnotationLink link = (PDAnnotationLink) pdAnnotation;
                                PDAction action = link.getAction();
                                if (action instanceof PDActionURI) {
                                    PDActionURI actionURI = (PDActionURI) link.getAction();
                                    URI uri = URI.create(actionURI.getURI());
                                    String actionURIHost = uri.getHost();
                                    //[!!!] Simple check for the demo: Need more advanced validation for real implementation
                                    if (!allowedHosts.contains(actionURIHost)) {
                                        invalidLinkCounter.incrementAndGet();
                                    }
                                }
                            }
                        });
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                });
                if (invalidLinkCounter.intValue() > 0) {
                    throw new SecurityException("Link with non allowed hosts detected!");
                }
            }
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    private void additionalValidationAfterRound6(FileInformation fileInformation) throws SecurityException {
        try {
            try (PDDocument document = Loader.loadPDF(fileInformation.getDecodedContent())) {
                ByteArrayOutputStream cleanedPDFBytes = new ByteArrayOutputStream();
                document.save(cleanedPDFBytes);
                String base64EncodedContent = Base64.getEncoder().encodeToString(cleanedPDFBytes.toByteArray());
                fileInformation.setBase64EncodedContent(base64EncodedContent);
            }
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

}
