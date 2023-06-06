package com.bitflip.sanolagani.document.service;

import com.bitflip.sanolagani.document.ReadDocumentWithTabula;
import com.bitflip.sanolagani.document.model.Document;
import com.bitflip.sanolagani.document.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    FileRepository fileRepository;
    @Override
    public void uploadDocument(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Document document = new Document();
        String filePath = "src/main/resources/documents/";
        File dir = new File(filePath+fileName.split("\\.")[0]+"/");
        dir.mkdir();
        document.setFilePath(dir.getPath()+"/"+ file.getOriginalFilename());
        document.setCompanyName("ABC company");

        try {
            byte[] b = file.getBytes();
            Path path = Paths.get(dir.getPath());
            Files.write(Path.of(dir.getPath() +"/"+ file.getOriginalFilename()), b);

            ReadDocumentWithTabula readDocumentWithTabula =
                    new ReadDocumentWithTabula(dir.getPath() +"/"+ file.getOriginalFilename());
            readDocumentWithTabula.getAllTables();
        }catch (IOException e){
            System.out.println("An error occured");
        }
//        fileRepository.save(document);
    }

    @Override
    public void getItem(String fileName) {

    }
}
