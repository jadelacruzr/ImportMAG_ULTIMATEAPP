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
import com.import_mag.importmag.R;
import com.import_mag.importmag.Adapters.ProductosAdapter;
import com.import_mag.importmag.Models.ProdsDestacado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuscarProdsActivity extends AppCompatActivity {


    //VARIABLES DEL RECYCLERVIEW
    //VARIABLES DEL CARRUSEL PRODUCTOS
    private RecyclerView rvSearchProducts;
    private ProductosAdapter productosAdapter;
    private List<ProdsDestacado> prodsEnconList;
    private ImageView cerrar3,cargando2;
    private String strBusqueda;
    private TextView txtBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_prods);

        //IMPLEMENTACIÓN CARRUSEL PRODUCTOS EN DESCUENTO
        rvSearchProducts = findViewById(R.id.recyclerBusProductos);
        setProductosRecycler(rvSearchProducts);
        cerrar3 = findViewById(R.id.salirBusqueda);
        txtBusqueda=findViewById(R.id.txtBusqueda);
        txtBusqueda.setText("Resultados para "+strBusqueda+":");
        cargando2=findViewById(R.id.img_cargando2);
        rvSearchProducts.setVisibility(View.INVISIBLE);


        /**
         * Método que cierra la actividad de Registro
         */
        cerrar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    /**
     * Método que genera un carrusel de ProdsDestacado Encontrados
     */
    private void setProductosRecycler(RecyclerView recyclerViewTodosProductos) {
        Intent i = getIntent();
        strBusqueda =i.getStringExtra("stringBusqueda");

        String url = "https://import-mag.com/rest/productSearch?s="+strBusqueda+"&resultsPerPage=1000";
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

                        prodsEnconList.add(new ProdsDestacado(id_product, name, url_image));

                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(BuscarProdsActivity.this, 2);
                    rvSearchProducts.setLayoutManager(layoutManager);
                    productosAdapter = new ProductosAdapter(BuscarProdsActivity.this, prodsEnconList);
                    rvSearchProducts.setAdapter(productosAdapter);
                    rvSearchProducts.setVisibility(View.VISIBLE);
                    cargando2.setVisibility(View.INVISIBLE);
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
        Volley.newRequestQueue(BuscarProdsActivity.this).add(postRequest);
    }

}