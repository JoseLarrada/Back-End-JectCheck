package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.CustomReportResponse;
import com.Jetcheck.Aplication.DTo.GeneralReportResponse;
import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Repository.OtherRepository;
import com.Jetcheck.Aplication.Repository.RateRepository;
import com.Jetcheck.Aplication.Repository.RepositoryJDBC;
import com.Jetcheck.Aplication.Repository.RoutesRepository;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class GeneratorReportService {
    private final IdGeneratorConfig idGeneratorConfig;
    private final RateRepository rateRepository;
    private final OtherRepository otherRepository;
    private final UploadContentService uploadContentService;
    private final RepositoryJDBC repositoryJDBC;
    private final RoutesRepository routesRepository;
    private final AreasService areasService;
    public String htmlToPdf(String processedHtml) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream);
            DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(defaultFont);
            HtmlConverter.convertToPdf(processedHtml, pdfwriter, converterProperties);
            String name= String.valueOf(idGeneratorConfig);
            FileOutputStream fout = new FileOutputStream("C:/Users/Usuario/Documents/informe"+name+".pdf");
            byteArrayOutputStream.writeTo(fout);
            byteArrayOutputStream.close();
            byteArrayOutputStream.flush();
            fout.close();
            return null;
        } catch(Exception ex) {
            return ex.getMessage();
        }
    }
    public Context setData(List<GeneralReportResponse> routesList) {

        Context context = new Context();
        Map<String, Object> data = new HashMap<>();
        data.put("Rutas", routesList);
        context.setVariables(data);
        return context;
    }

    public List<GeneralReportResponse> listProject(HttpServletRequest request){
        List<GeneralReportResponse> lista = new java.util.ArrayList<>(List.of());

        for (var item:uploadContentService.UploadProjects(request)){
            var dataProject=GeneralReportResponse.builder()
                    .idRoute(item.getId_ruta()).state(otherRepository.getNameState(item.getId_ruta()))
                    .title(item.getTitulo()).description(item.getDescripcion()).nameTeacher(repositoryJDBC.getFullNameUserById(item.getId_estudiante(), "profesores","id_docente"))
                    .nameOwner(repositoryJDBC.getFullNameUserById(item.getId_estudiante(), "estudiantes","id_estudiante"))
                    .nameFirtsIntegrant(repositoryJDBC.getFullNameUserById(item.getId_integrante(), "estudiantes","id_estudiante"))
                    .nameSeconIntegrant(repositoryJDBC.getFullNameUserById(item.getId_integrante2(), "estudiantes","id_estudiante"))
                    .area(areasService.getNameAreaById(item.getIdArea()))
                    .facultly(areasService.getNameFacultlyById(item.getIdFacultad()))
                    .build();
            lista.add(dataProject);
        }
        return lista;
    }
    public GeneralReportResponse convertToResponse(String Id_Route){
        Rutas ruta = routesRepository.findById(Id_Route).orElse(null);
        return GeneralReportResponse.builder()
                .idRoute(ruta.getId_ruta()).state(otherRepository.getNameState(ruta.getId_ruta()))
                .title(ruta.getTitulo()).description(ruta.getDescripcion()).nameTeacher(repositoryJDBC.getFullNameUserById(ruta.getId_docente(), "profesores","id_docente"))
                .nameOwner(repositoryJDBC.getFullNameUserById(ruta.getId_estudiante(), "estudiantes","id_estudiante"))
                .nameFirtsIntegrant(repositoryJDBC.getFullNameUserById(ruta.getId_integrante(), "estudiantes","id_estudiante"))
                .nameSeconIntegrant(repositoryJDBC.getFullNameUserById(ruta.getId_integrante2(), "estudiantes","id_estudiante"))
                .area(areasService.getNameAreaById(ruta.getIdArea()))
                .facultly(areasService.getNameFacultlyById(ruta.getIdFacultad()))
                .build();
    }
}
