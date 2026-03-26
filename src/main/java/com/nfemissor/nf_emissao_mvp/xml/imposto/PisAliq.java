package com.nfemissor.nf_emissao_mvp.xml.imposto;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"cst", "baseCalculo", "aliquota", "valor"})
public class PisAliq {

    @XmlElement(name = "CST", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String cst = "01";

    @XmlElement(name = "vBC", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String baseCalculo;

    @XmlElement(name = "pPIS", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String aliquota = "0.65";

    @XmlElement(name = "vPIS", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valor;
}
