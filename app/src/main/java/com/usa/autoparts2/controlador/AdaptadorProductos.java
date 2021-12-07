package com.usa.autoparts2.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.usa.autoparts2.R;
import com.usa.autoparts2.modelo.Product;

import java.util.ArrayList;

  /*
    Codigo de referencia tomado de clase
     */

public class AdaptadorProductos extends BaseAdapter {

    Context context;
    ArrayList<Product> productos;

    public AdaptadorProductos(Context context, ArrayList<Product> productos) {
        this.context = context;
        this.productos = productos;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int posicion) {
        return productos.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return productos.get(posicion).getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {

        View vista = view;

        LayoutInflater inflater = LayoutInflater.from(context);
        vista = inflater.inflate(R.layout.item_producto, null);

        ImageView imagen = (ImageView) vista.findViewById(R.id.imvImagen);
        TextView tvwNombre = (TextView) vista.findViewById(R.id.tvwNombre);
        TextView tvwPrecio = (TextView) vista.findViewById(R.id.tvwPrecio);

        tvwNombre.setText(productos.get(posicion).getNombre());
        tvwPrecio.setText("" +productos.get(posicion).getPrecio());
        imagen.setImageResource(productos.get(posicion).getImagen());

        return vista;
    }
}
