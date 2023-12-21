package com.paquete.Bodega.service;

import java.io.IOException;
import java.util.List;

public interface ServiceImpl <T, E>{

    public E guardar(T t) throws IOException;

    public List<E> listar();

    public E buscar(Long i);

    public void eliminar(Long i) throws IOException;

    public E actualizar(T t);



}
