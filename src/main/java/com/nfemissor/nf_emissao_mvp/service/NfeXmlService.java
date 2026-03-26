package com.nfemissor.nf_emissao_mvp.service;

import com.nfemissor.nf_emissao_mvp.xml.nfe.NFe;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Service;
import java.io.StringWriter;

@Service
public class NfeXmlService {

    public String gerarXml(NFe nfe) {
        try {
            JAXBContext context = JAXBContext.newInstance(NFe.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            StringWriter writer = new StringWriter();
            marshaller.marshal(nfe, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Erro ao gerar XML da NF-e", e);
        }
    }
}
