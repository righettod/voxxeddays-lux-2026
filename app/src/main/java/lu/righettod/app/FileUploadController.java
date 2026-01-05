package lu.righettod.app;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileUploadController {

    @PostMapping(consumes = "application/json", produces = "application/json", value = "/files")
    public ResponseEntity<String> upload(@RequestBody FileInformation fileInformation) {
        return ResponseEntity.ok("User registered successfully");
    }
}
