package com.bitflip.sanolagani.document;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadDocumentWithTabula {
    public static void main(String[] args) throws IOException {

        PDDocument doc=null;
        StringBuilder text= new StringBuilder();

        String inputPath = "D:/sanolagani/documents/";
        String documentName = "NRB";
        String documentExtension = ".pdf";
        String tsvDirectoryPath = "src/main/resources/output/" + documentName+"/";

        doc = Loader.loadPDF(new File(inputPath+ documentName+documentExtension));
        File opdir = new File(tsvDirectoryPath);
        boolean directoryCreated = opdir.mkdir();

        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
        PageIterator it = new ObjectExtractor(doc).extract();

        int i = 0;
        while (it.hasNext()) {
            Page page = it.next();
            List<Table> tableList = sea.extract(page);
            ++i;
            int j= 0;
//            Iterate over the tables in a page
            for(Table table: tableList){
                ++j;
//                Iterate over the rows in a table
                for(List<RectangularTextContainer> rows: table.getRows()){
//                    Iterate over the cells in a table
                    for(RectangularTextContainer cells: rows){
                        text.append(cells.getText().replace("\r", "").strip());
                        text.append("\t");
                    }
                    text.append("\n");

                }

                PDDocument document = Loader.loadPDF(new File(inputPath+documentName+ documentExtension));
                Files.write(Paths.get(tsvDirectoryPath+"table" + i+".tsv"), text.toString().getBytes());
                text = new StringBuilder();
            }
            System.out.println("Found "+ j + "tables from "+ i + " page");
        }
    }
}
