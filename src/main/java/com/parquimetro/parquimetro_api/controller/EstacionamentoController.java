package com.parquimetro.parquimetro_api.controller;

import com.parquimetro.parquimetro_api.model.Estacionamento;
import com.parquimetro.parquimetro_api.services.EstacionamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/estacionamentos")
@RequiredArgsConstructor
@Tag(name = "Estacionamentos", description = "API para gerenciamento de estacionamentos")
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;

    @PostMapping("/iniciar")
    @Operation(summary = "Iniciar estacionamento", description = "Inicia um novo período de estacionamento para um veículo")
    public CompletableFuture<ResponseEntity<Estacionamento>> iniciarEstacionamento(@RequestParam String veiculoId) {
        return estacionamentoService.iniciarEstacionamento(veiculoId)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar estacionamento", description = "Finaliza um período de estacionamento e calcula o valor")
    public CompletableFuture<ResponseEntity<Estacionamento>> finalizarEstacionamento(@PathVariable String id) {
        return estacionamentoService.finalizarEstacionamento(id)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar estacionamento", description = "Busca um estacionamento pelo ID")
    public CompletableFuture<ResponseEntity<Estacionamento>> buscarEstacionamento(@PathVariable String id) {
        return estacionamentoService.buscarEstacionamento(id)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar estacionamentos ativos", description = "Lista todos os estacionamentos ativos com paginação")
    public ResponseEntity<Page<Estacionamento>> listarEstacionamentosAtivos(
            @Parameter(description = "Número da página (começa em 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação")
            @RequestParam(defaultValue = "entrada") String sort,
            @Parameter(description = "Direção da ordenação (ASC ou DESC)")
            @RequestParam(defaultValue = "DESC") String direction) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        return ResponseEntity.ok(estacionamentoService.listarEstacionamentosAtivos(pageRequest));
    }
}