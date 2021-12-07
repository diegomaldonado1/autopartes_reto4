package com.usa.autoparts2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.usa.autoparts2.MainActivity;
import com.usa.autoparts2.R;
import com.usa.autoparts2.casos_de_uso.ProductCase;
import com.usa.autoparts2.controlador.AdaptadorProductos;
import com.usa.autoparts2.controlador.MyOpenHelper;
import com.usa.autoparts2.modelo.Product;

import java.util.ArrayList;

  /*
    Codigo de referencia tomado de clase
     */

public class CatalogoActivity extends AppCompatActivity {

    ListView lvwProductos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);


        /**
         * agregar Logo en el menu principal
         */
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_autoparts);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Catalogo");
        /*

         */

        lvwProductos = (ListView) findViewById(R.id.lvwProductos);

        ArrayList<Product> misProductos = consultarProductos(this);
        AdaptadorProductos adapter = new AdaptadorProductos(getApplicationContext(), misProductos);
        lvwProductos.setAdapter(adapter);

        lvwProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                Product p = (Product) adapterView.getItemAtPosition(posicion);
                lanzarDialogProductoFavorito(p);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.favoritos:
                Intent intent = new Intent(CatalogoActivity.this, FavoritosActivity.class);
                startActivity(intent);
                return true;


            case R.id.MainActivity:
                Intent intent2 = new Intent(CatalogoActivity.this, MainActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }

    public ArrayList<Product> consultarProductos (Context context){
        ArrayList<Product> productos = new ArrayList<>();

        MyOpenHelper dataBase = new MyOpenHelper(CatalogoActivity.this);
        SQLiteDatabase db = dataBase.getReadableDatabase();

        Cursor c = dataBase.leerProductos(db);

        while (c.moveToNext()){
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre"));
            @SuppressLint("Range") int precio = c.getInt(c.getColumnIndex("precio"));
            @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex("imagen"));
            @SuppressLint("Range") int favorito = c.getInt(c.getColumnIndex("favorito"));

            if(favorito == 1){
                productos.add(new Product(id, nombre, precio, imagen, true));
            }else{
                productos.add(new Product(id, nombre, precio, imagen, false));
            }
        }
        return productos;

    }

    private void lanzarDialogProductoFavorito(Product p){
        MyOpenHelper dataBase = new MyOpenHelper(CatalogoActivity.this);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        DialogDeConfirmacion ddc = new DialogDeConfirmacion();
        ddc.setParametros(p.getId(), dataBase, db);
        ddc.show(getSupportFragmentManager(), "DialogDeConfirmacion");
    }


    public static class DialogDeConfirmacion extends DialogFragment {

        int idProducto;
        MyOpenHelper dataBase;
        SQLiteDatabase db;

        public void setParametros(int idProducto, MyOpenHelper dataBase, SQLiteDatabase db){
            this.idProducto = idProducto;
            this.dataBase = dataBase;
            this.db = db;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Está seguro de agregar a sus productos favoritos?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ProductCase.anadirFavorito(idProducto, dataBase, db);
                            Toast.makeText(getActivity(), "Usted agrego como favorito", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity(), "Usted ha cancelado la operación", Toast.LENGTH_LONG).show();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }





}