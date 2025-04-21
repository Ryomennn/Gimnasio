package org.example.models.services.impl;

import lombok.Getter;
import org.example.models.entities.Alumno;
import org.example.models.entities.Clase;
import org.example.models.repositories.impl.AlumnoRepository;
import org.example.models.repositories.impl.ClaseRepository;
import org.example.models.services.interfaces.IService;

import java.nio.channels.ScatteringByteChannel;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ClaseService implements IService<Clase> {

    @Getter
    private static final ClaseService instance = new ClaseService();

    private final ClaseRepository claseRepository;
    private final AlumnoRepository alumnoRepository;

    private ClaseService() {
        this.claseRepository = ClaseRepository.getInstance();
        this.alumnoRepository = AlumnoRepository.getInstance();

    }

    @Override
    public void agregar(Clase clase) {
        try{
            claseRepository.save(clase);
            System.out.println("Clase agregada con exito");
        }catch(SQLException e){
            System.out.println("Error al agregar la clase");
        }
    }

    @Override
    public List<Clase> mostrarTodos() {
        try{
            return claseRepository.findAll();
        }catch(SQLException e){
            System.out.println("Error al mostrar la lista de clases");
        }
        return List.of();
    }

    @Override
    public Clase obtenerPorId(Integer id) {
        try{
            return claseRepository.findById(id).orElseThrow(NoSuchElementException::new);
        }catch(SQLException e){
            throw new NoSuchElementException("Error al obtener clase por id: " + id);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try{
            claseRepository.delete(id);
            System.out.println("Clase eliminada con exito");
        }catch(SQLException e){
            System.out.println("Error al eliminar la clase");
        }
    }

    public List<Clase> claseConCuposDisponibles() {
        try{
            return claseRepository.findAll()
                    .stream()
                    .filter(c->c.getCuposDisponibles()>0)
                    .toList();

        }catch(SQLException e){
            System.out.println("Error al obtener la lista de clases");
        }
        return List.of();
    }

    public Clase claseConMasAlumnosIncriptos() {
        try {
            List<Clase> clases = claseRepository.findAll();
            List<Alumno> alumnos = alumnoRepository.findAll();

            return clases.stream()
                    .max(Comparator.comparingLong(clase ->
                            alumnos.stream()
                                    .filter(alumno -> alumno.getIdClase().equals(clase.getId()))
                                    .count()
                    ))
                    .orElseThrow(() -> new NoSuchElementException("No se encontro"));

        } catch (SQLException e) {
            throw new NoSuchElementException("Error al obtener la lista de clases");
        }
    }

    public Integer cuposDisponiblesTotales() {
        try {
            return claseRepository.findAll()
                    .stream()
                    .mapToInt(Clase::getCuposDisponibles)
                    .sum();
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de clases");
            return 0;
        }
    }

}
