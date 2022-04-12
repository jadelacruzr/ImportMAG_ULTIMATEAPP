package com.import_mag.importmag.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.import_mag.importmag.R;

import org.json.JSONException;
import org.json.JSONObject;

public class NuevaListaActivity extends AppCompatActivity {
    EditText editName;
    Button cancelar, crearListFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_lista);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;
        getWindow().setLayout((int) (ancho * 0.85), (int) (alto * 0.5));


        editName = findViewById(R.id.edtxtCrearListFav);

        cancelar = findViewById(R.id.btnCancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        crearListFav=findViewById(R.id.btnCrearListNew);
        crearListFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=editName.getText().toString();
                final String url = "https://import-mag.com/rest/wishlist?action=createWishlist&name="+name;

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String mensaje =jsonObject.getString("message");
                            String code =jsonObject.getString("code");
                            if (code.equals("200")){
                                Toast.makeText(NuevaListaActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                                finish();

                            }else{
                                Toast.makeText(NuevaListaActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                final Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NuevaListaActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                };

                StringRequest request2 = new StringRequest(Request.Method.GET, url,
                        responseListener,errorListener) {
                };
                Volley.newRequestQueue(NuevaListaActivity.this).add(request2);
            }
        });

    }

}