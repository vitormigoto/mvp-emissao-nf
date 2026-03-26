package com.nfemissor.nf_emissao_mvp.xml.nfe;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"codigo", "codigoBarras", "descricao", "ncm", "cfop",
        "unidadeComercial", "quantidade", "valorUnitario", "valorTotal",
        "codigoBarrasTributavel", "unidadeTributavel", "quantidadeTributavel",
        "valorUnitarioTributavel", "indicadorTotal"})
public class Produto {

    @XmlElement(name = "cProd", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String codigo;

    @XmlElement(name = "cEAN", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String codigoBarras = "SEM GTIN";

    @XmlElement(name = "xProd", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String descricao;

    @XmlElement(name = "NCM", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String ncm;

    @XmlElement(name = "CFOP", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String cfop;

    @XmlElement(name = "uCom", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String unidadeComercial;

    @XmlElement(name = "qCom", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String quantidade;

    @XmlElement(name = "vUnCom", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorUnitario;

    @XmlElement(name = "vProd", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorTotal;

    @XmlElement(name = "cEANTrib", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String codigoBarrasTributavel = "SEM GTIN";

    @XmlElement(name = "uTrib", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String unidadeTributavel;

    @XmlElement(name = "qTrib", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String quantidadeTributavel;

    @XmlElement(name = "vUnTrib", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorUnitarioTributavel;

    @XmlElement(name = "indTot", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String indicadorTotal = "1";
}
