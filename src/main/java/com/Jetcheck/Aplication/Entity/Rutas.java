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
    private int id_estado;
    @Id
    private String id_ruta;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String id_docente;
    @Column(nullable = false)
    private String id_estudiante;
    @Column(nullable = false)
    private String nombre;
    private String id_integrante;
    private String id_integrante2;

}
