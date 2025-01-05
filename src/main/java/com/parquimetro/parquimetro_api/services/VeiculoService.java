package com.parquimetro.parquimetro_api.services;

import com.parquimetro.parquimetro_api.model.Veiculo;
import com.parquimetro.parquimetro_api.repositories.VeiculoRepository;
import com.parquimetro.parquimetro_api.exception.EntityNotFoundException;
import com.parquimetro.parquimetro_api.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private static final String CACHE_VEICULO = "veiculos";

    @Transactional
    @CachePut(value = CACHE_VEICULO, key = "#veiculo.placa")
    public Veiculo cadastrarVeiculo(Veiculo veiculo) {
        if (veiculoRepository.existsByPlaca(veiculo.getPlaca())) {
            throw new BusinessException("Já existe um veículo cadastrado com esta placa");
        }
        return veiculoRepository.save(veiculo);
    }

    @Cacheable(value = CACHE_VEICULO, key = "#id")
    public Veiculo buscarVeiculo(String id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
    }

    @Cacheable(value = CACHE_VEICULO, key = "#placa")
    public Veiculo buscarVeiculoPorPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CACHE_VEICULO, key = "#id"),
            @CacheEvict(value = CACHE_VEICULO, key = "#result.placa", condition = "#result != null")
    })
    public void removerVeiculo(String id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));

        veiculoRepository.deleteById(id);
    }
}