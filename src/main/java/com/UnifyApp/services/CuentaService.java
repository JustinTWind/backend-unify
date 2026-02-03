package com.UnifyApp.services;

import com.UnifyApp.models.Cuenta;
import com.UnifyApp.repositories.ICuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private ICuenta cuentaRepository;

    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public List<Cuenta> obtenerTodas() {
        return cuentaRepository.findAll();
    }

    public List<Cuenta> obtenerActivas() {
        return cuentaRepository.findByActivoTrue();
    }

    public Optional<Cuenta> obtenerPorId(Long id) {
        return cuentaRepository.findById(id);
    }

    public List<Cuenta> obtenerPorUsuario(Long usuarioId) {
        return cuentaRepository.findByUsuarioId(usuarioId);
    }

    public List<Cuenta> obtenerActivasPorUsuario(Long usuarioId) {
        return cuentaRepository.findByUsuarioIdAndActivoTrue(usuarioId);
    }

    public Cuenta actualizarCuenta(Long id, Cuenta cuentaActualizada) {
        Optional<Cuenta> cuentaExistente = cuentaRepository.findById(id);
        if (cuentaExistente.isPresent()) {
            Cuenta cuenta = cuentaExistente.get();
            cuenta.setNombre(cuentaActualizada.getNombre());
            cuenta.setTipo(cuentaActualizada.getTipo());
            cuenta.setSaldoInicial(cuentaActualizada.getSaldoInicial());
            cuenta.setActivo(cuentaActualizada.getActivo());
            return cuentaRepository.save(cuenta);
        }
        return null;
    }

    public boolean eliminarCuenta(Long id) {
        if (cuentaRepository.existsById(id)) {
            cuentaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Cuenta desactivarCuenta(Long id) {
        Optional<Cuenta> cuentaExistente = cuentaRepository.findById(id);
        if (cuentaExistente.isPresent()) {
            Cuenta cuenta = cuentaExistente.get();
            cuenta.setActivo(false);
            return cuentaRepository.save(cuenta);
        }
        return null;
    }

    public Double obtenerSaldoActual(Long cuentaId) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(cuentaId);
        if (cuenta.isPresent()) {
            return cuenta.get().getSaldoInicial();
        }
        return null;
    }
}
