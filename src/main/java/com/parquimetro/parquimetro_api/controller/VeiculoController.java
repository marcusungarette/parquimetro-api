package com.parquimetro.parquimetro_api.controller;

import com.parquimetro.parquimetro_api.model.Veiculo;
import com.parquimetro.parquimetro_api.model.Estacionamento;
import com.parquimetro.parquimetro_api.model.StatusEstacionamento;
import com.parquimetro.parquimetro_api.services.VeiculoService;
import com.parquimetro.parquimetro_api.services.EstacionamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
@Tag(name = "Veículos", description = "API para gerenciamento de veículos")
public class VeiculoController {

    private final VeiculoService veiculoService;
    private final EstacionamentoService estacionamentoService;

    @PostMapping
    @Operation(summary = "Cadastrar veículo", description = "Cadastra um novo veículo no sistema")
    @ApiResponse(responseCode = "201", description = "Veículo cadastrado com sucesso")
    public ResponseEntity<Veiculo> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(veiculoService.cadastrarVeiculo(veiculo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veículo por ID", description = "Retorna um veículo baseado no ID fornecido")
    public ResponseEntity<Veiculo> buscarVeiculo(@PathVariable String id) {
        return ResponseEntity.ok(veiculoService.buscarVeiculo(id));
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Buscar veículo por placa", description = "Retorna um veículo baseado na placa fornecida")
    public ResponseEntity<Veiculo> buscarVeiculoPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(veiculoService.buscarVeiculoPorPlaca(placa));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover veículo", description = "Remove um veículo do sistema")
    @ApiResponse(responseCode = "204", description = "Veículo removido com sucesso")
    public ResponseEntity<Void> removerVeiculo(@PathVariable String id) {
        veiculoService.removerVeiculo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/estacionamentos")
    @Operation(summary = "Histórico de estacionamentos", description = "Lista o histórico de estacionamentos do veículo")
    public CompletableFuture<ResponseEntity<Page<Estacionamento>>> listarHistoricoEstacionamentos(
            @Parameter(description = "ID do veículo")
            @PathVariable String id,
            @Parameter(description = "Status do estacionamento (opcional)")
            @RequestParam(required = false) StatusEstacionamento status,
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

        return estacionamentoService.buscarEstacionamentosPorVeiculo(id, status, pageRequest)
                .thenApply(ResponseEntity::ok);
    }
}