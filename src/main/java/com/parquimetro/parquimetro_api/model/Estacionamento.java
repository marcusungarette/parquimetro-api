package com.parquimetro.parquimetro_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Document(collection = "estacionamentos")
@CompoundIndexes({
        @CompoundIndex(name = "veiculo_status_idx",
                def = "{'veiculoId': 1, 'status': 1}",
                background = true),
        @CompoundIndex(name = "status_entrada_idx",
                def = "{'status': 1, 'entrada': -1}",
                background = true)
})
public class Estacionamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Indexed(background = true)
    private String veiculoId;

    private LocalDateTime entrada;
    private LocalDateTime saida;
    private Double valorPago;

    @Indexed(background = true)
    private StatusEstacionamento status;
}