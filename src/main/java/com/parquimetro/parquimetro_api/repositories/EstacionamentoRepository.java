package com.parquimetro.parquimetro_api.repositories;

import com.parquimetro.parquimetro_api.model.Estacionamento;
import com.parquimetro.parquimetro_api.model.StatusEstacionamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface EstacionamentoRepository extends MongoRepository<Estacionamento, String> {

    List<Estacionamento> findByVeiculoIdAndStatus(String veiculoId, StatusEstacionamento status);

    @Query("{'veiculoId': ?0, 'status': 'ATIVO'}")
    Optional<Estacionamento> findEstacionamentoAtivoByVeiculoId(String veiculoId);

    List<Estacionamento> findByStatus(StatusEstacionamento status);

    @Query("{'status': 'ATIVO'}")
    List<Estacionamento> findAllAtivos();

    @Query(value = "{'status': 'ATIVO'}", count = true)
    long countAtivos();
}