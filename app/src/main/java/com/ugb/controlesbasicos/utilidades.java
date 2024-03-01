package com.ugb.controlesbasicos;

import java.util.Base64;

public class utilidades {
    static String urlConsulta = "http://192.168.83.202:5984/diego/_design/productos/_view/productos";
    static String urlMto = "http://192.168.83.202:5984/diego/";
    static String user = "admin";
    static String passwd = "admin";
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user +":"+ passwd).getBytes());
    public String generarIdUnico(){
        return java.util.UUID.randomUUID().toString();
    }
}