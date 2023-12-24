package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.services.service.BaseService;
import com.paquete.Bodega.models.BaseEntidad;
import com.paquete.Bodega.repository.BaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl <E extends BaseEntidad, ID extends Serializable> implements BaseService<E, ID> {

    protected BaseRepository<E,ID> baseRepository;

    //inyeccion de dependencias
    @Autowired
    public BaseServiceImpl(BaseRepository<E,ID> baseRepository) {

        this.baseRepository = baseRepository;
    }

    //primer metodo
    @Override
    @Transactional
    public List<E> findAll() throws Exception {
        try {
            List<E> entities = baseRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    //segundo metodo
    @Override
    @Transactional
    public E findById(ID id) throws Exception {
        try{
            Optional<E> entityOptional = baseRepository.findById(id);
            return entityOptional.get();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Tercer metodo
    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try{
            entity = baseRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //cuarto METODO
    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try{
            Optional<E> entityOptional = baseRepository.findById(id);
            E entityUpdate = entityOptional.get();
            entityUpdate = baseRepository.save(entity);
            return entityUpdate;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //quinto metodo
    public boolean delete(ID id) throws Exception {
        try{
            if(baseRepository.existsById(id)){
                baseRepository.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}
