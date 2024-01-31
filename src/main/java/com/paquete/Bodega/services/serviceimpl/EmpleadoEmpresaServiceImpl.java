package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.EmpleadoEmpresa;
import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.EmpleadoEmpresaRepository;
import com.paquete.Bodega.repository.EmpresaRepository;
import com.paquete.Bodega.services.service.EmpleadoEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoEmpresaServiceImpl extends BaseServiceImpl<EmpleadoEmpresa,Long> implements EmpleadoEmpresaService {

    //Inyeccion de dependencias
    @Autowired
    EmpleadoEmpresaRepository empleadoEmpresaRepository;

    @Autowired
    EmpresaRepository empresaRepository;

    //Configuracion Necesaria
    public EmpleadoEmpresaServiceImpl(BaseRepository<EmpleadoEmpresa, Long> baseRepository, EmpleadoEmpresaRepository empleadoEmpresaRepository) {
        super(baseRepository);
        this.empleadoEmpresaRepository = empleadoEmpresaRepository;
    }

    public void eliminarEmpleadoEmpresa(Long empleadoId, Long empresaId) throws Exception {
        Optional<Empresa> optionalEmpresa = empresaRepository.findById(empresaId);
        if (optionalEmpresa.isPresent()) {
            Empresa empresa= optionalEmpresa.get();

            List<EmpleadoEmpresa> empleados = empresa.getEmpleadoEmpresa();

            empleados.removeIf(empleado -> empleado.getId()==(empleadoId));

            empresaRepository.save(empresa);
        } else {
            throw new Exception("No se encontró el empleado con ID: " + empleadoId +
                    " en la empresa con ID: " + empresaId);
        }
    }

    public EmpleadoEmpresa actualizarEmpleado(EmpleadoEmpresa empleadoActualizado) {
        // Lógica para actualizar el empleado en el repositorio
        return empleadoEmpresaRepository.save(empleadoActualizado);
    }
}



