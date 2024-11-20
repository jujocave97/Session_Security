package com.es.seguridadsession.dto;

public class ProductoDTO {

    private String nombre;
    private int stock;
    private boolean precio;

    public ProductoDTO(){}

    public ProductoDTO(String nombre, int stock, boolean precio) {
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isPrecio() {
        return precio;
    }

    public void setPrecio(boolean precio) {
        this.precio = precio;
    }
}
