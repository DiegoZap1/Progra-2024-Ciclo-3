package com.ugb.controlesbasicos;

public class productos {
    String idProducto;
    String nombreProducto;
    String marca;
    String descripcion;
    String precio;
    String urlFotoProducto;

    public productos(String idProducto, String nombreProducto, String marca, String descripcion, String precio, String urlFoto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
        this.urlFotoProducto = urlFoto;
    }

    public String getUrlFotoProducto() {
        return urlFotoProducto;
    }

    public void setUrlFotoProducto(String urlFotoProducto) {
        this.urlFotoProducto = urlFotoProducto;
    }
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}