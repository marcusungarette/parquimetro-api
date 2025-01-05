package com.parquimetro.parquimetro_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Document(collection = "veiculos")
public class Veiculo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String placa;

    private String modelo;
    private String cor;
}
