package com.Jetcheck.Aplication.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "calificaciones")
public class Calificaficiones {
    @Column(name = "id_calificacion")
    @Id
    private String idCalificacion;
    @Column(name = "valor_calificacion")
    private Double valorCalificacion;
    private String comentario;
    @Column(name = "id_entrega")
    private String idEntrega;
}
