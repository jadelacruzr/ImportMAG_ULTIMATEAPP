package com.import_mag.importmag.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.import_mag.importmag.Activities.FavoritoDetallesActivity;
import com.import_mag.importmag.Models.Favoritos;
import com.import_mag.importmag.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.FavoritosViewHolder> {
    Context context;
    List<Favoritos> favoritosList;

    public FavoritosAdapter(Context context, List<Favoritos> favoritosList) {
        this.context = context;
        this.favoritosList = favoritosList;
    }

    @NotNull
    @Override
        public FavoritosViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_favoritos, parent, false);
        return new FavoritosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoritosViewHolder holder,int position) {
       String name = favoritosList.get(position).getWishlistName().toString();
        String cantidad= favoritosList.get(position).getNbProducts().toString();

        holder.txtNombreFavs.setText(name);
        holder.cant.setText("("+cantidad+")");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FavoritoDetallesActivity.class);
                i.putExtra("id_wishlist", favoritosList.get(position).getId_wishlist());
                i.putExtra("name_fav", favoritosList.get(position).getWishlistName());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoritosList.size();
    }
    public static class FavoritosViewHolder extends RecyclerView.ViewHolder {
        ImageView imagelistFavs;
        TextView txtNombreFavs,cant;
        ImageButton delete;

        public FavoritosViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imagelistFavs = itemView.findViewById(R.id.imgListFavs);
            txtNombreFavs = itemView.findViewById(R.id.txt_nombFav);

            cant=itemView.findViewById(R.id.txtcant);

        }
    }
    public void deleteFav (String idwish){
        final String url = "https://import-mag/rest/wishlist?action=deleteWishlist&idWishList="+idwish;

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.toString());
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Handle your errors
            }
        };

        StringRequest request2 = new StringRequest(Request.Method.GET, url,
                responseListener,errorListener) {
        };
        Volley.newRequestQueue(context.getApplicationContext()).add(request2);


    }
}