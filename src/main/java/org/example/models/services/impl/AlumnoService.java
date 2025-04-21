package org.example.models.services.impl;

import lombok.Getter;
import org.example.models.entities.Alumno;
import org.example.models.entities.Clase;
import org.example.models.repositories.impl.AlumnoRepository;
import org.example.models.repositories.impl.ClaseRepository;
import org.example.models.services.interfaces.IService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class AlumnoService implements IService<Alumno> {

    @Getter
    private static final AlumnoService instance = new AlumnoService();

    private final AlumnoRepository alumnoRepository;
    private final ClaseRepository claseRepository;

    private AlumnoService() {
        this.alumnoRepository = AlumnoRepository.getInstance();
        this.claseRepository = ClaseRepository.getInstance();
    }

    @Override
    public void agregar(Alumno alumno) {
        try {
                alumnoRepository.save(alumno);
                System.out.println("Alumno agregado exitosamente");

        } catch (SQLException e) {
            throw new NoSuchElementException("No se pudo agregar el alumno");
        }

    }

    @Override
    public List<Alumno> mostrarTodos() {
        try{
            return alumnoRepository.findAll();
        }catch (SQLException e){
            System.out.println("Error al mostrar la lista de alumnos");
        }
        return List.of();
    }

    @Override
    public Alumno obtenerPorId(Integer id) {
        try{
            return alumnoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        }catch(SQLException e){
           throw new NoSuchElementException("No existe el alumno con el id " + id);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try{
            alumnoRepository.delete(id);
            System.out.println("Alumno eliminado exitosamente");
        }catch(SQLException e){
            System.out.println("Error al eliminar el alumno");
        }
    }

    public void sacarAlumno(Integer id){
        try{
            Alumno alumno = alumnoRepository.findById(id)
                    .orElseThrow(NoSuchElementException::new);

            Clase disponibilidad = claseRepository.findById(id)
                    .orElseThrow(NoSuchElementException::new);

            disponibilidad.setCuposDisponibles(disponibilidad.getCuposDisponibles() + 1);
            claseRepository.update(disponibilidad);

        }catch(SQLException e){
            throw new NoSuchElementException("No existe el alumno con el id " + id);
        }

    }

    public List<Alumno> alumnosPorClase(Integer idClase){
        try{
            List<Alumno> inscriptos = alumnoRepository.findAll();

            return inscriptos.stream()
                    .filter(i-> i.getIdClase().equals(idClase))
                    .toList();

        }catch(SQLException e){
            System.out.println("Error al encontrar la lista de clases");
        }
        return List.of();
    }

    public void inscribir(Integer idAlumno, Integer idClase) {
        try {
            Alumno alumno = alumnoRepository.findById(idAlumno)
                    .orElseThrow(() -> new NoSuchElementException("Alumno no encontrado"));

            Clase clase = claseRepository.findById(idClase)
                    .orElseThrow(() -> new NoSuchElementException("Clase no encontrada"));

            if (!alumno.getIdClase().equals(idClase)) {
                if (clase.getCuposDisponibles() > 0) {
                    clase.setCuposDisponibles(clase.getCuposDisponibles() - 1);
                    claseRepository.update(clase);
                    alumno.setIdClase(idClase);
                    alumnoRepository.update(alumno);
                    System.out.println("Alumno inscrito correctamente en la clase.");
                } else {
                    System.out.println("No hay cupos disponibles en esta clase.");
                }
            } else {
                System.out.println("El alumno ya está inscripto en esta clase.");
            }

        } catch (SQLException e) {
            System.out.println("Error al inscribir alumno: " + e.getMessage());
        }
    }

    public Double promedioAlumnoPorClase(){
        try{
            List<Alumno> alumnos = alumnoRepository.findAll();

            Map<Integer, Long> alumnoPorClase = alumnos.stream()
                    .filter(a-> a.getIdClase() != null)
                    .collect(Collectors.groupingBy(
                            Alumno::getIdClase,
                            Collectors.counting()
                    ));

            Long totalAlumnos = alumnoPorClase.values().stream()
                    .mapToLong(Long::longValue)
                    .sum();

            Integer cantidadAlumnos =alumnoPorClase.size();

            if(cantidadAlumnos == 0) return 0.0;

            return (double)totalAlumnos/cantidadAlumnos;
        }catch(SQLException e){
            throw new NoSuchElementException("Error al obtener la lista de clases");
        }
    }

}
