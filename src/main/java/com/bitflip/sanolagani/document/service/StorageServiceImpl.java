package com.bitflip.sanolagani.document.service;

import com.bitflip.sanolagani.document.model.Document;
import com.bitflip.sanolagani.document.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    FileRepository fileRepository;
    @Override
    public void uploadDocument(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Document document = new Document();
        String filePath = "src/main/resources/documents";
        document.setFilePath(filePath +fileName);
        document.setCompanyName("ABC company");

        fileRepository.save(document);
    }

    @Override
    public void getItem(String fileName) {

    }
}
