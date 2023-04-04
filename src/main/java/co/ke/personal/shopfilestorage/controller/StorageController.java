package co.ke.personal.shopfilestorage.controller;

import co.ke.personal.shopfilestorage.model.Image;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.ke.personal.shopfilestorage.service.StorageService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/storage")
public record StorageController(StorageService storageService) {

    @PostMapping("/upload")
    public ResponseEntity<Image[]> uploadFile(@RequestParam(value = "files") MultipartFile[] files) {
        return new ResponseEntity(storageService.uploadFile(files), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.deleteFile(fileName), HttpStatus.OK);
    }
}
