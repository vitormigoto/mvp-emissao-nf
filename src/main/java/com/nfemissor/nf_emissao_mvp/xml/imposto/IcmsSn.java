package com.nfemissor.nf_emissao_mvp.xml.imposto;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"origem", "csosn"})
public class IcmsSn {

    @XmlElement(name = "orig", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String origem = "0";

    @XmlElement(name = "CSOSN", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String csosn = "102";
}
