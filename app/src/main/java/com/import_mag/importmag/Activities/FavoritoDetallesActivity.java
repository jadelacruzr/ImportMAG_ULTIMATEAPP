package com.import_mag.importmag.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.import_mag.importmag.Adapters.ProductosFavoritosAdapter;
import com.import_mag.importmag.Models.ProdsDestacado;
import com.import_mag.importmag.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoritoDetallesActivity extends AppCompatActivity {

    private RecyclerView rvProdsFavoritos;
    private ImageView cerrar3, edit, cargando, eliminar;
    private TextView nameFav, none;
    private String rempName, id_wish;
    private List<ProdsDestacado> prodsEnconList;
    private ProductosFavoritosAdapter productosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorito_detalles);
        //IMPLEMENTACIÓN CARRUSEL PRODUCTOS EN DESCUENTO
        rvProdsFavoritos = findViewById(R.id.recyclerprodsFav);
        setProductosFavRecycler(rvProdsFavoritos);

        //ASIGNACION DE VARIABLES DEL LAYOUT
        cerrar3 = findViewById(R.id.salirBusqueda);
        nameFav = findViewById(R.id.txtNombFavo);
        edit = findViewById(R.id.btnEditarFav);
        none = findViewById(R.id.txtNoneProdList);
        none.setVisibility(View.INVISIBLE);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FavoritoDetallesActivity.this, EditarListFavsActivity.class);
                i.putExtra("id_wishlist", id_wish);
                i.putExtra("namewish", rempName);
                startActivity(i);
            }
        });

        eliminar = findViewById(R.id.btnEliminarWish);
        //BOTÓN QUE ACCEDE AL MÉTODO ELIMINAR LISTA DE FAVORITOS
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarLisFav();
            }
        });

        cargando = findViewById(R.id.img_cargandoFav);
        rvProdsFavoritos.setVisibility(View.INVISIBLE);


        //BOTÓN QUE CIERRA LA ACTIVIDAD
        cerrar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Método que genera un viewslider de Lista de favoritos
     */
    private void setProductosFavRecycler(RecyclerView recyclerViewProdFav) {
        Intent i = getIntent();
        id_wish = i.getStringExtra("id_wishlist");

        String url = "https://import-mag.com/rest/wishlist?action=viewWishlist&id_wishlist=" + id_wish;

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject psdata = jsonObject.getJSONObject("psdata"); //encabezado de WS
                    String nameWish = psdata.getString("wishlistName");
                    JSONArray nods = psdata.getJSONArray("products");
                    int tam = nods.length();
                    prodsEnconList = new ArrayList<>();
                    for (int i = 0; i < tam; i++) {
                        JSONObject aux = nods.getJSONObject(i);
                        Integer id_product = aux.getInt("id_product");
                        //String description = psdata.getString("description");
                        String name = aux.getString("name");
                        JSONObject imgs = aux.getJSONObject("default_image");
                        String url_image = imgs.getString("url");
                        prodsEnconList.add(new ProdsDestacado(id_product, name, url_image));
                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(FavoritoDetallesActivity.this, 2);
                    productosAdapter = new ProductosFavoritosAdapter(FavoritoDetallesActivity.this, prodsEnconList);
                    rvProdsFavoritos.setLayoutManager(layoutManager);
                    rempName = nameWish;
                    nameFav.setText(rempName);
                    rvProdsFavoritos.setAdapter(productosAdapter);
                    rvProdsFavoritos.setVisibility(View.VISIBLE);
                    cargando.setVisibility(View.INVISIBLE);
                    if(tam==0) {
                        rvProdsFavoritos.setVisibility(View.INVISIBLE);
                        none.setVisibility(View.VISIBLE);
                    }


            } catch(
            JSONException e)

            {
                e.printStackTrace();
            }
        }
    }

    ;

    final Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(FavoritoDetallesActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
    };

    StringRequest request2 = new StringRequest(Request.Method.GET, url,
            responseListener, errorListener) {
    };
        Volley.newRequestQueue(FavoritoDetallesActivity .this).

    add(request2);

}

    /**
     * Método que elimina una lilsta de favoritos
     */

    public void EliminarLisFav() {

        AlertDialog.Builder alerta = new AlertDialog.Builder(FavoritoDetallesActivity.this);
        alerta.setMessage("¿Esta seguro de eliminar esta lista de favoritos?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String url = "https://import-mag.com/rest/wishlist?action=deleteWishlist&idWishList=" + id_wish;
                        final Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(FavoritoDetallesActivity.this, "Lista eliminada!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        };

                        final Response.ErrorListener errorListener = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(FavoritoDetallesActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                            }
                        };

                        StringRequest request2 = new StringRequest(Request.Method.GET, url,
                                responseListener, errorListener) {
                        };
                        Volley.newRequestQueue(FavoritoDetallesActivity.this).add(request2);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Eliminar");
        titulo.show();

    }

    @Override
    protected void onResume() {

        super.onResume();
        //IMPLEMENTACIÓN CARRUSEL PRODUCTOS EN DESCUENTO
        rvProdsFavoritos = findViewById(R.id.recyclerprodsFav);
        setProductosFavRecycler(rvProdsFavoritos);

        //ASIGNACION DE VARIABLES DEL LAYOUT
        cerrar3 = findViewById(R.id.salirBusqueda);
        nameFav = findViewById(R.id.txtNombFavo);
        edit = findViewById(R.id.btnEditarFav);
        //BOTÓN QUE ABRE LA ACTIVIDAD EDITAR LISTA DE FAVORITOS
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FavoritoDetallesActivity.this, EditarListFavsActivity.class);
                i.putExtra("id_wishlist", id_wish);
                i.putExtra("namewish", rempName);
                startActivity(i);
            }
        });

        eliminar = findViewById(R.id.btnEliminarWish);
        //BOTÓN QUE ACCEDE AL MÉTODO ELIMINAR LISTA DE FAVORITOS
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarLisFav();
            }
        });
        cargando = findViewById(R.id.img_cargandoFav);
        rvProdsFavoritos.setVisibility(View.INVISIBLE);

        //BOTÓN QUE CIERRA LA ACTIVIDAD
        cerrar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}