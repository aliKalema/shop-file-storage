package co.ke.personal.shopfilestorage.service;

import co.ke.personal.shopfilestorage.model.Image;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import io.quantics.multitenant.TenantContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    public StorageService(@Value("${PublicKey}") String publicKey,
                          @Value("${PrivateKey}")String privateKey,
                          @Value("${UrlEndpoint}")String url){
        ImageKit imageKit = ImageKit.getInstance();
        Configuration config = new Configuration(publicKey, privateKey, url);
        imageKit.setConfig(config);
    }

    public List<Image> uploadFile(MultipartFile[] files) {
        log.info(String.format("Uploading %s files",String.valueOf(files.length)));
        List<Image>images = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            assert originalName != null;
            String extension = originalName.substring(originalName.lastIndexOf(".") + 1);
            String newName = String.format("%s.%s",RandomStringUtils.randomAlphanumeric(18), extension);
            try{
                FileCreateRequest fileCreateRequest = new FileCreateRequest(file.getBytes(),newName);
                fileCreateRequest.setFolder(TenantContext.getTenantId());
                Result result=ImageKit.getInstance().upload(fileCreateRequest);
                images.add(new Image(result));
            }
            catch(IOException | InternalServerException | ForbiddenException |
                  BadRequestException | UnknownException |
                  TooManyRequestsException | UnauthorizedException e){
                log.error(e.getMessage());
            }
        }
        return  images;
    }

    public void deleteFile(String fileId) {
        try{
            Result result = ImageKit.getInstance().deleteFile(fileId);
        }
        catch ( InternalServerException | ForbiddenException | TooManyRequestsException |
               UnauthorizedException | BadRequestException | UnknownException e){
            log.error(e.getMessage());
        }

    }

}
