package com.codesharingplatform.java;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
public class CodigoController {

    private final String codigo = "public static void main(String[] args) {\n    SpringApplication.run(CodeSharingPlatform.class, args);\n}";

    private final List<ApiCodigos> listaApi = List.of(new ApiCodigos(codigo));

    @GetMapping("/api/code")
    public ApiCodigos apiCodigos() {
        return listaApi.get(0);
    }

    @GetMapping("/code")
    public ModelAndView codigo() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("codigo");
        mv.addObject("codigoData", listaApi.get(0).getDate());
        mv.addObject("codigo", listaApi.get(0).getCode());

        return mv;
    }

    @GetMapping("/code/new")
    public ModelAndView newCodigo() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("novoCodigo");
        return mv;
    }

    @PostMapping("/api/code/new")
    public String postCodigo(@RequestBody ApiCodigos codigo) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        listaApi.get(0).setDate(localDateTime.format(formatter));
        listaApi.get(0).setCode(codigo.getCode());
        return "{}";
    }

}
