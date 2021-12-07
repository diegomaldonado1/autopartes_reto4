package com.usa.autoparts2.modelo;
/*
esta clase declara el formato de producto
 */
public class Product {
    private int id;
    private String nombre;
    private int precio;
    private int imagen;
    private boolean favorito;

    public Product(int id, String nombre, int precio, int imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.favorito = false;
    }

    public Product(int id, int imagen) {
        this.id = id;
        this.imagen = imagen;
    }

    public Product(int id, String nombre, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Product(String nombre, int precio, int imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }

    public Product(int id, String nombre, int precio, int imagen, boolean favorito) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.favorito = favorito;
    }

    public Product( String nombre, int precio, int imagen, boolean favorito) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.favorito = favorito;
    }


    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }


}
