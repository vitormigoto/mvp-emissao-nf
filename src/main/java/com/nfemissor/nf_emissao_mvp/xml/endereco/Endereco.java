package com.nfemissor.nf_emissao_mvp.xml.endereco;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"logradouro", "numero", "bairro", "codigoMunicipio", "nomeMunicipio", "uf", "cep", "codigoPais", "nomePais"})
public class Endereco {

    @XmlElement(name = "xLgr", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String logradouro;

    @XmlElement(name = "nro", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String numero;

    @XmlElement(name = "xBairro", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String bairro;

    @XmlElement(name = "cMun", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String codigoMunicipio;

    @XmlElement(name = "xMun", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String nomeMunicipio;

    @XmlElement(name = "UF", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String uf;

    @XmlElement(name = "CEP", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String cep;

    @XmlElement(name = "cPais", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String codigoPais = "1058";

    @XmlElement(name = "xPais", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String nomePais = "Brasil";
}
