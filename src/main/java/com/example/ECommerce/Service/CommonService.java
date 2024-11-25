package com.example.ECommerce.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface CommonService {
    void removeSessionMessage();
    void saveImage(String path, MultipartFile file) throws IOException;
}
