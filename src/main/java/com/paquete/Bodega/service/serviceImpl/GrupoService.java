package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.Grupo;
import com.paquete.Bodega.repository.GrupoRepository;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GrupoService implements ServiceImpl<Grupo,Grupo> {


    @Autowired
    GrupoRepository grupoRepository;


    @Override
    public Grupo guardar(Grupo grupo) throws IOException {
        return grupoRepository.save(grupo);
    }

    @Override
    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    @Override
    public Grupo buscar(Long i) {


        return grupoRepository.findById(i).orElse(null);
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public Grupo actualizar(Grupo grupo) {
        return null;
    }
}
