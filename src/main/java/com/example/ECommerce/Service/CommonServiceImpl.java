package com.example.ECommerce.Service;

import com.example.ECommerce.Service.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public void removeSessionMessage(){
        HttpServletRequest request = ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes())))
                .getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute("successMsg");
        session.removeAttribute("errorMsg");
    }

    @Override
    public void saveImage(String path, MultipartFile file) throws IOException {
        File imagesDir = new File(path);

        if(!imagesDir.exists()){
            imagesDir.mkdirs();
        }

        // Create a path for the image file in the directory
        Path filePath = imagesDir.toPath().resolve(Objects.requireNonNull(file.getOriginalFilename()));

        // Copy the MultipartFile to the specified path on the filesystem
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Failed to upload image: " + e.getMessage());
        }

    }
}
