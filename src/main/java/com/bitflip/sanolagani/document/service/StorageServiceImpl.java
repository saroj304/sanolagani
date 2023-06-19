package com.bitflip.sanolagani.document.service;

import com.bitflip.sanolagani.document.models.Documents;
import com.bitflip.sanolagani.document.repository.DocumentRepository;
import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.repository.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    CompanyRepo companyRepo;

    @Override
    public void uploadDocument(HttpServletRequest request, String companyName, Documents documents) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("document");
        String path = "C:\\Users\\WannaCRY\\Desktop\\sanolagani\\src\\main\\resources\\documents\\";
        String documentName = file.getOriginalFilename().replace(".pdf","");
        File dir = new File(path+documentName+"/");
        dir.mkdir();
        file.transferTo(dir);
        int companyId = documentRepository.getCompanyId(companyName);
        Company company = companyRepo.getReferenceById(companyId);
//        System.out.println(companyName);
        documents.setCompany(company);
        documents.setFilePath(dir.getPath());

    }
}
