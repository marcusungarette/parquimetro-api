package com.parquimetro.parquimetro_api.model;

public enum StatusEstacionamento {
    ATIVO, //Veículo está estacionado
    FINALIZADO, //Estacionamento concluído e pago
    PENDENTE_PAGAMENTO //Estacionamento finalizado mas aguardando pagamento
}