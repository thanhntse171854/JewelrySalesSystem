package com.swp391.JewelrySalesSystem.service.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.swp391.JewelrySalesSystem.service.DocumentService;
import java.io.ByteArrayOutputStream;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {
  @Override
  @SneakyThrows
  public byte[] htmlToPdf(String processedHtml) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
      DefaultFontProvider defaultFontProvider = new DefaultFontProvider(false, true, false);

      ConverterProperties converterProperties = new ConverterProperties();
      converterProperties.setFontProvider(defaultFontProvider);

      HtmlConverter.convertToPdf(processedHtml, pdfWriter, converterProperties);

      return byteArrayOutputStream.toByteArray();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
