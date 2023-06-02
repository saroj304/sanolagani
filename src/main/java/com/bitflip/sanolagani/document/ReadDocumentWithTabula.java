package com.bitflip.sanolagani.document;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.tools.PDFText2HTML;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadDocumentWithTabula {
    public static void main(String[] args) throws IOException {

        PDDocument doc=null;
        StringBuilder text= new StringBuilder();
        doc = Loader.loadPDF(new File("D:\\sanolagani\\documents\\NRB.pdf"));

        File file = new File("./file.tsv");
        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
        PageIterator it = new ObjectExtractor(doc).extract();
        while (it.hasNext()) {
            Page page = it.next();
            List<Table> tableList = sea.extract(page);

//            Iterate over the tables in a page
            for(Table table: tableList){
//                Iterate over the rows in a table
                for(List<RectangularTextContainer> rows: table.getRows()){
//                    Iterate over the cells in a table
                    for(RectangularTextContainer cells: rows){
                        text.append(cells.getText().replace("\r", "").strip());
                        text.append("\t");
                    }
                    text.append("\n");
                    break;
                }
                Files.write(Paths.get("table1.tsv"), text.toString().getBytes());
            }
        }
    }
}
