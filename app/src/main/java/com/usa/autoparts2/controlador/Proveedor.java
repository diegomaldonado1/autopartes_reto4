package com.usa.autoparts2.controlador;

import com.usa.autoparts2.R;
import com.usa.autoparts2.modelo.Product;
import com.usa.autoparts2.modelo.Sucursales;

import java.util.ArrayList;

public class Proveedor {

    /*
    Codigo de referencia tomado de clase
     */

    public static ArrayList<Product> cargarProductos = getProductos();
    public static ArrayList<Sucursales> cargarSucursales = getSucursales();

    public static ArrayList<Product> getProductos(){
        ArrayList<Product> productos = new ArrayList<>();
        productos.add(new Product("KIT_MOTOR", 100000, R.drawable.partesmotor));
        productos.add(new Product("KIT pastillas", 50000, R.drawable.pastillasparafrenos));
        productos.add(new Product("RIN", 1500000, R.drawable.rin));
        productos.add(new Product("FILTRO", 30000, R.drawable.filtros ));
        productos.add(new Product("NEUMATICOS", 500000, R.drawable.neumatico ));

        return productos;
    }

    public static ArrayList<Sucursales> getSucursales(){
        ArrayList<Sucursales> sucursales = new ArrayList<>();
        sucursales.add(new Sucursales("BOGOTA", "Direccion: calle 63 # 24 12", "Telefono: 8954762"  , 4.682268, -74.090305, R.drawable.taller_1));
        sucursales.add(new Sucursales("CALI", "Direccion: calle 9 # 8 7", "Telefono: 8754857" ,  3.427712, -76.533912, R.drawable.taller_2));
        sucursales.add(new Sucursales("MEDELLIN", "Direccion: calle 2 # 30 7", "Telefono: 9845621" ,  6.261212, -75.568378, R.drawable.taller_3));
        return sucursales;
    }

}
