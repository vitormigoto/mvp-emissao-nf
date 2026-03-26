package com.nfemissor.nf_emissao_mvp.xml.imposto;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"icms", "pis", "cofins"})
public class Imposto {

    @XmlElement(name = "ICMS", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Icms icms;

    @XmlElement(name = "PIS", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Pis pis;

    @XmlElement(name = "COFINS", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Cofins cofins;
}
