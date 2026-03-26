package com.nfemissor.nf_emissao_mvp.xml.nfe;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"baseCalculo", "valorIcms", "valorIcmsDesonerado", "valorFcp",
        "baseCalculoSt", "valorSt", "valorFcpSt", "valorFcpStRetido",
        "valorProdutos", "valorFrete", "valorSeguro", "valorDesconto",
        "valorIi", "valorIpi", "valorIpiDevolvido", "valorPis",
        "valorCofins", "valorOutros", "valorNf"})
public class IcmsTotal {

    @XmlElement(name = "vBC", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String baseCalculo = "0.00";

    @XmlElement(name = "vICMS", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorIcms = "0.00";

    @XmlElement(name = "vICMSDeson", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorIcmsDesonerado = "0.00";

    @XmlElement(name = "vFCP", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorFcp = "0.00";

    @XmlElement(name = "vBCST", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String baseCalculoSt = "0.00";

    @XmlElement(name = "vST", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorSt = "0.00";

    @XmlElement(name = "vFCPST", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorFcpSt = "0.00";

    @XmlElement(name = "vFCPSTRet", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorFcpStRetido = "0.00";

    @XmlElement(name = "vProd", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorProdutos;

    @XmlElement(name = "vFrete", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorFrete = "0.00";

    @XmlElement(name = "vSeg", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorSeguro = "0.00";

    @XmlElement(name = "vDesc", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorDesconto = "0.00";

    @XmlElement(name = "vII", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorIi = "0.00";

    @XmlElement(name = "vIPI", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorIpi = "0.00";

    @XmlElement(name = "vIPIDevol", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorIpiDevolvido = "0.00";

    @XmlElement(name = "vPIS", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorPis = "0.00";

    @XmlElement(name = "vCOFINS", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorCofins = "0.00";

    @XmlElement(name = "vOutro", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorOutros = "0.00";

    @XmlElement(name = "vNF", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valorNf;
}
