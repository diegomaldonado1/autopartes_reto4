package com.usa.autoparts2.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.usa.autoparts2.modelo.Product;
import com.usa.autoparts2.modelo.Sucursales;

import java.util.ArrayList;

  /*
    Codigo de referencia tomado de clase
     */

public class MyOpenHelper extends SQLiteOpenHelper {

    private static final String PRODUCTOS_TABLE_CREATE = "CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, precio INTEGER, imagen INTEGER, favorito BOOLEAN) ";
    private static final String SUCURSAL_TABLE_CREATE = "CREATE TABLE sucursales (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, direccion TEXT, telefono TEXT , latitud REAL, longitud REAL, imagen INTEGER) ";

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "compras.db";

    public MyOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        crearTablaProductos(db);
        crearTablaSucursales(db);

        ArrayList<Product> productos = Proveedor.getProductos();
        for (Product p : productos) {
            insertarProducto(p, db);
        }

        ArrayList<Sucursales> sucursales = Proveedor.getSucursales();
        for(Sucursales s: sucursales){
            insertarSucursales(s, db);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS productos ");
        onCreate(db);
    }

    public void crearTablaProductos(SQLiteDatabase db){
        db.execSQL(PRODUCTOS_TABLE_CREATE);
    }
    public void crearTablaSucursales(SQLiteDatabase db){
        db.execSQL(SUCURSAL_TABLE_CREATE);
    }



    public void insertarProducto(Product p, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("nombre", p.getNombre());
        cv.put("precio", p.getPrecio());
        cv.put("imagen", p.getImagen());
        cv.put("favorito", p.isFavorito());
        db.insert("productos", null, cv);
    }

    public void insertarSucursales(Sucursales s, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("nombre", s.getNombre());
        cv.put("direccion", s.getDireccion());
        cv.put("telefono", s.getTelefono());
        cv.put("latitud", s.getLatitud());
        cv.put("longitud", s.getLongitud());
        cv.put("imagen", s.getImagen());

        db.insert("sucursales", null, cv);
    }

    public void seleccionarComoFavorito(int id, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("favorito", true);
        cv.put("id", id);
        String[] arg = new String[]{""+id};
        db.update("productos", cv, "id=?", arg);
    }

    public void quitarComoFavorito(int id, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("favorito", false);
        cv.put("id", id);
        String[] arg = new String[]{""+id};
        db.update("productos", cv, "id=?", arg);
    }

    public void borrarProducto(int id, SQLiteDatabase db){
        String[] arg = new String[]{""+id};
        db.delete("productos", "id=?", arg);
    }


    public Cursor leerProductos(SQLiteDatabase db){
        return db.query("productos", null, null, null, null, null, null);
    }

    public Cursor leerSucursales(SQLiteDatabase db){
        return db.query("sucursales", null, null, null, null, null, null);
    }

    public Cursor leerProductosFavoritos(SQLiteDatabase db){


        return db.rawQuery("SELECT * FROM productos WHERE favorito = true", null);
    }
}
