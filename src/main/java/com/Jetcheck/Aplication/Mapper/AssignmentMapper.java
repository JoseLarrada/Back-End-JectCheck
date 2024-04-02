package com.Jetcheck.Aplication.Mapper;

import com.Jetcheck.Aplication.DTo.AssignmentRequest;
import com.Jetcheck.Aplication.Entity.Entregas;
import org.springframework.stereotype.Service;

@Service
public class AssignmentMapper {
    public Entregas mapperAssignment(AssignmentRequest assignmentRequest){
        return Entregas.builder()
                .id_avance(assignmentRequest.getIdAdvance())
                .anexos(assignmentRequest.getAnnexes())
                .archivo_entrega(assignmentRequest.getFile())
                .comentario(assignmentRequest.getComment())
                .id_entrega(assignmentRequest.getIdAssignment())
                .build();
    }
}
