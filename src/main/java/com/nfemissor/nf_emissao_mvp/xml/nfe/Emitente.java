package com.nfemissor.nf_emissao_mvp.xml.nfe;

import com.nfemissor.nf_emissao_mvp.xml.endereco.Endereco;
import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"cnpj", "razaoSocial", "nomeFantasia", "endereco", "inscricaoEstadual", "codigoRegimeTributario"})
public class Emitente {

    @XmlElement(name = "CNPJ", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String cnpj;

    @XmlElement(name = "xNome", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String razaoSocial;

    @XmlElement(name = "xFant", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String nomeFantasia;

    @XmlElement(name = "enderEmit", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Endereco endereco;

    @XmlElement(name = "IE", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String inscricaoEstadual;

    @XmlElement(name = "CRT", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String codigoRegimeTributario;
}
