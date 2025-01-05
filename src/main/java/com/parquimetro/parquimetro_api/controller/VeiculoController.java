package com.parquimetro.parquimetro_api.controller;

import com.parquimetro.parquimetro_api.model.Veiculo;
import com.parquimetro.parquimetro_api.services.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
@Tag(name = "Veículos", description = "API para gerenciamento de veículos")
public class VeiculoController {

    private final VeiculoService veiculoService;

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
}