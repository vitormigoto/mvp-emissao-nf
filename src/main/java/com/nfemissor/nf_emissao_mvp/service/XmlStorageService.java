package com.nfemissor.nf_emissao_mvp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class XmlStorageService {

    @Value("${xml.diretorio}")
    private String diretorioBase;

    public String salvarLocal(String chaveAcesso, String xml, String tipo) {
        try {
            // Organiza em pastas: xmls/202603/enviados/ ou xmls/202603/retornos/
            String anoMes = chaveAcesso.substring(2, 6);
            Path diretorio = Paths.get(diretorioBase, anoMes, tipo);
            Files.createDirectories(diretorio);

            String nomeArquivo = chaveAcesso + ".xml";
            Path caminhoArquivo = diretorio.resolve(nomeArquivo);
            Files.writeString(caminhoArquivo, xml, StandardCharsets.UTF_8);

            log.info("XML salvo em: {}", caminhoArquivo);
            return caminhoArquivo.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar XML localmente: " + e.getMessage(), e);
        }
    }
}
