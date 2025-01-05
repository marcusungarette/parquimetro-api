package com.parquimetro.parquimetro_api.services;

import com.parquimetro.parquimetro_api.model.Estacionamento;
import com.parquimetro.parquimetro_api.model.StatusEstacionamento;
import com.parquimetro.parquimetro_api.repositories.EstacionamentoRepository;
import com.parquimetro.parquimetro_api.repositories.VeiculoRepository;
import com.parquimetro.parquimetro_api.exception.EntityNotFoundException;
import com.parquimetro.parquimetro_api.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {

    private final EstacionamentoRepository estacionamentoRepository;
    private final VeiculoRepository veiculoRepository;

    private static final double VALOR_HORA = 15.0;
    private static final double VALOR_MINIMO = 3.0;

    @Transactional
    public Estacionamento iniciarEstacionamento(String veiculoId) {
        // Verifica se o veículo existe
        if (!veiculoRepository.existsById(veiculoId)) {
            throw new EntityNotFoundException("Veículo não encontrado");
        }

        // Verifica se já existe estacionamento ativo
        estacionamentoRepository.findEstacionamentoAtivoByVeiculoId(veiculoId)
                .ifPresent(e -> {
                    throw new BusinessException("Veículo já possui estacionamento ativo");
                });

        var estacionamento = new Estacionamento();
        estacionamento.setVeiculoId(veiculoId);
        estacionamento.setEntrada(LocalDateTime.now());
        estacionamento.setStatus(StatusEstacionamento.ATIVO);

        return estacionamentoRepository.save(estacionamento);
    }

    @Transactional
    public Estacionamento finalizarEstacionamento(String id) {
        var estacionamento = estacionamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estacionamento não encontrado"));

        if (estacionamento.getStatus() != StatusEstacionamento.ATIVO) {
            throw new BusinessException("Estacionamento não está ativo");
        }

        estacionamento.setSaida(LocalDateTime.now());
        estacionamento.setValorPago(calcularValor(estacionamento.getEntrada(), estacionamento.getSaida()));
        estacionamento.setStatus(StatusEstacionamento.FINALIZADO);

        return estacionamentoRepository.save(estacionamento);
    }

    public Estacionamento buscarEstacionamento(String id) {
        return estacionamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estacionamento não encontrado"));
    }

    public List<Estacionamento> listarEstacionamentosAtivos() {
        return estacionamentoRepository.findAllAtivos();
    }

    private double calcularValor(LocalDateTime entrada, LocalDateTime saida) {
        long minutos = Duration.between(entrada, saida).toMinutes();
        double horas = Math.ceil(minutos / 60.0);
        return Math.max(VALOR_MINIMO, horas * VALOR_HORA);
    }

    public long contarEstacionamentosAtivos() {
        return estacionamentoRepository.countAtivos();
    }
}