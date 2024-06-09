package com.Jetcheck.Aplication.DTo.Response;

import com.Jetcheck.Aplication.Entity.Avances;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class AnalitycsResponse {
    private int numberProjects;
    private int numberAdvances;
    private int numberAssignments;
    private List<Avances> advances;
    List<InteractiveSearch> areas;
}
