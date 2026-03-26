package com.nfemissor.nf_emissao_mvp.xml.imposto;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"cst", "baseCalculo", "aliquota", "valor"})
public class CofinsAliq {

    @XmlElement(name = "CST", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String cst = "01";

    @XmlElement(name = "vBC", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String baseCalculo;

    @XmlElement(name = "pCOFINS", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String aliquota = "3.00";

    @XmlElement(name = "vCOFINS", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String valor;
}
