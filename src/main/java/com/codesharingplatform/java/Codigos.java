package com.codesharingplatform.java;

// Classe para a geração de códigos
public class Codigos {
    private String codigo;

    public Codigos() {}

    public Codigos(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getWebCodigo() {
        return "<html>\n" +
                "<head>\n" +
                "    <title>Code</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <pre>\n" +
                getCodigo() +
                "</body>\n" +
                "</html>";
    }
}
