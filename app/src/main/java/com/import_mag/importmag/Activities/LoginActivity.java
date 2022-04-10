package com.import_mag.importmag.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.NetworkResponse;

import com.android.volley.VolleyError;


import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;

import com.import_mag.importmag.PersistentCookieStore;
import com.import_mag.importmag.R;


import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText emailLogText, passwordLogText;
    TextView olvidarContraseña, btnRegistrar;
    CheckBox checkViewPass;
    private ImageView cerrar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //cookies store
        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(LoginActivity.this), CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(cookieManager);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnIniciarSesion);
        btnRegistrar = findViewById(R.id.enlaceRegistro);

        emailLogText = findViewById(R.id.emailLogText);
        passwordLogText = findViewById(R.id.passwordLogText);

        checkViewPass = findViewById(R.id.checkViewPass);

        olvidarContraseña = findViewById(R.id.olvidarContraseñaView);
        cerrar2 = findViewById(R.id.salirInicioSesion);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailTxt = emailLogText.getText().toString();
                String passTxt = passwordLogText.getText().toString();

                if (!emailTxt.isEmpty() && !passTxt.isEmpty()) {
                    try {
                        final String url = "https://import-mag.com/rest/login";
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("email", emailTxt);
                        jsonBody.put("password", passTxt);

                        final com.android.volley.Response.Listener<JSONObject> responseListener = new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println(response.toString());

                                startActivity(new Intent(LoginActivity.this, PerfilCliente.class));
                            }
                        };
                        final com.android.volley.Response.ErrorListener errorListener = new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Handle your errors
                            }
                        };

                        JsonObjectRequest request2 = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody,
                                responseListener, errorListener) {


                            @Override
                            protected com.android.volley.Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                                // since we don't know which of the two underlying network vehicles
                                // will Volley use, we have to handle and store session cookies manually
                                Log.i("response", response.headers.toString());
                                Map<String, String> responseHeaders = response.headers;
                                String rawCookies = responseHeaders.get("Set-Cookie");
                                Log.i("cookies", rawCookies);


                                return super.parseNetworkResponse(response);
                            }

                        };


                        Volley.newRequestQueue(LoginActivity.this).add(request2);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(LoginActivity.this, "Ingrese todos los campos requeridos.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        /**
         * Método que cierra la actividad de Registro
         */
        cerrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrarseActivity.class));
                finish();
            }
        });

        olvidarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Aun no funciona", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(LoginActivity.this, ResetPassword.class));
            }
        });

        checkViewPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordLogText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordLogText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

}
