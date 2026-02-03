package com.UnifyApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.UnifyApp.models.Cuenta;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICuenta extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findByUsuarioId(Long usuarioId);
    List<Cuenta> findByActivoTrue();
    List<Cuenta> findByUsuarioIdAndActivoTrue(Long usuarioId);
}
