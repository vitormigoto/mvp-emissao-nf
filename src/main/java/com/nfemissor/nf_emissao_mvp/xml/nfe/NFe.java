package com.nfemissor.nf_emissao_mvp.xml.nfe;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlRootElement(name = "NFe", namespace = "http://www.portalfiscal.inf.br/nfe")
@XmlAccessorType(XmlAccessType.FIELD)
public class NFe {

    @XmlElement(name = "infNFe", namespace = "http://www.portalfiscal.inf.br/nfe")
    private InfNFe infNFe;
}