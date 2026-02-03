package com.UnifyApp.services;

import com.UnifyApp.models.Movimiento;
import com.UnifyApp.repositories.IMovimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {

    @Autowired
    private IMovimiento movimientoRepository;

    public Movimiento crearMovimiento(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    public List<Movimiento> obtenerTodos() {
        return movimientoRepository.findAll();
    }

    public List<Movimiento> obtenerActivos() {
        return movimientoRepository.findByActivoTrue();
    }

    public Optional<Movimiento> obtenerPorId(Long id) {
        return movimientoRepository.findById(id);
    }

    public List<Movimiento> obtenerPorCategoria(Long categoriaId) {
        return movimientoRepository.findByCategoriaId(categoriaId);
    }

    public List<Movimiento> obtenerPorCuenta(Long cuentaId) {
        return movimientoRepository.findByCuentaId(cuentaId);
    }

    public List<Movimiento> obtenerActivosPorCuenta(Long cuentaId) {
        return movimientoRepository.findByCuentaIdAndActivoTrue(cuentaId);
    }

    public Movimiento actualizarMovimiento(Long id, Movimiento movimientoActualizado) {
        Optional<Movimiento> movimientoExistente = movimientoRepository.findById(id);
        if (movimientoExistente.isPresent()) {
            Movimiento movimiento = movimientoExistente.get();
            movimiento.setTipo(movimientoActualizado.getTipo());
            movimiento.setMonto(movimientoActualizado.getMonto());
            movimiento.setFecha(movimientoActualizado.getFecha());
            movimiento.setActivo(movimientoActualizado.isActivo());
            movimiento.setCategoria(movimientoActualizado.getCategoria());
            movimiento.setCuenta(movimientoActualizado.getCuenta());
            return movimientoRepository.save(movimiento);
        }
        return null;
    }

    public boolean eliminarMovimiento(Long id) {
        if (movimientoRepository.existsById(id)) {
            movimientoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Movimiento desactivarMovimiento(Long id) {
        Optional<Movimiento> movimientoExistente = movimientoRepository.findById(id);
        if (movimientoExistente.isPresent()) {
            Movimiento movimiento = movimientoExistente.get();
            movimiento.setActivo(false);
            return movimientoRepository.save(movimiento);
        }
        return null;
    }

    public Double calcularTotalMovimientos(Long cuentaId) {
        List<Movimiento> movimientos = obtenerPorCuenta(cuentaId);
        return movimientos.stream()
                .mapToDouble(Movimiento::getMonto)
                .sum();
    }

    public Double calcularIngresos(Long cuentaId) {
        List<Movimiento> movimientos = obtenerPorCuenta(cuentaId);
        return movimientos.stream()
                .filter(m -> "ingreso".equalsIgnoreCase(m.getTipo()))
                .mapToDouble(Movimiento::getMonto)
                .sum();
    }

    public Double calcularGastos(Long cuentaId) {
        List<Movimiento> movimientos = obtenerPorCuenta(cuentaId);
        return movimientos.stream()
                .filter(m -> "gasto".equalsIgnoreCase(m.getTipo()))
                .mapToDouble(Movimiento::getMonto)
                .sum();
    }
}
