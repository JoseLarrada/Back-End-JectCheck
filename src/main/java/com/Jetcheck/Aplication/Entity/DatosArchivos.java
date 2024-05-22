package com.Jetcheck.Aplication.Entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosArchivos {
    private String id_archivo;
    private String url;
    private String nombre_archivo;
}
