package com.Jetcheck.Aplication.DTo.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GeneralReportResponse {
    private String idRoute;
    private String state;
    private String title;
    private String description;
    private String nameTeacher;
    private String nameOwner;
    private String nameFirtsIntegrant;
    private String nameSeconIntegrant;
    private String facultly;
    private String area;
}
