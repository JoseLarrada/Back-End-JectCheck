package com.Jetcheck.Aplication.DTo.Request;

import com.Jetcheck.Aplication.DTo.Response.FileResponse;
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
