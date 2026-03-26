package com.nfemissor.nf_emissao_mvp.service;

import com.nfemissor.nf_emissao_mvp.config.CertificadoConfig;
import com.nfemissor.nf_emissao_mvp.config.SefazConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class SefazService {

    private final SefazConfig sefazConfig;
    private final CertificadoConfig certificadoConfig;

    public String enviar(String xmlAssinado) {
        try {
            String envelope = montarEnvelopeSoap(xmlAssinado);
            log.info("Envelope SOAP:\n{}", envelope);

            SSLSocketFactory sslFactory = criarSslFactory();
            return enviarParaSefaz(envelope, sslFactory);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar NF-e para SEFAZ: " + e.getMessage(), e);
        }
    }

    private String montarEnvelopeSoap(String xmlAssinado) {
        return """
                <soap12:Envelope xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                    <soap12:Body>
                        <nfeDadosMsg xmlns="http://www.portalfiscal.inf.br/nfe/wsdl/NFeAutorizacao4">
                            <enviNFe versao="4.00" xmlns="http://www.portalfiscal.inf.br/nfe">
                                <idLote>1</idLote>
                                <indSinc>1</indSinc>
                                %s
                            </enviNFe>
                        </nfeDadosMsg>
                    </soap12:Body>
                </soap12:Envelope>
                """.formatted(xmlAssinado);
    }

    private SSLSocketFactory criarSslFactory() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(certificadoConfig.getCaminho())) {
            keyStore.load(fis, certificadoConfig.getSenha().toCharArray());
        }

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, certificadoConfig.getSenha().toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init((KeyStore) null); // usa os certificados padrão do Java

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());

        return sslContext.getSocketFactory();
    }

    private String enviarParaSefaz(String envelope, SSLSocketFactory sslFactory) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) URI.create(sefazConfig.getUrlAutorizacao()).toURL().openConnection();

        if (connection instanceof HttpsURLConnection httpsConn) {
            httpsConn.setSSLSocketFactory(sslFactory);
        }

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
        connection.setDoOutput(true);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(envelope.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = connection.getResponseCode();
        log.info("SEFAZ response code: {}", responseCode);

        InputStream is = responseCode >= 400 ? connection.getErrorStream() : connection.getInputStream();
        String resposta = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        log.info("SEFAZ resposta:\n{}", resposta);

        return resposta;
    }
}
