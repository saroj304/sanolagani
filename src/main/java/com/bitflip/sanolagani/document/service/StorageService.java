package com.bitflip.sanolagani.document.service;

import com.bitflip.sanolagani.document.models.Documents;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface StorageService {
    public void uploadDocument(HttpServletRequest request, String companyName, Documents document) throws IOException;
}
