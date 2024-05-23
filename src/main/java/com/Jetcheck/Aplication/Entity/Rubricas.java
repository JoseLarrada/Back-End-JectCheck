package com.Jetcheck.Aplication.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rubricas {
    @Id
    @Column(name = "id_rubrica")
    private String idRubrica;
    private String url;
    @Column(name = "nombre_archivo")
    private String nombreArchivo;
    @Column(name = "id_avance")
    private String idAvance;
}
