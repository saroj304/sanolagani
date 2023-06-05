package com.bitflip.sanolagani.document.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ExtractTablesFromPDFImpl implements ExtractTablesFromPDF {
    String path;
    String documentName;
    String tsvDirectoryPath = null;
    PDDocument doc = null;
    SpreadsheetExtractionAlgorithm sea = null;

//    @Autowired IExtractTablesFromPDF extractTablesFromPDF;
    public ExtractTablesFromPDFImpl(){}
    public ExtractTablesFromPDFImpl(String path) throws IOException {
        this.path = path;
        this.documentName = new File(path).getName().split("\\.")[0];
        this.tsvDirectoryPath = "src/main/resources/output/" + this.documentName+"/";
        this.doc = Loader.loadPDF(new File(this.path));
        File opdir = new File(this.tsvDirectoryPath);
        boolean directoryCreated = opdir.mkdir();

        this.sea = new SpreadsheetExtractionAlgorithm();

    }

    public void extractAllTables() throws IOException {
        StringBuilder text = new StringBuilder();
        PageIterator it = new ObjectExtractor(this.doc).extract();
        int i = 0;

        while (it.hasNext()) {
            Page page = it.next();
            List<Table> tableList = this.sea.extract(page);
            ++i;

//            Iterate over the tables in a page
            for(Table table: tableList){
//                Iterate over the rows in a table
                for(List<RectangularTextContainer> rows: table.getRows()){
//                    Iterate over the cells in a table
                    for(RectangularTextContainer cells: rows){
                        String value = cells.getText().replace("\r", " ").strip();
                        if(value==null){
                            value = "-";
                        }
                        text.append(value).append("\t");
                    }
                    text.append("\n");

                }

                Files.write(Paths.get(tsvDirectoryPath+"table" + i+".tsv"), text.toString().getBytes());
                text = new StringBuilder();
            }
        }
    }
}
