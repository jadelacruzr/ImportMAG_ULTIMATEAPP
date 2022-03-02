package com.import_mag.importmag.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.import_mag.importmag.Adapters.ProductosAdapter;
import com.import_mag.importmag.Models.ProdsDestacado;
import com.import_mag.importmag.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CatProdsActivity extends AppCompatActivity {

    private RecyclerView recyclerProds;
    private ProductosAdapter productosAdapter;
    private List<ProdsDestacado> prodsEnconList;
    private ImageView cerrar;
    private String nameCat;
    private TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_prods);


        //IMPLEMENTACIÓN CARRUSEL PRODUCTOS DE UNA CATEGORIA
        recyclerProds = findViewById(R.id.recyclerProductosCat);
        setProductosRecycler(recyclerProds);
        txtName=findViewById(R.id.txtCategoria);
        txtName.setText(nameCat);

        cerrar = findViewById(R.id.salirCat);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    /**
     * Método que genera un recycler view para mostrar los productos encontrados en la categoria elegida
     */
    private void setProductosRecycler(RecyclerView recyclerViewTodosProductos) {
        Intent i = getIntent();
        String id_categoria =i.getStringExtra("id_categoria");
        nameCat =i.getStringExtra("name_cat");

        String url = "https://import-mag.com/rest/categoryProducts?id_category="+id_categoria+"&resultsPerPage=1000";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject psdata = jsonObject.getJSONObject("psdata"); //encabezado de WS
                    JSONArray nods=psdata.getJSONArray("products");

                    int tam = nods.length();

                    prodsEnconList = new ArrayList<>();
                    for (int i = 0; i < tam; i++) {
                        JSONObject aux = nods.getJSONObject(i);
                        Integer id_product = aux.getInt("id_product");
                        //String description = psdata.getString("description");
                        String name = aux.getString("name");

                        JSONObject imgs = aux.getJSONObject("default_image");
                        String url_image = imgs.getString("url");

                        prodsEnconList.add(new ProdsDestacado(id_product,name,url_image));

                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CatProdsActivity.this, 2);
                    recyclerProds.setLayoutManager(layoutManager);
                    productosAdapter = new ProductosAdapter(CatProdsActivity.this, prodsEnconList);
                    recyclerProds.setAdapter(productosAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error: ", error.getMessage());
            }
        });
        Volley.newRequestQueue(CatProdsActivity.this).add(postRequest);
    }
}