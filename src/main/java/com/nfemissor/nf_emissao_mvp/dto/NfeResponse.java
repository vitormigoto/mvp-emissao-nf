package com.nfemissor.nf_emissao_mvp.dto;

import lombok.Data;

@Data
public class NfeResponse {

    private String chaveAcesso;
    private String status;
    private String mensagem;
}