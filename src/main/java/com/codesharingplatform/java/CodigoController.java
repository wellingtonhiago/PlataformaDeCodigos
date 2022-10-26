package com.codesharingplatform.java;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
public class CodigoController {

    private final List<ApiCodigos> listaApi = List.of();

    @GetMapping("/api/code/{i}")
    public ApiCodigos apiCodigos(@PathVariable int i) {

        return listaApi.get(i-1);
    }

    @GetMapping("/code/{i}")
    public ModelAndView codigo(@PathVariable int i) {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("codigo");
        mv.addObject("codigoData", listaApi.get(i-1).getDate());
        mv.addObject("codigo", listaApi.get(i-1).getCode());

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
        listaApi.add(codigo);
        int i = listaApi.indexOf(codigo);
        listaApi.get(i).setDate(localDateTime.format(formatter));
        listaApi.get(i).setCode(codigo.getCode());
        return "{}";
    }

}
