package com.UnifyApp.controllers;

import com.UnifyApp.models.Movimiento;
import com.UnifyApp.services.MovimientoService;
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
@RequestMapping("/api/movimientos")
@Tag(name = "Movimientos", description = "Gestión de movimientos financieros (ingresos y gastos)")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    @Operation(
        summary = "Crear nuevo movimiento",
        description = "Crea un nuevo movimiento financiero (ingreso o gasto) en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Movimiento creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movimiento.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Movimiento> crearMovimiento(@RequestBody Movimiento movimiento) {
        try {
            Movimiento movimientoCreado = movimientoService.crearMovimiento(movimiento);
            return ResponseEntity.status(HttpStatus.CREATED).body(movimientoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los movimientos",
        description = "Retorna una lista completa de todos los movimientos registrados en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida exitosamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movimiento.class)))
    public ResponseEntity<List<Movimiento>> obtenerTodos() {
        List<Movimiento> movimientos = movimientoService.obtenerTodos();
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/activos")
    @Operation(
        summary = "Obtener movimientos activos",
        description = "Retorna solo los movimientos que están activos en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de movimientos activos obtenida",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movimiento.class)))
    public ResponseEntity<List<Movimiento>> obtenerActivos() {
        List<Movimiento> movimientos = movimientoService.obtenerActivos();
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener movimiento por ID",
        description = "Busca y retorna un movimiento específico usando su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movimiento encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movimiento.class))),
        @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    public ResponseEntity<Movimiento> obtenerPorId(@PathVariable Long id) {
        Optional<Movimiento> movimiento = movimientoService.obtenerPorId(id);
        return movimiento.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(
        summary = "Obtener movimientos por categoría",
        description = "Retorna todos los movimientos asociados a una categoría específica"
    )
    @ApiResponse(responseCode = "200", description = "Lista de movimientos de la categoría obtenida",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movimiento.class)))
    public ResponseEntity<List<Movimiento>> obtenerPorCategoria(@PathVariable Long categoriaId) {
        List<Movimiento> movimientos = movimientoService.obtenerPorCategoria(categoriaId);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/cuenta/{cuentaId}")
    @Operation(
        summary = "Obtener movimientos por cuenta",
        description = "Retorna todos los movimientos asociados a una cuenta específica"
    )
    @ApiResponse(responseCode = "200", description = "Lista de movimientos de la cuenta obtenida",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movimiento.class)))
    public ResponseEntity<List<Movimiento>> obtenerPorCuenta(@PathVariable Long cuentaId) {
        List<Movimiento> movimientos = movimientoService.obtenerPorCuenta(cuentaId);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/cuenta/{cuentaId}/activos")
    @Operation(
        summary = "Obtener movimientos activos por cuenta",
        description = "Retorna los movimientos activos asociados a una cuenta específica"
    )
    @ApiResponse(responseCode = "200", description = "Lista de movimientos activos de la cuenta obtenida",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movimiento.class)))
    public ResponseEntity<List<Movimiento>> obtenerActivosPorCuenta(@PathVariable Long cuentaId) {
        List<Movimiento> movimientos = movimientoService.obtenerActivosPorCuenta(cuentaId);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/cuenta/{cuentaId}/total")
    @Operation(
        summary = "Calcular total de movimientos",
        description = "Retorna la suma total de todos los movimientos de una cuenta"
    )
    @ApiResponse(responseCode = "200", description = "Total de movimientos calculado")
    public ResponseEntity<Double> calcularTotalMovimientos(@PathVariable Long cuentaId) {
        Double total = movimientoService.calcularTotalMovimientos(cuentaId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/cuenta/{cuentaId}/ingresos")
    @Operation(
        summary = "Calcular total de ingresos",
        description = "Retorna la suma total de todos los ingresos de una cuenta"
    )
    @ApiResponse(responseCode = "200", description = "Total de ingresos calculado")
    public ResponseEntity<Double> calcularIngresos(@PathVariable Long cuentaId) {
        Double ingresos = movimientoService.calcularIngresos(cuentaId);
        return ResponseEntity.ok(ingresos);
    }

    @GetMapping("/cuenta/{cuentaId}/gastos")
    @Operation(
        summary = "Calcular total de gastos",
        description = "Retorna la suma total de todos los gastos de una cuenta"
    )
    @ApiResponse(responseCode = "200", description = "Total de gastos calculado")
    public ResponseEntity<Double> calcularGastos(@PathVariable Long cuentaId) {
        Double gastos = movimientoService.calcularGastos(cuentaId);
        return ResponseEntity.ok(gastos);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar movimiento",
        description = "Actualiza los datos de un movimiento existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movimiento actualizado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movimiento.class))),
        @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    public ResponseEntity<Movimiento> actualizarMovimiento(@PathVariable Long id, @RequestBody Movimiento movimientoActualizado) {
        Movimiento movimientoActualizadoResult = movimientoService.actualizarMovimiento(id, movimientoActualizado);
        if (movimientoActualizadoResult != null) {
            return ResponseEntity.ok(movimientoActualizadoResult);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Desactivar movimiento",
        description = "Desactiva un movimiento (eliminación lógica - no elimina datos)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Movimiento desactivado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    public ResponseEntity<Void> desactivarMovimiento(@PathVariable Long id) {
        Movimiento movimientoDesactivado = movimientoService.desactivarMovimiento(id);
        if (movimientoDesactivado != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/permanente")
    @Operation(
        summary = "Eliminar movimiento permanentemente",
        description = "Elimina un movimiento de forma permanente (eliminación física)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Movimiento eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        if (movimientoService.eliminarMovimiento(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
