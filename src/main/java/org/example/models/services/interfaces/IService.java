package org.example.models.services.interfaces;

import java.util.List;

public interface IService<T>{
    void agregar(T t);
    List<T> mostrarTodos();
    T obtenerPorId(Integer id);
    void eliminar(Integer id);
}
