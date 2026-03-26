// controller/ — Recebe as requisições HTTP (POST, GET, etc.) e repassa para o service

package com.nfemissor.nf_emissao_mvp.controller;

import com.nfemissor.nf_emissao_mvp.dto.NfeRequest;
import com.nfemissor.nf_emissao_mvp.dto.NfeResponse;
import com.nfemissor.nf_emissao_mvp.service.NfeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nfe")
@RequiredArgsConstructor
public class NfeController {

    private final NfeService nfeService;

    @PostMapping("/emitir")
    public ResponseEntity<NfeResponse> emitir(@Valid @RequestBody NfeRequest request) {
        return ResponseEntity.ok(nfeService.emitir(request));
    }
}