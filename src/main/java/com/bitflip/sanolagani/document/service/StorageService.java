package com.bitflip.sanolagani.document.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    public void uploadDocument(MultipartFile file);

    public void getItem(String fileName);
}
