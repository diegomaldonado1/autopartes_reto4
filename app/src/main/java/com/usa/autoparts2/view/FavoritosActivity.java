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

public class FavoritosActivity extends AppCompatActivity {

    ListView lvwFavoritos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);


        /**
         * agregar Logo en el menu principal
         */
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_autoparts);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Productos Favoritos");

        /*

         */

        lvwFavoritos = (ListView) findViewById(R.id.lvwProductosFavoritos);

        ArrayList<Product> productos = consultarProductosFavoritos(this);

        AdaptadorProductos adapter = new AdaptadorProductos(getApplicationContext(), productos);
        lvwFavoritos.setAdapter(adapter);

        lvwFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                Product p = (Product) adapterView.getItemAtPosition(posicion);
                lanzarDialogquitarFavorito(p);

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
            case R.id.catalogo:
                Intent intent = new Intent(FavoritosActivity.this, CatalogoActivity.class);
                startActivity(intent);
                return true;
            case R.id.MainActivity:
                Intent intent2 = new Intent(FavoritosActivity.this, MainActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public ArrayList<Product> consultarProductosFavoritos(Context context){
        ArrayList<Product> productos = new ArrayList<>();

        MyOpenHelper dataBase = new MyOpenHelper(FavoritosActivity.this);
        SQLiteDatabase db = dataBase.getReadableDatabase();

        Cursor c = dataBase.leerProductosFavoritos(db);



        while (c.moveToNext()){
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre"));
            @SuppressLint("Range") int precio = c.getInt(c.getColumnIndex("precio"));
            @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex("imagen"));

            productos.add(new Product(id, nombre, precio, imagen));
        }

        return productos;

    }

    private void lanzarDialogquitarFavorito(Product p){
        MyOpenHelper dataBase = new MyOpenHelper(FavoritosActivity.this);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        DialogDeConfirmacion_quitar ddc = new DialogDeConfirmacion_quitar();
        ddc.setParametros(p.getId(), dataBase, db);
        ddc.show(getSupportFragmentManager(), "DialogDeConfirmacion_quitar");

    }


    public static class DialogDeConfirmacion_quitar extends DialogFragment {

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
            builder.setMessage("¿Está seguro de quitar de sus productos favoritos?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ProductCase.quitarFavorito(idProducto, dataBase, db);
                            Toast.makeText(getActivity(), "Usted elimino el producto de favoritos ", Toast.LENGTH_LONG).show();

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