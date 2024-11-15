package com.example.pruebaandroid;

public class Productos {
    int id;
    String nombre;
    int precio;
    int cantidad;
    String estado;

    public Productos () {}

    public Productos(int id, String nombre, int precio, int cantidad, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.estado = estado;
    }
}