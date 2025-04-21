package org.example.models.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Alumno {
    private Integer id;
    private String nombre;
    private String email;
    private Integer idClase;

    @Override
    public String toString() {
        return "\nAlumno {" +
                "\n  ID: " + id +
                "\n  Nombre: " + nombre +
                "\n  Email: " + email +
                "\n  ID de Clase: " + idClase +
                "\n}";
    }
}
