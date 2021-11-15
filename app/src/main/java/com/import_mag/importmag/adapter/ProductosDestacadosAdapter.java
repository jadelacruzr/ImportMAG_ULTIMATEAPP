package com.import_mag.importmag.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.import_mag.importmag.R;
import com.import_mag.importmag.models.ProductosDestacados;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;


import java.util.List;

public class ProductosDestacadosAdapter extends RecyclerView.Adapter<ProductosDestacadosAdapter.ProductosDestacadosViewHolder> {
    Context context;
    List<ProductosDestacados> featuredProductsList;

    public ProductosDestacadosAdapter(Context context, List<ProductosDestacados> featuredProductsList) {
        this.context = context;
        this.featuredProductsList = featuredProductsList;
    }

    @NotNull
    @Override
    public ProductosDestacadosViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.productos_oferta, parent, false);
        return new ProductosDestacadosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductosDestacadosViewHolder holder, int position) {
        Picasso.get().load(featuredProductsList.get(position).getImages()).into(holder.imageProdsDescuento);
        holder.txtNombre.setText(featuredProductsList.get(position).getName_product());
        //holder.lieaTachar.setVisibility(View.INVISIBLE);

        String varFlag_Nuevo = featuredProductsList.get(position).getF_new();
        String varFlag_Descuento = featuredProductsList.get(position).getF_discount();
        String varFlag_Oferta = featuredProductsList.get(position).getF_on_sale();
//CONDICIONES PARA MOSTRAR LOS INDICADORES (NUEVO, OFERTA, DESCUENTO)
        if (varFlag_Nuevo != null) {
            holder.txtFlag_Nuevo.setText(varFlag_Nuevo);
        }else{
            holder.txtFlag_Nuevo.setVisibility(View.INVISIBLE);
        }
        if (varFlag_Descuento != null) {
            holder.txtFlag_Descuento.setText(varFlag_Descuento);
        }else{
            holder.txtFlag_Descuento.setVisibility(View.INVISIBLE);
        }
        if (varFlag_Oferta != null) {
            holder.txtFlag_Oferta.setText(varFlag_Oferta);
        }else{
            holder.txtFlag_Oferta.setVisibility(View.INVISIBLE);
        }
        /*/CONDICIONES PARA INDICAR EL PRECIO (PRECIO NORMAL O PRECIO DE VENTA  CON DESCUENTO)

        String var_PrecioVenta= featuredProductsList.get(position).getPrecioVenta();
        String var_PrecioNormal= featuredProductsList.get(position).getPrecioNormal();

        if(var_PrecioVenta.equals(var_PrecioNormal)){
            holder.txtPrecioNormal.setText(var_PrecioNormal);
            holder.txtPrecioVenta.setVisibility(View.INVISIBLE);
            holder.txtPrecioTachado.setVisibility(View.INVISIBLE);
        }else {
            holder.txtPrecioNormal.setVisibility(View.INVISIBLE);
            holder.txtPrecioTachado.setText(var_PrecioNormal);
            holder.txtPrecioVenta.setText("  "+var_PrecioVenta);
            holder.lieaTachar.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public int getItemCount() {

        return featuredProductsList.size();
    }
    public static class ProductosDestacadosViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProdsDescuento;
        TextView txtNombre;
        ImageView lieaTachar;

        TextView txtFlag_Nuevo;
        TextView txtFlag_Descuento;
        TextView txtFlag_Oferta;

    /*    TextView txtPrecioNormal;
        TextView txtPrecioVenta;
        TextView txtPrecioTachado;
*/
        public ProductosDestacadosViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageProdsDescuento = itemView.findViewById(R.id.imgProdsDescuento);
            txtNombre = itemView.findViewById(R.id.txt_NombreProducto);
/*
            txtPrecioNormal = itemView.findViewById(R.id.txt_PrecioNormal);
            txtPrecioVenta = itemView.findViewById(R.id.txt_PrecioVenta);
            txtPrecioTachado= itemView.findViewById(R.id.txt_PrecioTachado);*/

            txtFlag_Nuevo = itemView.findViewById(R.id.txtFlag_Nuevo);
            txtFlag_Descuento = itemView.findViewById(R.id.txtFlag_Descuento);
            txtFlag_Oferta = itemView.findViewById(R.id.txtFlag_Oferta);

            //lieaTachar=itemView.findViewById(R.id.img_linea);

        }
    }
}