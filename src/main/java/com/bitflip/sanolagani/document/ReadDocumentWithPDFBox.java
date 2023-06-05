package com.bitflip.sanolagani.document;

import ch.qos.logback.core.joran.event.BodyEvent;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.PDFText2HTML;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ReadDocumentWithPDFBox {
    String inputPath, outputPath, documentName;
    public void pdfToImage(PDDocument document){
        try {
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI(0, 300); // Render the first page with 300 DPI

            File outputDirectoryPath = new File(outputPath+documentName.split("\\.")[0]);
            outputDirectoryPath.mkdir();
            ImageIO.write(image, "png", new File(outputPath+documentName.split("\\.")[0]+"/1.png"));
            System.out.println("PDF converted to image successfully!");

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ReadDocumentWithPDFBox() {
        this.inputPath = "src/main/resources/documents/";
        this.outputPath = "src/main/resources/images/";
        this.documentName = "SBL Q4 Report 3 August 2022_2.pdf";
    }

    public static void main(String[] args) throws IOException {
        String inputPath = "src/main/resources/documents/";
//        String outputPath = "src/main/resources/images/";
        String documentName = "NRB";
        String documentExtension = ".pdf";
        String textDirectoryPath = "src/main/resources/output/" + documentName+"/";

//        Create the text output directory if doesn't exist
        File opdir = new File(textDirectoryPath);
        boolean directoryCreated = opdir.mkdir();
        System.out.println("Directory creation status: " + !directoryCreated);
        PDDocument document = Loader.loadPDF(new File(inputPath+documentName+ documentExtension));

        Files.write(Paths.get(textDirectoryPath+documentName+".html"), new PDFText2HTML().getText(document).getBytes());
//        String text = new PDFTextStripper().getText(document);

//        Files.write(Paths.get(textDirectoryPath+documentName+".txt"), text.getBytes());
        System.out.println("Writing into the files completed.");


    }

}
