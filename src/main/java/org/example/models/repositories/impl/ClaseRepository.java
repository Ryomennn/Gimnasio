package org.example.models.repositories.impl;

import org.example.configs.DBConnection;
import org.example.models.entities.Clase;
import org.example.models.repositories.interfaces.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClaseRepository implements IRepository<Clase> {

    private static  ClaseRepository instance;

    private ClaseRepository() {}

    public static ClaseRepository getInstance() {
        if (instance == null) {
            instance = new ClaseRepository();
        }
        return instance;
    }

    @Override
    public void save(Clase clase) throws SQLException {
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO clases (nombre,profesor,cupos_disponibles) VALUES (?,?,?)")) {
            ps.setString(1, clase.getNombre());
            ps.setString(2, clase.getProfesor());
            ps.setInt(3,clase.getCuposDisponibles());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Clase> findAll() throws SQLException {
        List<Clase> clases = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM clases");) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clases.add(Clase.builder()
                            .id(rs.getInt("id"))
                            .nombre(rs.getString("nombre"))
                            .profesor(rs.getString("profesor"))
                            .cuposDisponibles(rs.getInt("cupos_disponibles"))
                            .build()
                    );
                }
            }
        }
        return clases;
    }

    @Override
    public Optional<Clase> findById(Integer id) throws SQLException {
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM clases WHERE id = ?")) {
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(Clase.builder()
                            .id(rs.getInt("id"))
                            .nombre(rs.getString("nombre"))
                            .profesor(rs.getString("profesor"))
                            .cuposDisponibles(rs.getInt("cupos_disponibles"))
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
        PreparedStatement ps = connection.prepareStatement("DELETE FROM clases WHERE id = ?")) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Clase clase) throws SQLException {
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE clases SET cupos_disponibles = ? WHERE id = ?")) {
            ps.setInt(1,clase.getCuposDisponibles());
            ps.setInt(2,clase.getId());
            ps.executeUpdate();
        }
    }
}
