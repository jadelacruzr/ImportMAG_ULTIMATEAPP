package com.import_mag.importmag.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.import_mag.importmag.R;

public class PerfilClienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);

        perfConsumo();



    }
    /*
    Metodo que hace get a la api accountInfo
    * */
    private void perfConsumo(){

        final String url = "https://import-mag.com/rest/accountInfo";

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
        Volley.newRequestQueue(PerfilClienteActivity.this).add(request2);

    }

}