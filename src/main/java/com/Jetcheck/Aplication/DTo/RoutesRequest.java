package com.Jetcheck.Aplication.DTo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutesRequest {
    String id;
    String Description;
    String Teacher;
    String NameRoute;
    String Id_Member;
    String Id_Member2;
    int idFacultly;
    int idArea;
}
