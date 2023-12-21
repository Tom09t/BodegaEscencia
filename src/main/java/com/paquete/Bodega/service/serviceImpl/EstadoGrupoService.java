package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.EstadoGrupo;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EstadoGrupoService  implements ServiceImpl<EstadoGrupo,EstadoGrupo> {
    @Override
    public EstadoGrupo guardar(EstadoGrupo estadoGrupo) throws IOException {
        return null;
    }

    @Override
    public List<EstadoGrupo> listar() {
        return null;
    }

    @Override
    public EstadoGrupo buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public EstadoGrupo actualizar(EstadoGrupo estadoGrupo) {
        return null;
    }
}
