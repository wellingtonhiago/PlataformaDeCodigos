package com.codesharingplatform.java;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CodigoController {

    private final String codigo = "public static void main(String[] args) {\n    SpringApplication.run(CodeSharingPlatform.class, args);\n}";

    private final List<ApiCodigos> listaApi = List.of(new ApiCodigos(codigo));

    @GetMapping("/api/code")
    public ApiCodigos getApiCodigos() {
        return listaApi.get(0);
    }


    private final List<Codigos> listaCodigo = List.of(new Codigos(codigo));

    @GetMapping("/code")
    public String getCodigos() {
        return listaCodigo.get(0).getWebCodigo();
    }

}
