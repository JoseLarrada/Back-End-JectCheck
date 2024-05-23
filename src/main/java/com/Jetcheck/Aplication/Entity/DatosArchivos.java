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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "archivosentregas")
public class DatosArchivos {
    @Id
    @Column(name = "id_archivo")
    private String idArchivo;
    private String url;
    @Column(name = "nombre_archivo")
    private String nombreArchivo;
    @Column(name = "id_entrega")
    private String idEntrega;
}
