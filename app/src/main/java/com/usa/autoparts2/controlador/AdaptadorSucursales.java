package com.usa.autoparts2.controlador;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usa.autoparts2.R;
import com.usa.autoparts2.modelo.Sucursales;

import java.util.ArrayList;

public class AdaptadorSucursales
        extends RecyclerView.Adapter<AdaptadorSucursales.ViewHolderSucursales>
        implements View.OnClickListener{

    ArrayList<Sucursales> sucursales;
    private View.OnClickListener listener;

    public AdaptadorSucursales(ArrayList<Sucursales> sucursales) {
        this.sucursales = sucursales;
    }

    @NonNull
    @Override
    public ViewHolderSucursales onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sucursal, null, false);
        vista.setOnClickListener(this);
        return new AdaptadorSucursales.ViewHolderSucursales(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSucursales holder, int position) {
        holder.imvImagenSuc.setImageResource(sucursales.get(position).getImagen());
        holder.tvwNombreSuc.setText(sucursales.get(position).getNombre());
        holder.tvwDireccionSuc.setText(sucursales.get(position).getDireccion());
        holder.tvwTelefonoSuc.setText(sucursales.get(position).getTelefono());
    }

    @Override
    public int getItemCount() {
        return sucursales.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolderSucursales extends RecyclerView.ViewHolder {

        ImageView imvImagenSuc;
        TextView tvwNombreSuc;
        TextView tvwDireccionSuc;
        TextView tvwTelefonoSuc;

        public ViewHolderSucursales(@NonNull View itemView) {
            super(itemView);

            imvImagenSuc = (ImageView) itemView.findViewById(R.id.imvImagenSuc);
            tvwNombreSuc = (TextView) itemView.findViewById(R.id.tvwNombreSuc);
            tvwDireccionSuc = (TextView) itemView.findViewById(R.id.tvwDireccionSuc);
            tvwTelefonoSuc = (TextView) itemView.findViewById(R.id.tvwTelefonoSuc);
        }
    }
}
