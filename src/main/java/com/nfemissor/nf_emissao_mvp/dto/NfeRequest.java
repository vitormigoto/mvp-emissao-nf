package com.nfemissor.nf_emissao_mvp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class NfeRequest {

    @NotNull @Valid
    private IdentificacaoDto identificacao;

    @NotNull @Valid
    private EmitenteDto emitente;

    @NotNull @Valid
    private DestinatarioDto destinatario;

    @NotEmpty @Valid
    private List<ItemDto> itens;

    @Data
    public static class IdentificacaoDto {
        @NotBlank
        private String codigoUf;
        @NotBlank
        private String naturezaOperacao;
        @NotBlank
        private String serie;
        @NotBlank
        private String numeroNf;
        @NotBlank
        private String tipoOperacao;
        @NotBlank
        private String destinoOperacao;
        @NotBlank
        private String codigoMunicipioFatoGerador;
        private String formatoImpressao = "1";
        private String tipoEmissao = "1";
        private String tipoAmbiente = "2";
        private String finalidadeEmissao = "1";
        private String consumidorFinal = "1";
        private String indicadorPresenca = "1";
    }

    @Data
    public static class EmitenteDto {
        @NotBlank
        private String cnpj;
        @NotBlank
        private String razaoSocial;
        private String nomeFantasia;
        @NotBlank
        private String inscricaoEstadual;
        @NotBlank
        private String codigoRegimeTributario;
        @NotNull @Valid
        private EnderecoDto endereco;
    }

    @Data
    public static class DestinatarioDto {
        private String cnpj;
        private String cpf;
        @NotBlank
        private String nome;
        private String indicadorIe = "9";
        @NotNull @Valid
        private EnderecoDto endereco;
    }

    @Data
    public static class EnderecoDto {
        @NotBlank
        private String logradouro;
        @NotBlank
        private String numero;
        @NotBlank
        private String bairro;
        @NotBlank
        private String codigoMunicipio;
        @NotBlank
        private String nomeMunicipio;
        @NotBlank
        private String uf;
        @NotBlank
        private String cep;
        private String codigoPais = "1058";
        private String nomePais = "Brasil";
        private String telefone;
    }

    @Data
    public static class ItemDto {
        @NotBlank
        private String codigo;
        @NotBlank
        private String descricao;
        @NotBlank
        private String ncm;
        @NotBlank
        private String cfop;
        @NotBlank
        private String unidade;
        @NotNull
        private String quantidade;
        @NotNull
        private String valorUnitario;
        @NotNull
        private String valorTotal;
        @NotNull @Valid
        private ImpostoDto imposto;
    }

    @Data
    public static class ImpostoDto {
        @NotNull @Valid
        private IcmsDto icms;
        @NotNull @Valid
        private PisDto pis;
        @NotNull @Valid
        private CofinsDto cofins;
    }

    @Data
    public static class IcmsDto {
        @NotBlank
        private String origem;
        @NotBlank
        private String csosn;
    }

    @Data
    public static class PisDto {
        @NotBlank
        private String cst;
        @NotNull
        private String baseCalculo;
        @NotNull
        private String aliquota;
        @NotNull
        private String valor;
    }

    @Data
    public static class CofinsDto {
        @NotBlank
        private String cst;
        @NotNull
        private String baseCalculo;
        @NotNull
        private String aliquota;
        @NotNull
        private String valor;
    }

    @NotNull @Valid
    private PagamentoDto pagamento;

    @Data
    public static class PagamentoDto {
        @NotEmpty @Valid
        private List<DetalhePagamentoDto> detalhes;
    }

    @Data
    public static class DetalhePagamentoDto {
        @NotBlank
        private String tipo; // 01=Dinheiro, 02=Cheque, 03=Cartão Crédito, 04=Cartão Débito, 15=Boleto, 90=Sem pagamento
        @NotNull
        private String valor;
    }

}
