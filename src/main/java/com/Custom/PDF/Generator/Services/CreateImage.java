package com.Custom.PDF.Generator.Services;
import java.io.*;

import com.Custom.PDF.Generator.Controller.FileController;
import com.Custom.PDF.Generator.Model.ConfigImage;
import com.Custom.PDF.Generator.exception.MyFileNotFoundException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


public class CreateImage {
    @Autowired
    private FileStorageService fileStorageService;

    public  CreateImage () {

    }
    public PDDocument CreateDoc(ConfigImage config, PDDocument doc, MultipartFile image) throws IOException {


        PDPage page ;

        //Retrieving the page
        try {
            page= doc.getPage(config.getPageNumber());


        PDImageXObject  pdImage = PDImageXObject.createFromByteArray(doc,image.getBytes(),image.getName());
        PDPageContentStream contents = new PDPageContentStream(doc, page,true,false);
                 contents.drawImage(pdImage, config.getX(), config.getY(),
                 config.getW()!=null?config.getW():pdImage.getWidth()
                ,config.getH()!=null?config.getH():pdImage.getHeight());
        System.out.println(pdImage.getWidth());
        System.out.println(pdImage.getHeight());

        System.out.println("Image inserted");

        //Closing the PDPageContentStream object
        contents.close();
        }catch (Exception ex) {
           new MyFileNotFoundException("Page number not found");
            System.out.println("Page number not found");
         }
        //Saving the document






return doc;



    }

}