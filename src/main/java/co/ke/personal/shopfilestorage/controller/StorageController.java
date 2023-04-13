package co.ke.personal.shopfilestorage.controller;

import co.ke.personal.shopfilestorage.model.Image;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.ke.personal.shopfilestorage.service.StorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/storage")
public record StorageController(StorageService storageService) {

    @PostMapping("/upload")
    public ResponseEntity<List<Image>> uploadFile(@RequestParam MultipartFile[] files) {
        return new ResponseEntity<>(storageService.uploadFile(files), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<?> deleteFile(@PathVariable String fileName) {
        storageService.deleteFile(fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
