package com.UnifyApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.UnifyApp.models.Categoria;
import java.util.List;

@Repository
public interface ICategoria extends JpaRepository<Categoria, Long> {
    List<Categoria> findByActivoTrue();
    java.util.Optional<Categoria> findByNombre(String nombre);
}