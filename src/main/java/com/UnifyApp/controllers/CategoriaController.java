package com.UnifyApp.controllers;

import com.UnifyApp.models.Categoria;
import com.UnifyApp.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías", description = "Gestión de categorías de gastos")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    @Operation(
        summary = "Crear nueva categoría",
        description = "Crea una nueva categoría de gastos en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria categoriaCreada = categoriaService.crearCategoria(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(
        summary = "Obtener todas las categorías",
        description = "Retorna una lista completa de todas las categorías registradas en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)))
    public ResponseEntity<List<Categoria>> obtenerTodas() {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/activas")
    @Operation(
        summary = "Obtener categorías activas",
        description = "Retorna solo las categorías que están activas en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de categorías activas obtenida",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)))
    public ResponseEntity<List<Categoria>> obtenerActivas() {
        List<Categoria> categorias = categoriaService.obtenerActivas();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener categoría por ID",
        description = "Busca y retorna una categoría específica usando su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.obtenerPorId(id);
        return categoria.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(
        summary = "Obtener categoría por nombre",
        description = "Busca una categoría específica por su nombre"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Categoria> obtenerPorNombre(@PathVariable String nombre) {
        Optional<Categoria> categoria = categoriaService.obtenerPorNombre(nombre);
        return categoria.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar categoría",
        description = "Actualiza los datos de una categoría existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaActualizada) {
        Categoria categoriaActualizadaResult = categoriaService.actualizarCategoria(id, categoriaActualizada);
        if (categoriaActualizadaResult != null) {
            return ResponseEntity.ok(categoriaActualizadaResult);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Desactivar categoría",
        description = "Desactiva una categoría (eliminación lógica - no elimina datos)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría desactivada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Void> desactivarCategoria(@PathVariable Long id) {
        Categoria categoriaDesactivada = categoriaService.desactivarCategoria(id);
        if (categoriaDesactivada != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/permanente")
    @Operation(
        summary = "Eliminar categoría permanentemente",
        description = "Elimina una categoría de forma permanente (eliminación física)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        if (categoriaService.eliminarCategoria(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/verificar/{nombre}")
    @Operation(
        summary = "Verificar si nombre existe",
        description = "Verifica si una categoría con ese nombre ya existe en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Resultado de verificación obtenido")
    public ResponseEntity<Boolean> nombreExiste(@PathVariable String nombre) {
        boolean existe = categoriaService.nombreExiste(nombre);
        return ResponseEntity.ok(existe);
    }
}
