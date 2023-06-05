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

    public ReadDocumentWithPDFBox(String url) throws IOException {
        this.outputPath = "src/main/resources/images/";
        this.url = url;
        PDDocument document = Loader.loadPDF(new File(url));
        File opdir = new File("src/main/resources/output/" + new File(url).getName());
        opdir.mkdir();
    }

    public void write(PDDocument document) throws IOException {
        String text = new PDFTextStripper().getText(document);

        Files.write(Paths.get(".txt"), text.getBytes());
    }
}