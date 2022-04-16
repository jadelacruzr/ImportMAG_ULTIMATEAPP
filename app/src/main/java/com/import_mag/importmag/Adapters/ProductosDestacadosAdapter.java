package com.import_mag.importmag.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.import_mag.importmag.Activities.DetallesProductosActivity;
import com.import_mag.importmag.Models.ProdsDestacado;
import com.import_mag.importmag.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductosDestacadosAdapter extends RecyclerView.Adapter<ProductosDestacadosAdapter.ProductosDestacadosViewHolder> {
    Context context;
    List<ProdsDestacado> ProductsList;

    public ProductosDestacadosAdapter(Context context, List<ProdsDestacado> ProductsList) {
        this.context = context;
        this.ProductsList = ProductsList;
    }

    @NotNull
    @Override
        public ProductosDestacadosViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_productos_destacados, parent, false);
        return new ProductosDestacadosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductosDestacadosViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(ProductsList.get(position).getUrl_image()).resize(220,220).into(holder.imageProds);
        holder.txtNombre_T.setText(ProductsList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetallesProductosActivity.class);
                i.putExtra("id_product",ProductsList.get(position).getId_product());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {

        return ProductsList.size();
    }
    public static class ProductosDestacadosViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProds;
        TextView txtNombre_T;

        public ProductosDestacadosViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageProds = itemView.findViewById(R.id.imgProdsTodos);
            txtNombre_T = itemView.findViewById(R.id.txt_NombreProducto);

        }
    }
}