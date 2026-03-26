package com.nfemissor.nf_emissao_mvp.repository;

import com.nfemissor.nf_emissao_mvp.model.NotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface NfeRepository extends JpaRepository<NotaFiscal, Long> {

    Optional<NotaFiscal> findByChaveAcesso(String chaveAcesso);
}
