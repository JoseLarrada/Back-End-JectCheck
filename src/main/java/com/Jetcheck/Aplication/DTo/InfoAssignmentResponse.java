package com.Jetcheck.Aplication.DTo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class InfoAssignmentResponse{
    private String comment;
    private List<FileResponse> filesUpload;
    private String rateComment;
    private Double valueRate;
    private String state;
}
