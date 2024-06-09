package com.Jetcheck.Aplication.DTo.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateRequest {
    private String idRate;
    private Double rateValue;
    private String comment;
    private String idAssignment;
}
