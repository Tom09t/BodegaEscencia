package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.DTO.NuevaEmpresaDto;
import com.paquete.Bodega.models.EmpleadoEmpresa;
import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.EmpleadoEmpresaRepository;
import com.paquete.Bodega.repository.EmpresaRepository;
import com.paquete.Bodega.services.service.EmpleadoEmpresaService;
import com.paquete.Bodega.services.service.EmpresaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServiceImpl extends BaseServiceImpl<Empresa, Long> implements EmpresaService {

    //Inyeccion de dependencias
    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    EmpleadoEmpresaRepository empleadoEmpresaRepository;
    @Autowired
    private EmpleadoEmpresaServiceImpl empleadoService;

    //Configuracion necesaria
    public EmpresaServiceImpl(BaseRepository<Empresa, Long> baseRepository, EmpresaRepository empresaRepository) {
        super(baseRepository);
        this.empresaRepository = empresaRepository;
    }



public Empresa crearEmpresaConEmpleados (NuevaEmpresaDto nuevaEmpresaDto){
        Empresa empresa = new Empresa();
        empresa.setNombreEmpresa(nuevaEmpresaDto.getNombreEmpresa());

        List<EmpleadoEmpresa>empleadoEmpresas= new ArrayList<>();

    for(EmpleadoEmpresa empleadoEmpresa : nuevaEmpresaDto.getEmpleadoEmpresa()) {
        EmpleadoEmpresa empleado = new EmpleadoEmpresa();
        empleado.setNombreEmpleado(empleadoEmpresa.getNombreEmpleado());
        empleado.setComision(empleadoEmpresa.getComision());
        empleadoEmpresas.add(empleado);
    }
    System.out.println("Empleados antes de persistir: " + empleadoEmpresas);
    empresa.setEmpleadoEmpresa(empleadoEmpresas);

    return empresaRepository.save(empresa);
}


    public Empresa actualizarEmpresa(Long empresaId, NuevaEmpresaDto empresaDto) {
        Optional<Empresa> empresaExistenteOptional = empresaRepository.findById(empresaId);

        if (empresaExistenteOptional.isPresent()) {
            Empresa empresaExistente = empresaExistenteOptional.get();

            empresaExistente.setNombreEmpresa(empresaDto.getNombreEmpresa());

            // Actualizar empleados
            for (EmpleadoEmpresa empleadoDto : empresaDto.getEmpleadoEmpresa()) {
                Optional<EmpleadoEmpresa> empleadoExistenteOptional = empleadoEmpresaRepository.findById(empleadoDto.getId());

                if (empleadoExistenteOptional.isPresent()) {
                    EmpleadoEmpresa empleadoExistente = empleadoExistenteOptional.get();
                    empleadoExistente.setNombreEmpleado(empleadoDto.getNombreEmpleado());
                    empleadoExistente.setComision(empleadoDto.getComision());

                    // Llamar al método en EmpleadoEmpresaService para actualizar el empleado
                    empleadoService.actualizarEmpleado(empleadoExistente);
                }
            }

            // Guardar la empresa actualizada
            return empresaRepository.save(empresaExistente);
        } else {
            return null; // O manejar según tus necesidades
        }
    }
    public EmpleadoEmpresa agregarEmpleado(Long empresaId, EmpleadoEmpresa empleadoEmpresa) {
        Optional<Empresa> empresaExistenteOptional = empresaRepository.findById(empresaId);

        if (empresaExistenteOptional.isPresent()) {
            Empresa empresaExistente = empresaExistenteOptional.get();

            // Verificar si el empleado ya existe
            Optional<EmpleadoEmpresa> empleadoExistenteOptional = empleadoEmpresaRepository.findById(empleadoEmpresa.getId());

            if (empleadoExistenteOptional.isPresent()) {
                // El empleado ya existe, puedes manejarlo según tus necesidades
                return null;
            } else {
                // El empleado no existe, puedes crear uno nuevo y agregarlo a la empresa
                EmpleadoEmpresa nuevoEmpleado = new EmpleadoEmpresa();
                nuevoEmpleado.setNombreEmpleado(empleadoEmpresa.getNombreEmpleado());
                nuevoEmpleado.setComision(empleadoEmpresa.getComision());
                nuevoEmpleado.setEmpresa(empresaExistente);

                // Guardar el nuevo empleado
                return empleadoEmpresaRepository.save(nuevoEmpleado);
            }
        } else {
            return null; // O manejar según tus necesidades
        }
    }



}
