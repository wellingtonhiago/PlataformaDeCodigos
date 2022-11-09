package com.codesharingplatform.java;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ApiCodigos {

    private String code;
    private String date;

    public ApiCodigos(){

    }
    public ApiCodigos(String code) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String date = now.format(formato);

        this.date = date;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
