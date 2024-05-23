package com.Jetcheck.Aplication.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facultades {
    @Id
    @Column(name = "id_facultad")
    private int idFacultad;
    private String nombre;
}
