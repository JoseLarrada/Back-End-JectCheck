package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Entity.Rutas;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.thymeleaf.context.Context;

@Service
public class GeneratorReportService {
    public String htmlToPdf(String processedHtml) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream);
            DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(defaultFont);
            HtmlConverter.convertToPdf(processedHtml, pdfwriter, converterProperties);
            FileOutputStream fout = new FileOutputStream("C:/Users/Usuario/Documents/informe.pdf");
            byteArrayOutputStream.writeTo(fout);
            byteArrayOutputStream.close();
            byteArrayOutputStream.flush();
            fout.close();
            return null;
        } catch(Exception ex) {
            return ex.getMessage();
        }
    }
    public Context setData(List<Rutas> RoutesList) {

        Context context = new Context();
        Map<String, Object> data = new HashMap<>();
        data.put("Rutas", RoutesList);
        context.setVariables(data);
        return context;
    }
}
