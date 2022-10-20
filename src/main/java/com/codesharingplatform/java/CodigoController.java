package com.codesharingplatform.java;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
public class CodigoController {

    private static final String DATA_FORMATADA= "yyyy/MM/dd HH:mm:ss";

    private final String codigo = "public static void main(String[] args) {\n    SpringApplication.run(CodeSharingPlatform.class, args);\n}";

    private final List<ApiCodigos> listaApi = List.of(new ApiCodigos(codigo));

    @GetMapping("/api/code")
    public ApiCodigos apiCodigos() {
        return listaApi.get(0);
    }

    @GetMapping("/code")
    public ModelAndView codigo() {
        ModelAndView mv = new ModelAndView();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(DATA_FORMATADA);
        String data = now.format(formato);

        mv.setViewName("codigo");
        mv.addObject("codigoData", data);
        mv.addObject("codigo", listaApi.get(0).getCode());

        return mv;
    }

    @PostMapping("/api/code/new")
    public String postCodigo(@RequestBody String codigo) {
        listaApi.get(0).setCode(codigo);
        return "{}";
    }

}
