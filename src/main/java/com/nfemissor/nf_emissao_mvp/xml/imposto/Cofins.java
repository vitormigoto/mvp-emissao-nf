package com.nfemissor.nf_emissao_mvp.xml.imposto;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Cofins {

    @XmlElement(name = "COFINSAliq", namespace = "http://www.portalfiscal.inf.br/nfe")
    private CofinsAliq cofinsAliq;
}
