package org.example.views;

import org.example.models.entities.Alumno;
import org.example.models.entities.Clase;
import org.example.models.services.impl.AlumnoService;
import org.example.models.services.impl.ClaseService;
import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;

public class Menu {

    public static final ClaseService cs = ClaseService.getInstance();
    public static final AlumnoService as = AlumnoService.getInstance();

    public static void run(){
        Integer opcion;

        do{
            opcion = menu();

            switch(opcion){
                case 1 -> agregarAlumno();
                case 2 -> eliminarAlumno();
                case 3 -> mostrarTodosLosAlumnos();
                case 4 -> mostrarAlumnosIncriptosEnClase();
                case 5 -> agregarClase();
                case 6 -> claseConCupoDisponible();
                case 7 -> inscribirAlumno();
                case 8 -> claseConMasAlumnosIncriptos();
                case 9 -> promedioAlumnosPorClase();
                case 10 -> cuposDisponiblesTotal();
                case 0 -> System.exit(opcion);
                default -> System.out.println("Opcion invalida");
            }

        }while(opcion!=0);
    }

    public static Integer menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== MENÚ DE OPCIONES =====");
        System.out.println("1. Agregar alumno");
        System.out.println("2. Eliminar alumno");
        System.out.println("3. Mostrar todos los alumnos");
        System.out.println("4. Mostrar alumnos inscriptos en una clase");
        System.out.println("5. Agregar clase");
        System.out.println("6. Mostrar clases con cupo disponible");
        System.out.println("7. Inscribir alumno a una clase");
        System.out.println("8. Mostrar clase con más alumnos inscriptos");
        System.out.println("9. Mostrar promedio de alumnos por clase");
        System.out.println("10. Mostrar total de cupos disponibles");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");

        return scanner.nextInt();
    }

    public static void agregarAlumno() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre del alumno: ");
        String alumnoNombre = scanner.nextLine();
        System.out.println("Ingrese el email del alumno: ");
        String alumnoEmail = scanner.nextLine();

        as.agregar(Alumno.builder()
                .nombre(alumnoNombre)
                .email(alumnoEmail)
                        .idClase(0)
                        .id(0)
                .build());
    }

    public static void eliminarAlumno() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(as.mostrarTodos());
        System.out.println("Ingrese el id del alumno: ");
        Integer alumnoId = scanner.nextInt();
           as.sacarAlumno(alumnoId);
    }

    public static void mostrarTodosLosAlumnos(){
        as.mostrarTodos()
                .forEach(System.out::println);

    }

    public static void mostrarAlumnosIncriptosEnClase(){
        Scanner scanner = new Scanner(System.in);
        cs.mostrarTodos()
                .forEach(System.out::println);

        System.out.println("Ingresar el id de la clase que desea ver: ");
        Integer claseId = scanner.nextInt();

        as.alumnosPorClase(claseId)
                .forEach(System.out::println);
    }

    public static void agregarClase() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del clase: ");
        String claseNombre = scanner.nextLine();
        System.out.println("Ingrese el nombre del profesor");
        String profesorNombre = scanner.nextLine();
        System.out.println("Ingrese cupos disponibles");
        Integer cupoDisponibles = scanner.nextInt();

        cs.agregar(Clase.builder()
                .nombre(claseNombre)
                .profesor(profesorNombre)
                .cuposDisponibles(cupoDisponibles)
                .build());
    }

    public static void claseConCupoDisponible() {
        cs.claseConCuposDisponibles()
                .forEach(System.out::println);
    }
    public static void inscribirAlumno() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el id del alumno: ");
        Integer alumnoId = scanner.nextInt();
        System.out.println("Ingrese el id de la clase");
        Integer claseId = scanner.nextInt();

        as.inscribir(alumnoId, claseId);
    }

    public static void claseConMasAlumnosIncriptos() {
        System.out.println(cs.claseConMasAlumnosIncriptos());
    }

    public static void promedioAlumnosPorClase(){
        System.out.println("Promedio de alumnos por clase: " + as.promedioAlumnoPorClase());
    }

    public static void cuposDisponiblesTotal(){
        System.out.println("Total de cupos disponibles: " + cs.cuposDisponiblesTotales());
    }
}
