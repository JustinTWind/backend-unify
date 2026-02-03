package com.UnifyApp.controllers;

import com.UnifyApp.models.Usuario;
import com.UnifyApp.services.UsuarioService;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios de la aplicación")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Operation(
        summary = "Crear nuevo usuario",
        description = "Crea un nuevo usuario en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Retorna una lista completa de todos los usuarios registrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)))
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/activos")
    @Operation(
        summary = "Obtener usuarios activos",
        description = "Retorna solo los usuarios que están activos en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de usuarios activos obtenida",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)))
    public ResponseEntity<List<Usuario>> obtenerActivos() {
        List<Usuario> usuarios = usuarioService.obtenerActivos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener usuario por ID",
        description = "Busca y retorna un usuario específico por su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @Operation(
        summary = "Obtener usuario por email",
        description = "Busca un usuario específico por su dirección de correo electrónico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> obtenerPorEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.obtenerPorEmail(email);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar usuario",
        description = "Actualiza los datos de un usuario existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuarioActualizadoResult = usuarioService.actualizarUsuario(id, usuarioActualizado);
        if (usuarioActualizadoResult != null) {
            return ResponseEntity.ok(usuarioActualizadoResult);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Desactivar usuario",
        description = "Desactiva un usuario (eliminación lógica - no elimina datos)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario desactivado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> desactivarUsuario(@PathVariable Long id) {
        Usuario usuarioDesactivado = usuarioService.desactivarUsuario(id);
        if (usuarioDesactivado != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/permanente")
    @Operation(
        summary = "Eliminar usuario permanentemente",
        description = "Elimina un usuario de forma permanente (eliminación física)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioService.eliminarUsuario(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/verificar/{email}")
    @Operation(
        summary = "Verificar si email existe",
        description = "Verifica si un usuario con ese email ya existe en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Resultado de verificación obtenido")
    public ResponseEntity<Boolean> emailExiste(@PathVariable String email) {
        boolean existe = usuarioService.emailExiste(email);
        return ResponseEntity.ok(existe);
    }
}
