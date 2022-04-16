package com.import_mag.importmag.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.import_mag.importmag.MainActivity;
import com.import_mag.importmag.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarseActivity extends AppCompatActivity {


    /**
     * Declaración de elementos de la vista para trabajar en la clase
     */
    private Button btnRegistro;
    private EditText emailText, passwordTex, nameText, apellidosText, efecha, passwordconfirmText;
    private CheckBox checkRegisterViewPass, checkRegisterConfirmViewPass;
    private TextView enlace_IniciarSesion;
    private ImageView cerrar;
    private RadioButton radioButton;
    private RadioGroup radiogroup;
    View viewmsg;


    /**
     * Variables que encapsularán los componentes de la aplicación y su contenido.
     */
    String email;
    String password;
    String passwordConfirm;
    String name;
    String last_name;
    String gender;
    String genderE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Asignación de los elementos a las variables locales.

        btnRegistro = findViewById(R.id.btnRegistrar);
        emailText = findViewById(R.id.emailText);
        passwordTex = findViewById(R.id.passwordText);
        checkRegisterViewPass = findViewById(R.id.checkRegisterViewPass);
        passwordconfirmText = findViewById(R.id.passwordConfirmText);
        checkRegisterConfirmViewPass = findViewById(R.id.checkRegisterConfirmViewPass);
        nameText = findViewById(R.id.nameText);
        apellidosText = findViewById(R.id.nickText);
        enlace_IniciarSesion = findViewById(R.id.enlace_IniciarSesion);
        cerrar = findViewById(R.id.salirRegistro);
        radiogroup = findViewById(R.id.rdGrupo);


        /**
         * Acción para mostrar contraseña al usuario en el campo contraseña.
         */
        checkRegisterViewPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int numbre = passwordTex.getSelectionEnd();
                    passwordTex.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordTex.setSelection(numbre);
                } else {
                    int numbre = passwordTex.getSelectionEnd();
                    passwordTex.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordTex.setSelection(numbre);
                }
            }
        });
        /**
         * Método que redirige a la actividad de inicio de sesion
         */
        enlace_IniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrarseActivity.this, LoginActivity.class));
                finish();

            }
        });

        /**
         * Método que cierra la actividad de Registro
         */
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        /**
         * Acción para mostrar contraseña al usuario en el campo confirmar contraseña.
         */
        checkRegisterConfirmViewPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int numbre = passwordconfirmText.getSelectionEnd();
                    passwordconfirmText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordconfirmText.setSelection(numbre);
                } else {
                    int numbre = passwordconfirmText.getSelectionEnd();
                    passwordconfirmText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordconfirmText.setSelection(numbre);
                }
            }
        });

        /**
         * Acción del botón registrar, que obtiene los datos de la vista y verifica si están llenos
         * y correctamente llenados.
         */
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailText.getText().toString();
                password = passwordTex.getText().toString();
                passwordConfirm = passwordconfirmText.getText().toString();
                name = nameText.getText().toString();
                last_name = apellidosText.getText().toString();
                int radioID = radiogroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);
                gender = radioButton.getText().toString();
                if (gender.equals("Sr.")) {
                    genderE = "1";
                } else if (gender.equals("Sra./Srta.")) {
                    genderE = "2";
                }
                //Verifica si todos los campos están llenos, de no ser así verifica qué campo falta llenar
                //Si todos los campos están llenos pregunta si la contraseña tiene más de 9 dígitos y si
                //es igual la contraseña a la confirmación de contraseña.

                if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !last_name.isEmpty()
                        && !passwordConfirm.isEmpty()) {
                    if (password.length() >= 9) {
                        if (passwordConfirm.equals(password)) {
                            if (isOnlineNet() == true) {
                                consumoApi(email, password, name, last_name, genderE);
                            } else {
                                Snackbar snackbar = Snackbar.make(viewmsg, " ! Revisa tu conexión a Internet ", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null);
                                View sbView = snackbar.getView();
                                sbView.setBackgroundColor(ContextCompat.getColor(RegistrarseActivity.this, R.color.mensajeinfo));
                                snackbar.show();
                            }
                        } else {
                            Snackbar snackbar = Snackbar.make(v, " ! Las contraseñas no coinciden", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            View sbView = snackbar.getView();
                            sbView.setBackgroundColor(ContextCompat.getColor(RegistrarseActivity.this, R.color.mensaerror));
                            snackbar.show();

                        }
                    } else {
                        Snackbar snackbar = Snackbar.make(v, " ! La contraseña debe contener almenos 9 caracteres", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(ContextCompat.getColor(RegistrarseActivity.this, R.color.mensaerror));
                        snackbar.show();
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(v, " ! Debe completar todos los campos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(RegistrarseActivity.this, R.color.mensaerror));
                    snackbar.show();
                }

            }
        });
    }    //Finaliza OnCreate


    private void consumoApi(String email2, String password2, String name2, String last_name2, String gender2) {

        try {

            final String url = "https://import-mag.com/rest/register";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email2);
            jsonBody.put("password", password2);
            jsonBody.put("firstName", name2);
            jsonBody.put("lastName", last_name2);
            jsonBody.put("gender", gender2);

            final Response.Listener<JSONObject> responseListener = new com.android.volley.Response.Listener<JSONObject>() {
                String mensaje = "";

                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String code = response.getString("code");

                        if (code.equals("308")) {
                            mensaje = "Usuario ya registrado con este correo electrónico";
                            Snackbar snackbar = Snackbar.make(getWindow().findViewById(android.R.id.content), mensaje, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            View sbView = snackbar.getView();
                            sbView.setBackgroundColor(ContextCompat.getColor(RegistrarseActivity.this, R.color.mensaerror));
                            snackbar.show();


                        } else if (code.equals("302")) {
                            mensaje = "Correo electrónico inválido";
                            Snackbar snackbar = Snackbar.make(getWindow().findViewById(android.R.id.content), mensaje, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            View sbView = snackbar.getView();
                            sbView.setBackgroundColor(ContextCompat.getColor(RegistrarseActivity.this, R.color.mensaerror));
                            snackbar.show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            };

            final Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String mensaje2 = "Usuario registrado correctamente";
                    Snackbar snackbar = Snackbar.make(getWindow().findViewById(android.R.id.content), "Usuario registrado correctamente", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(RegistrarseActivity.this, R.color.mensajeok));
                    snackbar.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent data2 = new Intent();
                            data2.setData(Uri.parse(email2));
                            setResult(RESULT_OK, data2);
                            finish();
                        }
                    }, 1500);
                }
            };

            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    responseListener, errorListener) {
            };
            Volley.newRequestQueue(RegistrarseActivity.this).add(request2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}