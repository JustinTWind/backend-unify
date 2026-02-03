package com.UnifyApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.UnifyApp.models.Usuario;
import java.util.Optional;
import java.util.List;

@Repository
public interface IUsuario extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailAndActivoTrue(String email);
    List<Usuario> findByActivoTrue();
}