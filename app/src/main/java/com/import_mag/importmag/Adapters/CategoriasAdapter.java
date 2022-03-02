package com.import_mag.importmag.Adapters;

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

import com.import_mag.importmag.Activities.CatProdsActivity;
import com.import_mag.importmag.Models.Categoria;
import com.import_mag.importmag.R;

import java.util.List;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.ViewHolderCat> {

    private List<Categoria> listCategoria;
    private Context context;

    public CategoriasAdapter(List<Categoria> listCategoria, Context context) {
        this.listCategoria = listCategoria;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderCat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_categorias, parent, false);
        return new ViewHolderCat(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCat holder, int position) {

        holder.txtNameCat.setText(listCategoria.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CatProdsActivity.class);
                i.putExtra("id_categoria", listCategoria.get(position).getId_category());
                i.putExtra("name_cat", listCategoria.get(position).getName());
                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listCategoria.size();
    }

    public static class ViewHolderCat extends RecyclerView.ViewHolder {
        private ImageView imageCat;
        private TextView txtNameCat;


        public ViewHolderCat(@NonNull View itemView) {
            super(itemView);
            imageCat = itemView.findViewById(R.id.imgCategoria);
            txtNameCat = itemView.findViewById(R.id.txtCategorias);

        }
    }
}
