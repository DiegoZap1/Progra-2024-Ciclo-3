package com.ugb.controlesbasicos;

public class productos {
    String _id;
    String _rev;
    String idProducto;
    String nombre;
    String marca;
    String descripcion;
    String costo;
    String precio;
    String stock;
    String urlFotoProducto;

    public productos(String _id, String _rev, String idProducto, String nombre, String marca, String descripcion, String costo, String precio, String stock, String urlFoto) {
        this._id = _id;
        this._rev = _rev;
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.marca = marca;
        this.descripcion = descripcion;
        this.costo = costo;
        this.precio = precio;
        this.stock = stock;
        this.urlFotoProducto = urlFoto;
    }

    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String get_rev() {
        return _rev;
    }
    public void set_rev(String _rev) {
        this._rev = _rev;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}