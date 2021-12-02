package com.usa.autoparts2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Introduccion extends AppCompatActivity {
    private Button btniniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduccion);


        /**
         * agregar Logo en el menu principal
         */
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_autoparts);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        /**
         * declarar accion clic de boton pantalla principal
         */

        btniniciar = (Button) findViewById(R.id.button_Iniciar);
        btniniciar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /**
                 * ingresar al menu Productos
                 */

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Menu de Productos", Toast.LENGTH_LONG).show();
            }
        });








    }
}