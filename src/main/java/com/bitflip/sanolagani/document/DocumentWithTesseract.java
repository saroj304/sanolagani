package com.bitflip.sanolagani.document;


import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
public class DocumentWithTesseract {
    File image;
    BufferedImage ipimage;
    BufferedImage processed_image;
    String output_path = "src/main/resources/output";
    String lang = "eng";
    protected Tesseract tesseract;

    public DocumentWithTesseract(String image_path, String output_path, String lang) {
        this.image = new File(image_path);
        this.output_path = output_path;
        this.lang = lang;
        this.tesseract = new Tesseract();
        this.tesseract.setDatapath("src/main/resources/tessdata/");
        this.tesseract.setLanguage(lang);

    }
    public DocumentWithTesseract(String image_path, String output_path) {
        this.image = new File(image_path);

        System.out.println(image_path);

        System.out.println(image_path);

        this.tesseract = new Tesseract();
        this.output_path = output_path;
        this.tesseract.setDatapath("src/main/resources/tessdata/");
        this.tesseract.setLanguage(lang);

    }

    public void convertPdfPageToImage(){

    }


    public void read(File imageFile) throws IllegalArgumentException, IOException{
    	System.out.println("up to here is ok");
    	System.out.println(imageFile);
        this.ipimage= ImageIO.read(imageFile);
    }

    public void read() throws IllegalArgumentException, IOException{
        this.ipimage= ImageIO.read(this.image);

    }

    public BufferedImage processImg(BufferedImage ipimage,
                           float scaleFactor,
                           float offset) throws IOException, TesseractException
    {

        // Making an empty image buffer
        // to store image later
        // ipimage is an image buffer
        // of input image
        BufferedImage opimage = new BufferedImage(1050, 1024, ipimage.getType());

        // creating a 2D platform
        // on the buffer image
        // for drawing the new image
        Graphics2D graphic = opimage.createGraphics();

        // drawing new image starting from 0 0
        // of size 1050 x 1024 (zoomed images)

        
        graphic.drawImage(ipimage, 0, 0, 1050, 1024, null);
        graphic.dispose();


        // rescale OP object
        // for gray scaling images
        RescaleOp rescale = new RescaleOp(scaleFactor, offset, null);

        // performing scaling
        // and writing on a .png file
        BufferedImage fopimage = rescale.filter(opimage, null);
        ImageIO.write(fopimage, String.valueOf(this.ipimage.getType()),
                        new File("src/main/resources/images/scaled/scaled_"+ this.image.getName()));

        // rescale OP object for gray scaling images
      
        File scaledImage = new File("src/main/resources/images/scaled/scaled_"+this.image.getName());
        System.out.println(scaledImage);
        boolean scaleImageCreationStatus=ImageIO.write(fopimage, "png", scaledImage);
        System.out.println("Scale image creation status: "+ scaleImageCreationStatus);

        return fopimage;

    }

    public BufferedImage scaleImage() throws TesseractException, IOException {

        // getting RGB content of the whole image file
        double d = this.ipimage.getRGB(ipimage.getTileWidth() / 2, ipimage.getTileHeight() / 2);

        // comparing the values
        // and setting new scaling values
        // that are later on used by RescaleOP
        if (d >= -1.4211511E7 && d < -7254228) {
            this.processed_image = processImg(this.ipimage, 3f, -10f);
        }
        else if (d >= -7254228 && d < -2171170) {
            this.processed_image = processImg(this.ipimage, 1.455f, -47f);
        }
        else if (d >= -2171170 && d < -1907998) {
            this.processed_image = processImg(this.ipimage, 1.35f, -10f);
        }
        else if (d >= -1907998 && d < -257) {
            this.processed_image = processImg(this.ipimage, 1.19f, 0.5f);
        }
        else if (d >= -257 && d < -1) {
            this.processed_image = processImg(this.ipimage, 1f, 0.5f);
        }
        else if (d >= -1 && d < 2) {
            this.processed_image = processImg(ipimage, 1f, 0.35f);
        }
        return this.processed_image;


//        TODO: Find a way to implement a low pass filter to smooth the text in the image file
    }


//        TODO: Find a way to implement a low pass filter to smooth the text in the image file

    

    public String extractText() throws IOException, TesseractException {
        // doing OCR on the image
        // and storing result in string str

        return this.tesseract.doOCR(this.image);
    }


//    public String processImageAndExtractText() throws TesseractException {
//        // doing OCR on the image
//        // and storing result in string str
//
//        return this.tesseract.doOCR(this.image);

    public String processImageAndExtractText() throws TesseractException, IOException {
        // doing OCR on the image
        // and storing result in string str
        BufferedImage processedImage = scaleImage();
        return this.tesseract.doOCR(processedImage);

    }


    public void writeToFile(boolean processImage) throws IOException, TesseractException {
        // doing OCR on the image
        // and storing result in string str
        System.out.println("Inititating the process");
        List<String> lines;
        if(processImage) {
            System.out.println("Preprocessing on the image file");
            lines = Arrays.asList(processImageAndExtractText().split("/n"));
        }
        else {
            lines = Arrays.asList(extractText().split("/n"));
        }

        System.out.println("Obtained image of type: "+ this.ipimage.getType());

        System.out.println("Obtained image of type: "+ this.ipimage.getType());

        // Write the lines to the file using the Files class
        Files.write(Paths.get(this.output_path +
                this.image.getName()
                .replace(".png",".txt")), lines);
        System.out.println("Writing into the files completed.");
    }


    public static void main(String[] args) throws Exception {

        String imageName = "img_7.png";
        String pathDir = "src/main/resources/images/";
        String imagePath = pathDir + imageName;

//        "src/main/resources/output/SBL Q4 Report 3 August 2022_2/1.png"
        DocumentWithTesseract document = new DocumentWithTesseract(
                imagePath,
                "src/main/resources/output/"
        );
        document.read();
        document.writeToFile(true);

    }

}
