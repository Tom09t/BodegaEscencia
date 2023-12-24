package com.paquete.Bodega.services.service;

import com.paquete.Bodega.models.BaseEntidad;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public interface BaseService <E extends BaseEntidad, ID extends Serializable>{

    //Listar todas las entidades con un ID (GET ALL)
    public List<E> findAll() throws Exception;

    //Buscar una entidad por id (GET)
    public E findById(ID id) throws Exception;

    //Metodo para crear una nueva entidad (POST)
    public E save(E entity) throws Exception;

    //Metodo para actualizar una entidad (UPDATE)
    public E update(ID id, E entity) throws Exception;

    //Metodo para eliminar una entidad (DELETE)
    public boolean delete(ID id) throws Exception;


}
