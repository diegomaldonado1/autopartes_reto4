package com.usa.autoparts2.controlador;

import com.usa.autoparts2.R;
import com.usa.autoparts2.modelo.Product;

import java.util.ArrayList;

public class ProveedorProductos {

    /*
    Codigo de referencia tomado de clase
     */

    public static ArrayList<Product> cargarProductos = getProductos();

    public static ArrayList<Product> getProductos(){
        ArrayList<Product> productos = new ArrayList<>();
        productos.add(new Product("KIT_MOTOR", 100000, R.drawable.partesmotor));
        productos.add(new Product("KIT pastillas", 50000, R.drawable.pastillasparafrenos));
        productos.add(new Product("RIN", 1500000, R.drawable.rin));
        productos.add(new Product("FILTRO", 30000, R.drawable.filtros ));
        productos.add(new Product("NEUMATICOS", 500000, R.drawable.neumatico ));

        return productos;
    }
}
