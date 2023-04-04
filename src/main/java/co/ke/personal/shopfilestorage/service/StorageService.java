package co.ke.personal.shopfilestorage.service;

import co.ke.personal.shopfilestorage.model.Image;
import com.amazonaws.services.s3.AmazonS3;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

@Service
@Slf4j
public class StorageService {
    private final String bucketName;

    private AmazonS3 s3Client;
    @Autowired
    StorageService( AmazonS3 s3Client, @Value("${aws.s3.buckets.shopfilestorage.name}") String bucketName){
        this.bucketName = bucketName;
        this.s3Client = s3Client;
    }

    public List<Image> uploadFile(MultipartFile[] files) {
        log.info(String.format("Uploading %s files",String.valueOf(files.length)));
        List<Image> images =  new ArrayList<>();
        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf(".") + 1);
            String newName = RandomStringUtils.randomAlphanumeric(18) + "." + extension;
            File fileObj = convertMultiPartFileToFile(file);
            s3Client.putObject(new PutObjectRequest(bucketName, newName, fileObj));
            Image image = new Image();
            image.setName(newName);
            image.setFileType(file.getContentType());
            images.add(image);
            fileObj.delete();
        }
        return  images;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

}
