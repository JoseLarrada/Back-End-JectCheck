package com.Jetcheck.Aplication.DTo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentRequest {
    String idAdvance;
    String annexes;
    String file;
    String comment;
    String idAssignment;
}
