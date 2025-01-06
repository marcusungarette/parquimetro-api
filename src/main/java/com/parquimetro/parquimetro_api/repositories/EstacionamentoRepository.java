package com.parquimetro.parquimetro_api.repositories;

import com.parquimetro.parquimetro_api.model.Estacionamento;
import com.parquimetro.parquimetro_api.model.StatusEstacionamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstacionamentoRepository extends MongoRepository<Estacionamento, String> {

    @Query("{'veiculoId': ?0, 'status': ?1}")
    Page<Estacionamento> findByVeiculoIdAndStatus(String veiculoId, StatusEstacionamento status, Pageable pageable);

    @Query("{'veiculoId': ?0, 'status': 'ATIVO'}")
    Optional<Estacionamento> findEstacionamentoAtivoByVeiculoId(String veiculoId);

    @Query("{'status': 'ATIVO'}")
    Page<Estacionamento> findAllAtivos(Pageable pageable);

    @Query(value = "{'status': 'ATIVO'}", count = true)
    long countAtivos();
}