package com.codesharingplatform.java;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CodigoController {

    private final List<ApiCodigos> listaApi = new ArrayList<>();


    @GetMapping("/api/code/{i}")
    public ApiCodigos apiCodigo(@PathVariable int i) {
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
        ApiCodigos novoCodigo = new ApiCodigos(codigo.getCode());
        listaApi.add(novoCodigo);

        Id id = new Id();
        return id.toString();
    }

    @GetMapping("/api/code/latest")
    public List<ApiCodigos> apiCodigos() {
        List<ApiCodigos> listaApiReversa = new ArrayList<>(listaApi);

        Collections.reverse(listaApiReversa);

        return listaApiReversa.stream().limit(10).collect(Collectors.toList());
    }

    @GetMapping("/code/latest")
    public ModelAndView codigoLista() {
        ModelAndView mv = new ModelAndView();

        List<ApiCodigos> listaApiReversa = new ArrayList<>(listaApi);
        Collections.reverse(listaApiReversa);

        mv.setViewName("codigoLatest");
        mv.addObject("codigos",
                listaApiReversa.stream().limit(10).collect(Collectors.toList()));

        return mv;
    }
}
