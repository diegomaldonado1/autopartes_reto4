package com.usa.autoparts2.casos_de_uso;

import android.database.sqlite.SQLiteDatabase;

import com.usa.autoparts2.controlador.MyOpenHelper;

  /*
    Codigo de referencia tomado de clase
     */

public class ProductCase {

    public static void anadirFavorito(int id, MyOpenHelper dataBase, SQLiteDatabase db){
        try{
            dataBase.seleccionarComoFavorito(id, db);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBase.close();
        }
    }

    public static void quitarFavorito(int id, MyOpenHelper dataBase, SQLiteDatabase db){
        try{
            dataBase.quitarComoFavorito(id, db);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBase.close();
        }
    }
}
