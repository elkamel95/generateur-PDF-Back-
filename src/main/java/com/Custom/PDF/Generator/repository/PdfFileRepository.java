package com.Custom.PDF.Generator.repository;

import com.Custom.PDF.Generator.Entites.PdfFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PdfFileRepository extends JpaRepository<PdfFile,Long> {
     public List<PdfFile> findAllByOrderByIdDesc();

}
