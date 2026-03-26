package com.nfemissor.nf_emissao_mvp.xml.nfe;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"detalhes"})
public class Pagamento {

    @XmlElement(name = "detPag", namespace = "http://www.portalfiscal.inf.br/nfe")
    private List<DetalhePagamento> detalhes;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"tipo", "valor"})
    public static class DetalhePagamento {

        @XmlElement(name = "tPag", namespace = "http://www.portalfiscal.inf.br/nfe")
        private String tipo;

        @XmlElement(name = "vPag", namespace = "http://www.portalfiscal.inf.br/nfe")
        private String valor;
    }
}
