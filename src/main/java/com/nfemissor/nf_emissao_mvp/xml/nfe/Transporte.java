package com.nfemissor.nf_emissao_mvp.xml.nfe;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Transporte {

    @XmlElement(name = "modFrete", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String modalidadeFrete = "9"; // 9 = sem frete
}
