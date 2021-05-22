package com.Custom.PDF.Generator.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ConfigImage {
    private  String name ;
    private Integer  pageNumber ;
    private Float  x ;
    private Float  y ;
    private Float  w ;
    private Float  h ;
    private String urlImage  ;

}
