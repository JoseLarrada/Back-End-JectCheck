package com.Jetcheck.Aplication.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Areas {
    @Id
    @Column(name = "id_area")
    private int idArea;
    private String nombre;
    @Column(name = "id_facultad")
    private int idFacultad;
}
