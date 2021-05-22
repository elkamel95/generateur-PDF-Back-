package com.Custom.PDF.Generator.Controller;


import com.Custom.PDF.Generator.Entites.PdfFile;
import com.Custom.PDF.Generator.Model.ConfigImage;
import com.Custom.PDF.Generator.Model.UploadFileResponse;
import com.Custom.PDF.Generator.Services.FileStorageService;
import com.Custom.PDF.Generator.repository.PdfFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    PdfFileRepository pdfFileRepository;
     @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile( @RequestPart(value = "config") ConfigImage model,
                                          @RequestPart(value = "pdf") MultipartFile pdf
                                         ,@RequestPart(value = "image") MultipartFile image) throws IOException {


        String fileName = fileStorageService.storeFile(pdf, model,image);
                    PdfFile doc = new PdfFile();
         doc.setOriginName(fileName);

         doc.setNameFile(model.getName());
                    pdfFileRepository.save(doc);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                pdf.getContentType(), pdf.getSize());
    }
     @GetMapping("/documents")
    public List<PdfFile>  getDocuments() {
        return  pdfFileRepository.findAllByOrderByIdDesc();

    }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}