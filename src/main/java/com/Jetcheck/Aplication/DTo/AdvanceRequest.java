package com.Jetcheck.Aplication.DTo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceRequest {
    String tittle;
    String description;
    List<FileResponse> rubric;
    String routeId;
    String advanceId;
}
