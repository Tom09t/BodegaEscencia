package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.DTO.DetalleRegaloDto;
import com.paquete.Bodega.DTO.RegaloDto;
import com.paquete.Bodega.models.DetalleRegalo;
import com.paquete.Bodega.models.Grupo;
import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.models.Regalo;
import com.paquete.Bodega.repository.*;
import com.paquete.Bodega.services.service.RegaloService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegaloServiceImpl extends BaseServiceImpl<Regalo, Long> implements RegaloService {

    //Inyeccion De dependencias
    @Autowired
    private RegaloRepository regaloRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    private DetalleRegaloRepository detalleRegaloRepository;

    @Autowired
    private  DetalleRegaloServiceImpl detalleRegaloService;

    @Autowired
    private ProductoServiceImpl productoService;
    //Configuraciones necesarias
    public RegaloServiceImpl(BaseRepository<Regalo, Long> baseRepository, RegaloRepository regaloRepository) {
        super(baseRepository);
        this.regaloRepository = regaloRepository;
    }



@Transactional
    public Regalo guardarRegalo(RegaloDto regalo) throws Exception {


        Long grupoId=regalo.getGrupoId();
        Grupo grupoExistente =grupoRepository.findById(grupoId)
                .orElseThrow(() -> new Exception("No se encontró el grupo con ID: " + grupoId));


        Regalo nuevoRegalo = new Regalo();
        nuevoRegalo.setFecha(regalo.getFecha());

        Regalo regaloGuardado=regaloRepository.save(nuevoRegalo);
        List<DetalleRegalo>detalles= new ArrayList<>();


        for(DetalleRegaloDto detalleRegaloDto :regalo.getDetallesRegalo()){

            DetalleRegalo detalleRegalo = new DetalleRegalo();
            detalleRegalo.setCantidad(detalleRegaloDto.getCantidad());

            Producto producto = productoRepository.findById(detalleRegaloDto.getProductoId()).orElse(null);
            detalleRegalo.setProducto(producto);
            productoService.actualizarStockRegalo( detalleRegaloDto.getProductoId(),detalleRegaloDto.getCantidad());
            detalleRegalo.setRegalo(regaloGuardado);
            detalles.add(detalleRegalo);


            detalleRegaloService.guardarDetalleRegalo(detalleRegalo);

        }

        regaloRepository.save(regaloGuardado);
        return  regaloGuardado;

    }

    public void eliminarRegalo(Long idRegalo) throws Exception {
        Regalo regalo= regaloRepository.findById(idRegalo)
                .orElseThrow(()-> new Exception(("Regalo no encontrado con el ID" + idRegalo)));


        List<DetalleRegalo>detalleRegalos=regalo.getDetalleRegalos();

        for(DetalleRegalo detalleRegalo : detalleRegalos){
            Producto producto=detalleRegalo.getProducto();

            if (producto != null) {
                producto.setStock(producto.getStockRegalo() + detalleRegalo.getCantidad());
                productoRepository.save(producto);
            }

            detalleRegaloRepository.deleteById(detalleRegalo.getId());
        }

        regaloRepository.deleteById(idRegalo);

    }


}
