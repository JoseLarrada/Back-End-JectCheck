package com.Jetcheck.Aplication.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "entregas")
public class Entregas {
    @Column(name = "id_avance")
    private String idAvance;
    private String comentario;
    @Id
    private String id_entrega;
    @Column(name = "fecha_entrega")
    private Date fechaEntrega;
}
