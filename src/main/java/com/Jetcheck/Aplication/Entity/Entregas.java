package com.Jetcheck.Aplication.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "entregas")
public class Entregas {
    private String id_avance;
    private String anexos;
    private String archivo_entrega;
    private String comentario;
    @Id
    private String id_entrega;
}
