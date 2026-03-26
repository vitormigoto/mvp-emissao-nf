package com.nfemissor.nf_emissao_mvp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sefaz")
public class SefazConfig {

    private String ambiente;
    private String uf;
    private String urlAutorizacao;
    private String urlRetorno;
    private String urlConsulta;
    private String urlInutilizacao;
    private String urlCancelamento;
}