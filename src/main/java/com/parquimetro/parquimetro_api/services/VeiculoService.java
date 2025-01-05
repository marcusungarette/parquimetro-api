package com.parquimetro.parquimetro_api.services;

import com.parquimetro.parquimetro_api.model.Veiculo;
import com.parquimetro.parquimetro_api.repositories.VeiculoRepository;
import com.parquimetro.parquimetro_api.exception.EntityNotFoundException;
import com.parquimetro.parquimetro_api.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    @Transactional
    public Veiculo cadastrarVeiculo(Veiculo veiculo) {
        if (veiculoRepository.existsByPlaca(veiculo.getPlaca())) {
            throw new BusinessException("Já existe um veículo cadastrado com esta placa");
        }
        return veiculoRepository.save(veiculo);
    }

    public Veiculo buscarVeiculo(String id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
    }

    @Transactional
    public void removerVeiculo(String id) {
        if (!veiculoRepository.existsById(id)) {
            throw new EntityNotFoundException("Veículo não encontrado");
        }
        veiculoRepository.deleteById(id);
    }
}
