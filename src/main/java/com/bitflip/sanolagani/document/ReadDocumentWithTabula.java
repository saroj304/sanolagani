package com.bitflip.sanolagani.document;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.multipart.MultipartFile;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadDocumentWithTabula {
    PDDocument doc = null;
    public ReadDocumentWithTabula(String filePath) throws IOException {
         this.doc = Loader.loadPDF(new File(filePath));

    }

    public void getAllTables(String filePath) throws IOException {
        StringBuilder text= new StringBuilder();

        String tsvDirectoryPath = "src/main/resources/files/";

        File opdir = new File(tsvDirectoryPath);
        boolean directoryCreated = opdir.mkdir();

        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
        PageIterator it = new ObjectExtractor(this.doc).extract();

        int i = 0;
        while (it.hasNext()) {
            Page page = it.next();
            List<Table> tableList = sea.extract(page);
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
                        text.append(value);
                        text.append("\t");
                    }
                    text.append("\n");

                }
                Files.write(Paths.get(tsvDirectoryPath+"table" + i+".tsv"), text.toString().getBytes());
                text = new StringBuilder();
            }
        }
    }
}
