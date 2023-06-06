package com.bitflip.sanolagani.document.controller;

import com.bitflip.sanolagani.document.service.StorageService;
import com.bitflip.sanolagani.document.service.StorageServiceImpl;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class PDFDocumentUploadFormController {
    @Autowired
    StorageService storageService;

    @GetMapping("/upload-document")
    public String uploadDocumentForm(){
        return "documents/upload-document";
    }

    @PostMapping("/upload-document")
    public String uploadDocument(@RequestParam(name = "document") MultipartFile document){
        storageService.uploadDocument(document);
        return "index";
    }
}
