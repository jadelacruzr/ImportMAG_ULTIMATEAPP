package com.import_mag.importmag.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.import_mag.importmag.R;
import com.import_mag.importmag.fragments.detallesprods.ProductoDetallesFragment;
import com.import_mag.importmag.models.DetallesProds;
import com.import_mag.importmag.models.Productos;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;


import java.util.List;

public class TodosProductosAdapter extends RecyclerView.Adapter<TodosProductosAdapter.ProductosViewHolder> {
    Context context;
    List<Productos> ProductsList;

    public TodosProductosAdapter(Context context, List<Productos> ProductsList) {
        this.context = context;
        this.ProductsList = ProductsList;
    }

    @NotNull
    @Override
    public ProductosViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todos_productos, parent, false);
        return new ProductosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductosViewHolder holder, int position) {
        Picasso.get().load(ProductsList.get(position).getImages()).resize(220,220).into(holder.imageProds);
        holder.txtNombre_T.setText(ProductsList.get(position).getName_product());
       // holder.lieaTachar_T.setVisibility(View.INVISIBLE);

        String varFlag_Nuevo = ProductsList.get(position).getF_new();
        String varFlag_Descuento = ProductsList.get(position).getF_discount();
        String varFlag_Oferta = ProductsList.get(position).getF_on_sale();
//CONDICIONES PARA MOSTRAR LOS INDICADORES (NUEVO, OFERTA, DESCUENTO)
        if (varFlag_Nuevo != null) {
            holder.txtFlag_Nuevo_T.setText(varFlag_Nuevo);
        }else{
            holder.txtFlag_Nuevo_T.setVisibility(View.INVISIBLE);
        }
        if (varFlag_Descuento != null) {
            holder.txtFlag_Descuento_T.setText(varFlag_Descuento);
        }else{
            holder.txtFlag_Descuento_T.setVisibility(View.INVISIBLE);
        }
        if (varFlag_Oferta != null) {
            holder.txtFlag_Oferta_T.setText(varFlag_Oferta);
        }else{
            holder.txtFlag_Oferta_T.setVisibility(View.INVISIBLE);
        }
        /*/CONDICIONES PARA INDICAR EL PRECIO (PRECIO NORMAL O PRECIO DE VENTA  CON DESCUENTO)

        String var_PrecioVenta= ProductsList.get(position).getPrecioVenta();
        String var_PrecioNormal= ProductsList.get(position).getPrecioNormal();

        if(var_PrecioVenta.equals(var_PrecioNormal)){
            holder.txtPrecioNormal_T.setText(var_PrecioNormal);
            holder.txtPrecioVenta_T.setVisibility(View.INVISIBLE);
            holder.txtPrecioTachado_T.setVisibility(View.INVISIBLE);
        }else {
            holder.txtPrecioNormal_T.setVisibility(View.INVISIBLE);
            holder.txtPrecioTachado_T.setText(var_PrecioNormal);
            holder.txtPrecioVenta_T.setText("  "+var_PrecioVenta);
            holder.lieaTachar_T.setVisibility(View.VISIBLE);
        }*/
       /*   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View view) {
                Bundle i = new Bundle();
                i.putString("name",ProductsList.get(position).getName_product());
                i.putString("imageDefault",ProductsList.get(position).getImages());
                i.putInt("id_product",ProductsList.get(position).getId_product());
                getParentFragmentManager().setFragmentResult("key",i)

            }
        });*/
    }

    @Override
    public int getItemCount() {

        return ProductsList.size();
    }
    public static class ProductosViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProds;
        TextView txtNombre_T;
        ImageView lieaTachar_T;

        TextView txtFlag_Nuevo_T;
        TextView txtFlag_Descuento_T;
        TextView txtFlag_Oferta_T;

        TextView txtPrecioNormal_T;
        TextView txtPrecioVenta_T;
        TextView txtPrecioTachado_T;

        public ProductosViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageProds = itemView.findViewById(R.id.imgProdsTodos);
            txtNombre_T = itemView.findViewById(R.id.txt_NombreProducto);
/*
            txtPrecioNormal_T = itemView.findViewById(R.id.txt_PrecioNormal);
            txtPrecioVenta_T = itemView.findViewById(R.id.txt_PrecioVenta);
            txtPrecioTachado_T= itemView.findViewById(R.id.txt_PrecioTachado);*/

            txtFlag_Nuevo_T = itemView.findViewById(R.id.txtFlag_Nuevo);
            txtFlag_Descuento_T = itemView.findViewById(R.id.txtFlag_Descuento);
            txtFlag_Oferta_T = itemView.findViewById(R.id.txtFlag_Oferta);

           // lieaTachar_T=itemView.findViewById(R.id.img_linea);

        }
    }
}