package com.nfemissor.nf_emissao_mvp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 44)
    private String chaveAcesso;

    private String cnpjEmitente;
    private String cnpjDestinatario;
    private String numeroNf;
    private String serie;
    private BigDecimal valorTotal;
    private String status;

    private String caminhoXmlEnviado;
    private String caminhoXmlRetorno;

    private String codigoRetorno;
    private String motivoRetorno;
    private LocalDateTime dataEmissao;
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }
}
