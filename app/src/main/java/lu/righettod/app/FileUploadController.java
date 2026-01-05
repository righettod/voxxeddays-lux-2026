package lu.righettod.app;

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
    public ResponseEntity<String> upload(@RequestBody FileInformation fileInformation) throws URISyntaxException {
        URI fileLocation = new URI("/files/" + UUID.randomUUID());
        return ResponseEntity.created(fileLocation).body("File stored.");
    }
}
