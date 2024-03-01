package com.ugb.controlesbasicos;

public class vehiculos {
    String _id;
    String _rev;
    String idVehiculo;
    String marca;
    String motor;
    String chasis;
    String vin;
    String combustion;
    String urlFotoVehiculo;

    public vehiculos(String _id, String _rev, String idVehiculo, String marca, String motor, String chasis, String vin, String combustion, String urlFoto) {
        this._id = _id;
        this._rev = _rev;
        this.idVehiculo = idVehiculo;
        this.marca = marca;
        this.motor = motor;
        this.chasis = chasis;
        this.vin = vin;
        this.combustion = combustion;
        this.urlFotoVehiculo = urlFoto;
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
    public String getUrlFotoVehiculo() {
        return urlFotoVehiculo;
    }

    public void setUrlFotoVehiculo(String urlFotoVehiculo) {
        this.urlFotoVehiculo = urlFotoVehiculo;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCombustion() {
        return combustion;
    }

    public void setCombustion(String combustion) {
        this.combustion = combustion;
    }
}