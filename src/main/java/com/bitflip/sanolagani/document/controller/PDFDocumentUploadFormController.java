package com.bitflip.sanolagani.document.controller;

import com.bitflip.sanolagani.document.models.Documents;
import com.bitflip.sanolagani.document.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;

@Controller
public class PDFDocumentUploadFormController {

    @Autowired
    StorageService storageService;

    @GetMapping("/upload-document")
    public String uploadDocumentForm(){
        return "documents/upload-document";
    }

    @PostMapping("/upload-document")
    public String uploadDocument(HttpServletRequest request, Documents document) throws IOException {
       // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String companyName = "Test Company";
        storageService.uploadDocument(request, companyName, document);
        return "/";
    }
}
