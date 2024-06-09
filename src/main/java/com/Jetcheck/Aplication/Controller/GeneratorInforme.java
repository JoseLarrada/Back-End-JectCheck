package com.Jetcheck.Aplication.Controller;


import com.Jetcheck.Aplication.DTo.Response.GeneralReportResponse;
import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Repository.OtherRepository;
import com.Jetcheck.Aplication.Repository.RoutesRepository;
import com.Jetcheck.Aplication.Services.GeneratorReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/PrincipalContent")
public class GeneratorInforme {
    private final SpringTemplateEngine springTemplateEngine; // Agregado @Autowired
    private final GeneratorReportService generatorReportService;
    private final OtherRepository otherRepository;
    private final RoutesRepository routesRepository;

    @PostMapping(value = "/generate/document")
    public String generateDocument(HttpServletRequest request) {
        String finalHtml = null;
        List<GeneralReportResponse> lista = generatorReportService.listProject(request);
        Context dataContext = generatorReportService.setData(lista);
        finalHtml = springTemplateEngine.process("GeneralReport", dataContext);
        generatorReportService.htmlToPdf(finalHtml);
        return "Creado Correctamente";
    }

    @PostMapping(value = "/Generate/CustomDocument/{Id_Route}")
    public String generateCustomReport(HttpServletRequest request, @PathVariable String Id_Route) {
        GeneralReportResponse ruta=generatorReportService.convertToResponse(Id_Route);
        List<Avances> avances = otherRepository.getAvancesByRoutes(Id_Route);

        Context dataContext = new Context();
        dataContext.setVariable("ruta", ruta);
        dataContext.setVariable("avances", avances);

        String finalHtml = springTemplateEngine.process("CustomReport", dataContext);
        generatorReportService.htmlToPdf(finalHtml);
        return "Success";
    }
}


