package com.bitflip.sanolagani.document;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDF2PNG {
    String fileName;
    String fileExtension;

    public PDF2PNG(String fileName, String fileExtension) {
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }

    private void generateImageFromPDF() throws IOException {
        PDDocument document = Loader.loadPDF(new File(this.fileName));
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(
                    page, 300, ImageType.RGB);
            ImageIOUtil.writeImage(
                    bim, String.format("src/main/resources/output/NRB/page-%d.%s", page + 1, this.fileExtension), 300);
        }
        document.close();
    }

    public static void main(String[] args) {
<<<<<<< HEAD
        PDF2PNG converter = new PDF2PNG("src/main/java/sanolagani/documents/NRB.pdf", "png");
=======
        PDF2PNG converter = new PDF2PNG("documents/NRB.pdf", "png");
>>>>>>> 8a7b388a6ae8b3d8640cc12480d406e99105cbeb
        try {
            converter.generateImageFromPDF();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
