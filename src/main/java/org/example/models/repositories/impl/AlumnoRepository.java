package org.example.models.repositories.impl;

import org.example.configs.DBConnection;
import org.example.models.entities.Alumno;
import org.example.models.repositories.interfaces.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlumnoRepository implements IRepository<Alumno> {
    private static AlumnoRepository instance;

    private AlumnoRepository() {
    }

    public static AlumnoRepository getInstance() {
        if (instance == null) {
            instance = new AlumnoRepository();
        }
        return instance;
    }

    @Override
    public void save(Alumno alumno) throws SQLException {
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO alumnos(nombre,email) VALUES (?,?)")) {
            preparedStatement.setString(1, alumno.getNombre());
            preparedStatement.setString(2, alumno.getEmail());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Alumno> findAll() throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM alumnos")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alumnos.add(Alumno.builder()
                            .id(rs.getInt("id"))
                            .nombre(rs.getString("nombre"))
                            .email(rs.getString("email"))
                            .idClase(rs.getInt("clase_id"))
                            .build());
                }
            }
        }
        return alumnos;
    }

    @Override
    public Optional<Alumno> findById(Integer id) throws SQLException {
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM alumnos WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(Alumno.builder()
                            .id(rs.getInt("id"))
                            .nombre(rs.getString("nombre"))
                            .email(rs.getString("email"))
                            .idClase(rs.getInt("clase_id"))
                            .build()
                    );
                }

            }
        }
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM alumnos WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Alumno alumno) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE alumnos SET nombre = ?, email = ?, clase_id = ? WHERE id = ?")) {

            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getEmail());
            ps.setInt(3, alumno.getIdClase());
            ps.setInt(4, alumno.getId());
            ps.executeUpdate();
        }
    }
}
