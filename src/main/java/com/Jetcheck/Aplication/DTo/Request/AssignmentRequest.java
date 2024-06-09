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
public class AssignmentRequest {
    String idAdvance;
    String comment;
    String idAssignment;
    List<FileResponse> files;
}
