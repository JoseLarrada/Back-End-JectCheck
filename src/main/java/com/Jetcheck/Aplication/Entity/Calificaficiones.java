package com.Jetcheck.Aplication.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "calificaciones")
public class Calificaficiones {
    @Column(name = "id_calificacion")
    private String idCalificacion;
    @Column(name = "valor_calificacion")
    private Double valorCalificacion;
    private String comentario;
}
