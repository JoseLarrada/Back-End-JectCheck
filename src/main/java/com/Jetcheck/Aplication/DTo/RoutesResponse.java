package com.Jetcheck.Aplication.DTo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoutesResponse {
    private String title;
    private String area;
    private int state;
    private String description;
    private String firstsMember;
    private String secondMember;
    private String thirdMember;
}
