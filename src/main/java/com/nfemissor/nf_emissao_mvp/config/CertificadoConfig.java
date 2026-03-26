package com.nfemissor.nf_emissao_mvp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "certificado")
public class CertificadoConfig {

    private String caminho;
    private String senha;
}
