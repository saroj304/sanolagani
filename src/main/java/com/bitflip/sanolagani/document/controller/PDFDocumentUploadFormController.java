package com.bitflip.sanolagani.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PDFDocumentUploadFormController {
    @GetMapping("/upload-document")
    public String uploadDocument(){
        return "documents/upload-document";
    }
}
