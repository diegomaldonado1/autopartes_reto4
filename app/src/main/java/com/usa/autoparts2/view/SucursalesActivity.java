package com.usa.autoparts2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.usa.autoparts2.MainActivity;
import com.usa.autoparts2.R;
import com.usa.autoparts2.controlador.AdaptadorSucursales;
import com.usa.autoparts2.controlador.MyOpenHelper;
import com.usa.autoparts2.modelo.Sucursales;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class SucursalesActivity extends AppCompatActivity {

    RecyclerView rcvSuccursales;
    ArrayList<Sucursales> sucursales;
    ProgressDialog progressDialog;

    private MapView myOpenMapView;
    private MapController myMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursales);
        /**
         * agregar Logo en el menu principal
         */
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_autoparts);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Sucursales");


        rcvSuccursales = (RecyclerView) findViewById(R.id.rcvSucursales);
        rcvSuccursales.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(SucursalesActivity.this);
        progressDialog.setMessage("Cargando Información...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new ConsultaDeSucursales().execute();

        wait(1000);
        cargarMapa_inicial();

    }

    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }


    public  class ConsultaDeSucursales extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                sucursales = new ArrayList<>();
                //Thread.sleep(2000);
                MyOpenHelper dataBase = new MyOpenHelper(SucursalesActivity.this);
                SQLiteDatabase db = dataBase.getReadableDatabase();

                Cursor c = dataBase.leerSucursales(db);

                Sucursales sucursalTemporal = null;
                while (c.moveToNext()){
                    @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
                    @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre"));
                    @SuppressLint("Range") String direccion = c.getString(c.getColumnIndex("direccion"));
                    @SuppressLint("Range") String telefono = c.getString(c.getColumnIndex("telefono"));
                    @SuppressLint("Range") double latitud = c.getDouble(c.getColumnIndex("latitud"));
                    @SuppressLint("Range") double longitud = c.getDouble(c.getColumnIndex("longitud"));
                    @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex("imagen"));

                    sucursalTemporal = new Sucursales(id, nombre, direccion,telefono, latitud, longitud, imagen);
                    sucursales.add(sucursalTemporal);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();

            /*
            AdaptadorProductos adapter = new AdaptadorProductos(getApplicationContext(), productos);
            lvwProductos.setAdapter(adapter);
            *
             */

            AdaptadorSucursales adaptador = new AdaptadorSucursales(sucursales);

            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cargarMapa(sucursales.get(rcvSuccursales.getChildAdapterPosition(view)));
                }
            });

            rcvSuccursales.setAdapter(adaptador);

        }


    }

    private void cargarMapa_inicial(){

        for (int x = 0; x < sucursales.size(); x++) {
            cargarMapa(sucursales.get(x));
        }



        GeoPoint puntoSucursal = new GeoPoint( 4.677972, -74.088135);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        myOpenMapView = (MapView) findViewById(R.id.mapa);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setCenter(puntoSucursal);
        myMapController.setZoom(7);
        myOpenMapView.setMultiTouchControls(true);

        final MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), myOpenMapView);

        //myOpenMapView.getOverlays().add(myLocationoverlay); //No añadir si no quieres una marca
        myLocationoverlay.enableMyLocation();
        myLocationoverlay.runOnFirstFix(new Runnable() {
            public void run() {
                myMapController.animateTo(myLocationoverlay.getMyLocation());
            }
        });

        //ArrayList<OverlayItem> puntos = new ArrayList<OverlayItem>();
        //puntos.add(new OverlayItem(sucursal.getDireccion(), sucursal.getNombre(), puntoSucursal));

        ItemizedIconOverlay.OnItemGestureListener<OverlayItem> tap = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemLongPress(int arg0, OverlayItem arg1) {
                return false;
            }
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }


        };

      //  ItemizedOverlayWithFocus<OverlayItem> capa = new ItemizedOverlayWithFocus<OverlayItem>(this, puntos, tap);
       // capa.setFocusItemsOnTap(true);
        //myOpenMapView.getOverlays().add(capa);




    }

    private void cargarMapa(Sucursales sucursal){
        GeoPoint puntoSucursal = new GeoPoint(sucursal.getLatitud(), sucursal.getLongitud());
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        myOpenMapView = (MapView) findViewById(R.id.mapa);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setCenter(puntoSucursal);
        myMapController.setZoom(17);
        myOpenMapView.setMultiTouchControls(true);

        final MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), myOpenMapView);

        myOpenMapView.getOverlays().add(myLocationoverlay); //No añadir si no quieres una marca
        myLocationoverlay.enableMyLocation();
        myLocationoverlay.runOnFirstFix(new Runnable() {
            public void run() {
                myMapController.animateTo(myLocationoverlay.getMyLocation());
            }
        });

        ArrayList<OverlayItem> puntos = new ArrayList<OverlayItem>();
        puntos.add(new OverlayItem(sucursal.getNombre() , sucursal.getDireccion(),sucursal.getTelefono() , puntoSucursal));

        ItemizedIconOverlay.OnItemGestureListener<OverlayItem> tap = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemLongPress(int arg0, OverlayItem arg1) {
                return false;
            }
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }
        };

        ItemizedOverlayWithFocus<OverlayItem> capa = new ItemizedOverlayWithFocus<OverlayItem>(this, puntos, tap);
        capa.setFocusItemsOnTap(true);
        myOpenMapView.getOverlays().add(capa);

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
                Intent intent = new Intent(SucursalesActivity.this, FavoritosActivity.class);
                startActivity(intent);
                return true;


            case R.id.MainActivity:
                Intent intent2 = new Intent(SucursalesActivity.this, MainActivity.class);
                startActivity(intent2);
                return true;

            case R.id.catalogo:
                Intent intent_2 = new Intent(SucursalesActivity.this, CatalogoActivity.class);
                startActivity(intent_2);
                return true;



            default:
                return super.onOptionsItemSelected(item);


        }

    }


}