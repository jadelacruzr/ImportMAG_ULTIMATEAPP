package com.import_mag.importmag.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import com.import_mag.importmag.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText emailLogText, passwordLogText;
    TextView olvidarContraseña, btnRegistrar;
    CheckBox checkViewPass;
    private ImageView cerrar2;

    private String correo = "";

    String nick = "Ron";
    String password = "12345678";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                //String id = mfiFirebaseAuth.getUid();
                //nick = mDatabaseReference.child("Users").child(id).child("email").getKey().toString();
                if (!nick.isEmpty() && !password.isEmpty()) {
                    if (password.length() >= 8) {
                        {
                            if (nick.equals(emailTxt) && password.equals(passTxt)) {

                                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                Toast.makeText(LoginActivity.this, "Ha accedido correctamente", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(LoginActivity.this, "No se ha podido iniciar sesión correctamente.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "La contraseña contiene menos de 8 caracteres.", Toast.LENGTH_SHORT).show();
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
