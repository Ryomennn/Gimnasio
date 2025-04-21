package org.example.models.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Clase {
    private Integer id;
    private String nombre;
    private String profesor;
    private Integer cuposDisponibles;

    @Override
    public String toString() {
        return "\nClase {" +
                "\n  ID: " + id +
                "\n  Nombre: " + nombre +
                "\n  Profesor: " + profesor +
                "\n  Cupos disponibles: " + cuposDisponibles +
                "\n}";
    }
}
