package lu.righettod.app;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping(consumes = "application/json", produces = "application/json", value = "/files")
    public ResponseEntity<String> upload(@RequestBody FileInformation fileInformation) {
        /* Additional validations */
        try {
            additionalValidationAfterRound1(fileInformation);
            additionalValidationAfterRound2(fileInformation);
            additionalValidationAfterRound3(fileInformation);
        } catch (SecurityException e) {
            return ResponseEntity.badRequest().body("File rejected.");
        }
        /* File is accepted */
        try {
            URI fileLocation = new URI("/files/" + UUID.randomUUID());
            return ResponseEntity.created(fileLocation).body("File stored.");
        } catch (URISyntaxException e) {
            return ResponseEntity.internalServerError().build();
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
}
