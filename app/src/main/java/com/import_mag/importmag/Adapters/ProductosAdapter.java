package com.import_mag.importmag.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.import_mag.importmag.Activities.DetallesProductosActivity;
import com.import_mag.importmag.Models.ProdsDestacado;
import com.import_mag.importmag.R;
import com.import_mag.importmag.Activities.VentanaAgregarLike;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;


import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder> {
    Context context;
    List<ProdsDestacado> ProductsList;

    public ProductosAdapter(Context context, List<ProdsDestacado> ProductsList) {
        this.context = context;
        this.ProductsList = ProductsList;
    }

    @NotNull
    @Override
        public ProductosViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_productos, parent, false);
        return new ProductosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductosViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(ProductsList.get(position).getUrl_image()).resize(220,220).into(holder.imageProds);
        String name =ProductsList.get(position).getName();


        holder.txtNombre_T.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetallesProductosActivity.class);
                i.putExtra("id_product", ProductsList.get(position).getId_product());
                context.startActivity(i);
            }
        });

        holder.btnlike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Intent i = new Intent(context, VentanaAgregarLike.class);
                i.putExtra("id_productoo", ProductsList.get(position).getId_product());
                context.startActivity(i);
            }

            @Override
            public void unLiked(LikeButton likeButton) {


            }
        });

    }

    @Override
    public int getItemCount() {

        return ProductsList.size();
    }
    public static class ProductosViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProds;
        TextView txtNombre_T;
        LikeButton btnlike;

        public ProductosViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageProds = itemView.findViewById(R.id.imgProdsTodos);
            txtNombre_T = itemView.findViewById(R.id.txt_NombreProductoAll);
            btnlike=itemView.findViewById(R.id.btn_like);

        }
    }
    public static void darlike(Boolean conf){

    }
}