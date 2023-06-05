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
    String outputPath, url, documentName;
    public void pdfToImage(PDDocument document){
        try {
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI(0, 300); // Render the first page with 300 DPI

            File outputDirectoryPath = new File(outputPath+url.split("\\.")[0]);
            outputDirectoryPath.mkdir();
            ImageIO.write(image, "png", new File(outputPath+this.documentName.split("\\.")[0]));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public ReadDocumentWithPDFBox() {
<<<<<<< HEAD
        this.inputPath = "src/main/resources/documents/";
=======
        this.inputPath = "D:/sanolagani/documents/";
>>>>>>> 8a7b388a6ae8b3d8640cc12480d406e99105cbeb
=======
    public ReadDocumentWithPDFBox(String url) throws IOException {
>>>>>>> 8cb02cfe1ef9f59cee1e34312d8903924f6730c9
        this.outputPath = "src/main/resources/images/";
        this.url = url;
        PDDocument document = Loader.loadPDF(new File(url));
        File opdir = new File("src/main/resources/output/" + new File(url).getName());
        opdir.mkdir();
    }

<<<<<<< HEAD
    public static void main(String[] args) throws IOException {
<<<<<<< HEAD
        String inputPath = "src/main/resources/documents/";
=======
        String inputPath = "sanolagani/documents/";
>>>>>>> 8a7b388a6ae8b3d8640cc12480d406e99105cbeb
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

=======
    public void write(PDDocument document) throws IOException {
        String text = new PDFTextStripper().getText(document);
>>>>>>> 8cb02cfe1ef9f59cee1e34312d8903924f6730c9

        Files.write(Paths.get(".txt"), text.getBytes());
    }
}
