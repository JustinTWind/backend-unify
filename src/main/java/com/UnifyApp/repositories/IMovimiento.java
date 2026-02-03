package com.UnifyApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.UnifyApp.models.Movimiento;
import java.util.List;

@Repository
public interface IMovimiento extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCategoriaId(Long categoriaId);
    List<Movimiento> findByCuentaId(Long cuentaId);
    List<Movimiento> findByActivoTrue();
    List<Movimiento> findByCuentaIdAndActivoTrue(Long cuentaId);
}
