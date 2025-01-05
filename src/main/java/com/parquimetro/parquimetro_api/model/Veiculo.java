package com.parquimetro.parquimetro_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.io.Serializable;

@Data
@Document(collection = "veiculos")
public class Veiculo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Indexed(unique = true)
    private String placa;

    private String modelo;
    private String cor;
}