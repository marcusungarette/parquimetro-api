package com.parquimetro.parquimetro_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "estacionamentos")
public class Estacionamento {
    @Id
    private String id;

    private String veiculoId;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private Double valorPago;
    private StatusEstacionamento status;
}
