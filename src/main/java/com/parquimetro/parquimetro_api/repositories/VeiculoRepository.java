package com.parquimetro.parquimetro_api.repositories;

import com.parquimetro.parquimetro_api.model.Veiculo;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface VeiculoRepository extends MongoRepository<Veiculo, String> {
    Optional<Veiculo> findByPlaca(String placa);
    boolean existsByPlaca(String placa);
}