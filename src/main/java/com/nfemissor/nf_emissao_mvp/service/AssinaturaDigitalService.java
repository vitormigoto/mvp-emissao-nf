package com.nfemissor.nf_emissao_mvp.service;

import com.nfemissor.nf_emissao_mvp.config.CertificadoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssinaturaDigitalService {

    private final CertificadoConfig config;

    public String assinar(String xml) {
        try {
            Document document = parseXml(xml);
            KeyStore keyStore = carregarCertificado();

            String alias = keyStore.aliases().nextElement();
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, config.getSenha().toCharArray());
            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);

            assinarDocumento(document, privateKey, certificate);

            return documentToString(document);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao assinar XML da NF-e: " + e.getMessage(), e);
        }
    }

    private KeyStore carregarCertificado() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(config.getCaminho())) {
            keyStore.load(fis, config.getSenha().toCharArray());
        }
        return keyStore;
    }

    private void assinarDocumento(Document document, PrivateKey privateKey, X509Certificate certificate) throws Exception {
        XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");

        NodeList infNFeList = document.getElementsByTagNameNS("http://www.portalfiscal.inf.br/nfe", "infNFe");
        Element infNFe = (Element) infNFeList.item(0);
        String id = infNFe.getAttribute("Id");

        infNFe.setIdAttribute("Id", true);
        List<Transform> transforms = new ArrayList<>();
        transforms.add(factory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null));
        transforms.add(factory.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (TransformParameterSpec) null));

        Reference reference = factory.newReference(
                "#" + id,
                factory.newDigestMethod(DigestMethod.SHA1, null),
                transforms,
                null,
                null
        );

        SignedInfo signedInfo = factory.newSignedInfo(
                factory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
                factory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                Collections.singletonList(reference)
        );

        KeyInfoFactory keyInfoFactory = factory.getKeyInfoFactory();
        X509Data x509Data = keyInfoFactory.newX509Data(Collections.singletonList(certificate));
        KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));

        XMLSignature signature = factory.newXMLSignature(signedInfo, keyInfo);

        DOMSignContext signContext = new DOMSignContext(privateKey, document.getDocumentElement());
        signature.sign(signContext);
    }

    private Document parseXml(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
    }

    private String documentToString(Document document) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty("omit-xml-declaration", "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        return writer.toString();
    }
}
