package com.nfemissor.nf_emissao_mvp.service;

import com.nfemissor.nf_emissao_mvp.dto.NfeRequest;
import com.nfemissor.nf_emissao_mvp.dto.NfeRequest.*;
import com.nfemissor.nf_emissao_mvp.xml.endereco.Endereco;
import com.nfemissor.nf_emissao_mvp.xml.imposto.*;
import com.nfemissor.nf_emissao_mvp.xml.nfe.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class NfeMontadorService {

    public NFe montar(NfeRequest request) {
        NFe nfe = new NFe();        
        InfNFe infNFe = new InfNFe();

        infNFe.setIde(montarIdentificacao(request.getIdentificacao()));
        infNFe.setEmit(montarEmitente(request.getEmitente()));
        infNFe.setDest(montarDestinatario(request.getDestinatario()));
        infNFe.setDet(montarDetalhes(request.getItens()));
        infNFe.setTotal(montarTotal(request.getItens()));
        infNFe.setTransp(new Transporte());

        String chaveAcesso = gerarChaveAcesso(infNFe);
        infNFe.setId("NFe" + chaveAcesso);
        infNFe.getIde().setDigitoVerificador(chaveAcesso.substring(43));

        infNFe.setPag(montarPagamento(request.getPagamento()));
        nfe.setInfNFe(infNFe);
        return nfe;
    }

    private Pagamento montarPagamento(NfeRequest.PagamentoDto dto) {
    List<Pagamento.DetalhePagamento> detalhes = dto.getDetalhes().stream().map(d -> {
        Pagamento.DetalhePagamento det = new Pagamento.DetalhePagamento();
        det.setTipo(d.getTipo());
        det.setValor(d.getValor());
        return det;
    }).toList();

    Pagamento pag = new Pagamento();
    pag.setDetalhes(detalhes);
    return pag;
}

    private Identificacao montarIdentificacao(IdentificacaoDto dto) {
        Identificacao ide = new Identificacao();
        ide.setCodigoUf(dto.getCodigoUf());
        ide.setCodigoNf(String.format("%08d", ThreadLocalRandom.current().nextInt(1, 99999999)));
        ide.setNaturezaOperacao(dto.getNaturezaOperacao());
        ide.setSerie(dto.getSerie());
        ide.setNumeroNf(dto.getNumeroNf());
        ide.setDataEmissao(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")));
        ide.setTipoOperacao(dto.getTipoOperacao());
        ide.setDestinoOperacao(dto.getDestinoOperacao());
        ide.setCodigoMunicipioFatoGerador(dto.getCodigoMunicipioFatoGerador());
        ide.setFormatoImpressao(dto.getFormatoImpressao());
        ide.setTipoEmissao(dto.getTipoEmissao());
        ide.setTipoAmbiente(dto.getTipoAmbiente());
        ide.setFinalidadeEmissao(dto.getFinalidadeEmissao());
        ide.setConsumidorFinal(dto.getConsumidorFinal());
        ide.setIndicadorPresenca(dto.getIndicadorPresenca());
        return ide;
    }

    private Emitente montarEmitente(EmitenteDto dto) {
        Emitente emit = new Emitente();
        emit.setCnpj(dto.getCnpj());
        emit.setRazaoSocial(dto.getRazaoSocial());
        emit.setNomeFantasia(dto.getNomeFantasia());
        emit.setInscricaoEstadual(dto.getInscricaoEstadual());
        emit.setCodigoRegimeTributario(dto.getCodigoRegimeTributario());
        emit.setEndereco(montarEndereco(dto.getEndereco()));
        return emit;
    }

    private Destinatario montarDestinatario(DestinatarioDto dto) {
        Destinatario dest = new Destinatario();
        dest.setCnpj(dto.getCnpj());
        dest.setCpf(dto.getCpf());
        dest.setNome(dto.getNome());
        dest.setIndicadorIe(dto.getIndicadorIe());
        dest.setEndereco(montarEndereco(dto.getEndereco()));
        return dest;
    }

    private Endereco montarEndereco(EnderecoDto dto) {
        Endereco end = new Endereco();
        end.setLogradouro(dto.getLogradouro());
        end.setNumero(dto.getNumero());
        end.setBairro(dto.getBairro());
        end.setCodigoMunicipio(dto.getCodigoMunicipio());
        end.setNomeMunicipio(dto.getNomeMunicipio());
        end.setUf(dto.getUf());
        end.setCep(dto.getCep());
        end.setCodigoPais(dto.getCodigoPais());
        end.setNomePais(dto.getNomePais());
        return end;
    }

    private List<Detalhe> montarDetalhes(List<ItemDto> itens) {
        List<Detalhe> detalhes = new ArrayList<>();
        for (int i = 0; i < itens.size(); i++) {
            ItemDto item = itens.get(i);

            Produto produto = new Produto();
            produto.setCodigo(item.getCodigo());
            produto.setDescricao(item.getDescricao());
            produto.setNcm(item.getNcm());
            produto.setCfop(item.getCfop());
            produto.setUnidadeComercial(item.getUnidade());
            produto.setQuantidade(item.getQuantidade());
            produto.setValorUnitario(item.getValorUnitario());
            produto.setValorTotal(item.getValorTotal());
            produto.setUnidadeTributavel(item.getUnidade());
            produto.setQuantidadeTributavel(item.getQuantidade());
            produto.setValorUnitarioTributavel(item.getValorUnitario());

            Imposto imposto = montarImposto(item.getImposto());

            Detalhe detalhe = new Detalhe();
            detalhe.setNumeroItem(String.valueOf(i + 1));
            detalhe.setProduto(produto);
            detalhe.setImposto(imposto);
            detalhes.add(detalhe);
        }
        return detalhes;
    }

    private Imposto montarImposto(ImpostoDto dto) {
        Imposto imposto = new Imposto();

        IcmsSn icmsSn = new IcmsSn();
        icmsSn.setOrigem(dto.getIcms().getOrigem());
        icmsSn.setCsosn(dto.getIcms().getCsosn());
        Icms icms = new Icms();
        icms.setIcmsSn(icmsSn);
        imposto.setIcms(icms);

        PisAliq pisAliq = new PisAliq();
        pisAliq.setCst(dto.getPis().getCst());
        pisAliq.setBaseCalculo(dto.getPis().getBaseCalculo());
        pisAliq.setAliquota(dto.getPis().getAliquota());
        pisAliq.setValor(dto.getPis().getValor());
        Pis pis = new Pis();
        pis.setPisAliq(pisAliq);
        imposto.setPis(pis);

        CofinsAliq cofinsAliq = new CofinsAliq();
        cofinsAliq.setCst(dto.getCofins().getCst());
        cofinsAliq.setBaseCalculo(dto.getCofins().getBaseCalculo());
        cofinsAliq.setAliquota(dto.getCofins().getAliquota());
        cofinsAliq.setValor(dto.getCofins().getValor());
        Cofins cofins = new Cofins();
        cofins.setCofinsAliq(cofinsAliq);
        imposto.setCofins(cofins);

        return imposto;
    }

    private Total montarTotal(List<ItemDto> itens) {
    BigDecimal valorProdutos = BigDecimal.ZERO;
    BigDecimal valorPis = BigDecimal.ZERO;
    BigDecimal valorCofins = BigDecimal.ZERO;

    for (ItemDto item : itens) {
        valorProdutos = valorProdutos.add(new BigDecimal(item.getValorTotal()));
        valorPis = valorPis.add(new BigDecimal(item.getImposto().getPis().getValor()));
        valorCofins = valorCofins.add(new BigDecimal(item.getImposto().getCofins().getValor()));
    }

    IcmsTotal icmsTotal = new IcmsTotal();
    icmsTotal.setValorProdutos(valorProdutos.toPlainString());
    icmsTotal.setValorPis(valorPis.toPlainString());
    icmsTotal.setValorCofins(valorCofins.toPlainString());
    icmsTotal.setValorNf(valorProdutos.toPlainString());

    Total total = new Total();
    total.setIcmsTotal(icmsTotal);
    return total;
}


    private String gerarChaveAcesso(InfNFe infNFe) {
        Identificacao ide = infNFe.getIde();
        String semDv = String.format("%2s%4s%14s%2s%3s%9s%1s%8s%1s",
                ide.getCodigoUf(),
                OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyMM")),
                infNFe.getEmit().getCnpj(),
                ide.getModelo(),
                ide.getSerie().length() < 3 ? String.format("%03d", Integer.parseInt(ide.getSerie())) : ide.getSerie(),
                String.format("%09d", Integer.parseInt(ide.getNumeroNf())),
                ide.getTipoEmissao(),
                ide.getCodigoNf(),
                "0"
        ).replace(" ", "0");

        int peso = 2;
        int soma = 0;
        for (int i = semDv.length() - 2; i >= 0; i--) {
            soma += Character.getNumericValue(semDv.charAt(i)) * peso;
            peso = peso == 9 ? 2 : peso + 1;
        }
        int resto = soma % 11;
        int dv = (resto < 2) ? 0 : 11 - resto;

        return semDv.substring(0, 43) + dv;
    }
}
