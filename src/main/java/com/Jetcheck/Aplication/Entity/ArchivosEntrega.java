package com.Jetcheck.Aplication.Entity;

import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class ArchivosEntrega extends DatosArchivos{
    private String idEntrega;

    public ArchivosEntrega(String id_archivo, String url, String nombre_archivo, String idEntrega) {
        super(id_archivo, url, nombre_archivo);
        this.idEntrega = idEntrega;
    }
}
