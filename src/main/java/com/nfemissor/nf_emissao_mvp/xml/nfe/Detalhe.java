package com.nfemissor.nf_emissao_mvp.xml.nfe;

import com.nfemissor.nf_emissao_mvp.xml.imposto.Imposto;
import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"produto", "imposto"})
public class Detalhe {

    @XmlAttribute(name = "nItem")
    private String numeroItem;

    @XmlElement(name = "prod", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Produto produto;

    @XmlElement(name = "imposto", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Imposto imposto;
}
