package com.nfemissor.nf_emissao_mvp.xml.nfe;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"codigoUf", "codigoNf", "naturezaOperacao", "modelo", "serie",
        "numeroNf", "dataEmissao", "tipoOperacao", "destinoOperacao",
        "codigoMunicipioFatoGerador", "formatoImpressao", "tipoEmissao",
        "digitoVerificador", "tipoAmbiente", "finalidadeEmissao",
        "consumidorFinal", "indicadorPresenca", "processoEmissao", "versaoProcesso"})
public class Identificacao {

    @XmlElement(name = "cUF", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String codigoUf;

    @XmlElement(name = "cNF", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String codigoNf;

    @XmlElement(name = "natOp", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String naturezaOperacao;

    @XmlElement(name = "mod", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String modelo = "55";

    @XmlElement(name = "serie", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String serie;

    @XmlElement(name = "nNF", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String numeroNf;

    @XmlElement(name = "dhEmi", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String dataEmissao;

    @XmlElement(name = "tpNF", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String tipoOperacao;

    @XmlElement(name = "idDest", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String destinoOperacao;

    @XmlElement(name = "cMunFG", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String codigoMunicipioFatoGerador;

    @XmlElement(name = "tpImp", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String formatoImpressao;

    @XmlElement(name = "tpEmis", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String tipoEmissao = "1";

    @XmlElement(name = "cDV", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String digitoVerificador;

    @XmlElement(name = "tpAmb", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String tipoAmbiente = "2";

    @XmlElement(name = "finNFe", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String finalidadeEmissao = "1";

    @XmlElement(name = "indFinal", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String consumidorFinal;

    @XmlElement(name = "indPres", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String indicadorPresenca;

    @XmlElement(name = "procEmi", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String processoEmissao = "0";

    @XmlElement(name = "verProc", namespace = "http://www.portalfiscal.inf.br/nfe")
    private String versaoProcesso = "1.0.0";
}
