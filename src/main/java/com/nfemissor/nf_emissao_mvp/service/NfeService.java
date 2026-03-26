package com.nfemissor.nf_emissao_mvp.service;

import com.nfemissor.nf_emissao_mvp.dto.NfeRequest;
import com.nfemissor.nf_emissao_mvp.dto.NfeResponse;
import com.nfemissor.nf_emissao_mvp.model.NotaFiscal;
import com.nfemissor.nf_emissao_mvp.repository.NfeRepository;
import com.nfemissor.nf_emissao_mvp.xml.nfe.NFe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class NfeService {

    private final NfeRepository repository;
    private final NfeMontadorService montadorService;
    private final NfeXmlService xmlService;
    private final AssinaturaDigitalService assinaturaService;
    private final SefazService sefazService;
    private final XmlStorageService storageService;


    public NfeResponse emitir(NfeRequest request) {
        NFe nfe = montadorService.montar(request);
        String xml = xmlService.gerarXml(nfe);
        String xmlAssinado = assinaturaService.assinar(xml);
        String chaveAcesso = nfe.getInfNFe().getId().replace("NFe", "");

        log.info("NF-e {} assinada com sucesso", chaveAcesso);

        // Salva XML assinado localmente
        String caminhoXml = storageService.salvarLocal(chaveAcesso, xmlAssinado, "enviados");

        // Salva no banco antes de enviar
        BigDecimal valorTotal = request.getItens().stream()
                .map(i -> new BigDecimal(i.getValorTotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        NotaFiscal nota = new NotaFiscal();
        nota.setChaveAcesso(chaveAcesso);
        nota.setCnpjEmitente(request.getEmitente().getCnpj());
        nota.setCnpjDestinatario(request.getDestinatario().getCnpj() != null
                ? request.getDestinatario().getCnpj()
                : request.getDestinatario().getCpf());
        nota.setNumeroNf(request.getIdentificacao().getNumeroNf());
        nota.setSerie(request.getIdentificacao().getSerie());
        nota.setValorTotal(valorTotal);
        nota.setCaminhoXmlEnviado(caminhoXml);
        nota.setDataEmissao(LocalDateTime.now());
        nota.setStatus("ASSINADO");

        try {
            String respostaSefaz = sefazService.enviar(xmlAssinado);
            String caminhoRetorno = storageService.salvarLocal(chaveAcesso, respostaSefaz, "retornos");
            nota.setCaminhoXmlRetorno(caminhoRetorno);
            nota.setStatus("ENVIADO");
        } catch (Exception e) {
            log.error("Erro ao enviar para SEFAZ: {}", e.getMessage());
            nota.setStatus("ERRO_ENVIO");
            nota.setMotivoRetorno(e.getMessage());
        }

        repository.save(nota);
        log.info("NF-e {} salva no banco com status {}", chaveAcesso, nota.getStatus());

        NfeResponse response = new NfeResponse();
        response.setChaveAcesso(chaveAcesso);
        response.setStatus(nota.getStatus());
        response.setMensagem(nota.getStatus().equals("ENVIADO")
                ? "NF-e enviada para SEFAZ com sucesso"
                : "Erro ao enviar: " + nota.getMotivoRetorno());
        return response;
    }
}
