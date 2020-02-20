package com.mystudy.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PictureService {

    private static String UPLOAD_PATH = "img";

    public String savePicture( MultipartFile image){
        try {
            String name = image.getOriginalFilename();

            System.out.println(name);

            InputStream inputStream = image.getInputStream();
            Path directory = Paths.get(UPLOAD_PATH);
            if(!Files.exists(directory)){
                Files.createDirectories(directory);
            }
//            long copy = Files.copy(inputStream, directory.resolve(name));

            return name;

        } catch (Exception e) {
            return null;
        }
    }

    public byte[] getPicture(String name) throws IOException {
        return Files.readAllBytes(Paths.get(UPLOAD_PATH).resolve(name));
    }
}
