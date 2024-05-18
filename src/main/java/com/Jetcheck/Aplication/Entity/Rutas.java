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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="rutas")
public class Rutas {
    @Column(name = "id_estado")
    private int idEstado;
    @Id
    private String id_ruta;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String id_docente;
    @Column(nullable = false)
    private String id_estudiante;
    @Column(nullable = false)
    private String titulo;
    private String id_integrante;
    private String id_integrante2;
    @Column(name = "nota_final")
    private Double notaFinal;
}
