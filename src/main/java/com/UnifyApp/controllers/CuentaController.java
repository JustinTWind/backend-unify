package com.UnifyApp.controllers;

import com.UnifyApp.models.Cuenta;
import com.UnifyApp.services.CuentaService;
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
@RequestMapping("/api/cuentas")
@Tag(name = "Cuentas", description = "Gestión de cuentas bancarias")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @PostMapping
    @Operation(
        summary = "Crear nueva cuenta",
        description = "Crea una nueva cuenta bancaria en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody Cuenta cuenta) {
        try {
            Cuenta cuentaCreada = cuentaService.crearCuenta(cuenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(cuentaCreada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(
        summary = "Obtener todas las cuentas",
        description = "Retorna una lista completa de todas las cuentas registradas en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida exitosamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class)))
    public ResponseEntity<List<Cuenta>> obtenerTodas() {
        List<Cuenta> cuentas = cuentaService.obtenerTodas();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/activas")
    @Operation(
        summary = "Obtener cuentas activas",
        description = "Retorna solo las cuentas que están activas en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de cuentas activas obtenida",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class)))
    public ResponseEntity<List<Cuenta>> obtenerActivas() {
        List<Cuenta> cuentas = cuentaService.obtenerActivas();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener cuenta por ID",
        description = "Busca y retorna una cuenta específica usando su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class))),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<Cuenta> obtenerPorId(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaService.obtenerPorId(id);
        return cuenta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(
        summary = "Obtener cuentas de un usuario",
        description = "Retorna todas las cuentas asociadas a un usuario específico"
    )
    @ApiResponse(responseCode = "200", description = "Lista de cuentas del usuario obtenida",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class)))
    public ResponseEntity<List<Cuenta>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<Cuenta> cuentas = cuentaService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/usuario/{usuarioId}/activas")
    @Operation(
        summary = "Obtener cuentas activas de un usuario",
        description = "Retorna las cuentas activas asociadas a un usuario específico"
    )
    @ApiResponse(responseCode = "200", description = "Lista de cuentas activas del usuario obtenida",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class)))
    public ResponseEntity<List<Cuenta>> obtenerActivasPorUsuario(@PathVariable Long usuarioId) {
        List<Cuenta> cuentas = cuentaService.obtenerActivasPorUsuario(usuarioId);
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}/saldo")
    @Operation(
        summary = "Obtener saldo actual de una cuenta",
        description = "Retorna el saldo actual de una cuenta específica"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Saldo obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<Double> obtenerSaldoActual(@PathVariable Long id) {
        Double saldo = cuentaService.obtenerSaldoActual(id);
        if (saldo != null) {
            return ResponseEntity.ok(saldo);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar cuenta",
        description = "Actualiza los datos de una cuenta existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta actualizada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class))),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuentaActualizada) {
        Cuenta cuentaActualizadaResult = cuentaService.actualizarCuenta(id, cuentaActualizada);
        if (cuentaActualizadaResult != null) {
            return ResponseEntity.ok(cuentaActualizadaResult);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Desactivar cuenta",
        description = "Desactiva una cuenta (eliminación lógica - no elimina datos)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cuenta desactivada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<Void> desactivarCuenta(@PathVariable Long id) {
        Cuenta cuentaDesactivada = cuentaService.desactivarCuenta(id);
        if (cuentaDesactivada != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/permanente")
    @Operation(
        summary = "Eliminar cuenta permanentemente",
        description = "Elimina una cuenta de forma permanente (eliminación física)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cuenta eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        if (cuentaService.eliminarCuenta(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

