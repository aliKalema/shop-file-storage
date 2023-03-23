package co.ke.personal.shopfilestorage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.ke.personal.shopfilestorage.service.StorageService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
public record StorageController(StorageService storageService) {

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "files") MultipartFile[] files) {
        storageService.uploadFile(files);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.deleteFile(fileName), HttpStatus.OK);
    }
}
