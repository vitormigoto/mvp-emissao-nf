package com.nfemissor.nf_emissao_mvp.xml.nfe;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"ide", "emit", "dest", "det", "total", "transp", "pag"})
public class InfNFe {

    @XmlAttribute(name = "versao")
    private String versao = "4.00";

    @XmlAttribute(name = "Id")
    private String id;

    @XmlElement(name = "ide", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Identificacao ide;

    @XmlElement(name = "emit", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Emitente emit;

    @XmlElement(name = "dest", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Destinatario dest;

    @XmlElement(name = "det", namespace = "http://www.portalfiscal.inf.br/nfe")
    private List<Detalhe> det;

    @XmlElement(name = "total", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Total total;

    @XmlElement(name = "transp", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Transporte transp;

    @XmlElement(name = "pag", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Pagamento pag;
}
