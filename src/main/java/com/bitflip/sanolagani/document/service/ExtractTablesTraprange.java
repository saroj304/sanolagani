package com.bitflip.sanolagani.document.service;

import com.giaybac.traprange.PDFTableExtractor;
import com.giaybac.traprange.entity.Table;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtractTablesTraprange {
    public List<Table> extractTables(){
        PDFTableExtractor extractor = new PDFTableExtractor()
                .setSource("src/main/resources/documents/10/Click Shop_lt-howden-annual-report-2021-22-lth.pdf");
        List<Table> tables = extractor.extract();
        return tables;
    }
    public void saveToFile(){}

}
