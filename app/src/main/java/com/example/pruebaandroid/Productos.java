package com.example.pruebaandroid;

public class Productos {
    String nombre;
    String precio;
    String cantidad;
    String estado;

    public Productos () {}

    public Productos(String nombre, String precio, String cantidad, String estado) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.estado = estado;
    }
}