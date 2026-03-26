package com.nfemissor.nf_emissao_mvp.xml.nfe;

import com.nfemissor.nf_emissao_mvp.xml.endereco.Endereco;
import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"cnpj", "cpf", "nome", "endereco", "indicadorIe"})
public class Destinatario {

    @XmlElement(name = "CNPJ", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String cnpj;

    @XmlElement(name = "CPF", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String cpf;

    @XmlElement(name = "xNome", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String nome;

    @XmlElement(name = "enderDest", namespace = "http://www.portalfiscal.inf.br/nfe")
    private Endereco endereco;

    @XmlElement(name = "indIEDest", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String indicadorIe;
}
