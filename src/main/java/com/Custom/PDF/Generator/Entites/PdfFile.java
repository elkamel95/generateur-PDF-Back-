package com.Custom.PDF.Generator.Entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PdfFile {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id ;
    String nameFile;
    String originName;


}
