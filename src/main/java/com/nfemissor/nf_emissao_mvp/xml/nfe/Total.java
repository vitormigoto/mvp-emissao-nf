package com.nfemissor.nf_emissao_mvp.xml.nfe;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Total {

    @XmlElement(name = "ICMSTot", namespace = "http://www.portalfiscal.inf.br/nfe")
    private IcmsTotal icmsTotal;
}
