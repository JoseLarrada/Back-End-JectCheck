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
@Table(name = "avances")
public class Avances {
    @Column(name = "id_estado")
    private int idEstado;
    private Date fecha_creacion;
    private String id_ruta;
    @Id
    @Column(nullable = false)
    private String id_avance;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String titulo;
    @Column(name = "nota_acumulada")
    private Double notaAcumulada;
}
