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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.import_mag.importmag.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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


    /**
     * Variables que encapsularán los componentes de la aplicación y su contenido.
     */
    String email = "";
    String password = "";
    String passwordConfirm = "";
    String name = "";
    String last_name = "";
    String gender;
    String genderE;

    //Llamada al validador de credenciales de Firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        /**
         * Asignación de los elementos a las variables locales.
         */

        btnRegistro = findViewById(R.id.btnRegistrar);
        emailText = findViewById(R.id.emailText);
        passwordTex = findViewById(R.id.passwordText);
        checkRegisterViewPass = findViewById(R.id.checkRegisterViewPass);
        passwordconfirmText = findViewById(R.id.passwordConfirmText);
        checkRegisterConfirmViewPass = findViewById(R.id.checkRegisterConfirmViewPass);
        nameText = findViewById(R.id.nameText);
        apellidosText = findViewById(R.id.nickText);
        enlace_IniciarSesion=findViewById(R.id.enlace_IniciarSesion);
        cerrar=findViewById(R.id.salirRegistro);
        radiogroup=findViewById(R.id.rdGrupo);


        /**
         * Manejo del formato de la fecha
         */

        /**
         * Desplegamiento del calendario, y asignación de datos al campo de fecha.
         */
        /*efecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                 int year = calendar.get(Calendar.YEAR);
                 int month = calendar.get(Calendar.MONTH);
                 int day = calendar.get(Calendar.DAY_OF_MONTH);
                 DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrarseActivity.this, AlertDialog.THEME_HOLO_DARK,new DatePickerDialog.OnDateSetListener() {

                     @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        efecha.setText(twoDigits(dayOfMonth)+"/"+twoDigits(month+1)+"/"+twoDigits(year));

                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });*/


        /**
         * Acción para mostrar contraseña al usuario en el campo contraseña.
         */
        checkRegisterViewPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordTex.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordTex.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
                    passwordconfirmText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordconfirmText.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
                int radioID=radiogroup.getCheckedRadioButtonId();
                radioButton=findViewById(radioID);
                gender=radioButton.getText().toString();
                if (gender.equals("Sr.")){
                    genderE="1";
                }else if (gender.equals("Sra.")){
                    genderE="2";
                }
                //Verifica si todos los campos están llenos, de no ser así verifica qué campo falta llenar
                //Si todos los campos están llenos pregunta si la contraseña tiene más de 9 dígitos y si
                //es igual la contraseña a la confirmación de contraseña.

                if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !last_name.isEmpty()
                        && !passwordConfirm.isEmpty()) {
                    if (password.length() >= 9) {
                        if (passwordConfirm.equals(password)) {
                            apiRequest(email,password,name,last_name,genderE);
                        } else {
                            Toast.makeText(RegistrarseActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(RegistrarseActivity.this, "La contraseña debe tener al menos 9 caracteres.", Toast.LENGTH_SHORT).show();
                    }
                } else if (email.isEmpty()) {
                    Toast.makeText(RegistrarseActivity.this, "Debe ingresar el email.", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(RegistrarseActivity.this, "Debe ingresar una contraseña.", Toast.LENGTH_SHORT).show();
                } else if (passwordConfirm.isEmpty()) {
                    Toast.makeText(RegistrarseActivity.this, "Confirme su contraseña.", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(RegistrarseActivity.this, "Debe ingresar un Nombre.", Toast.LENGTH_SHORT).show();
                } else if (last_name.isEmpty()) {
                    Toast.makeText(RegistrarseActivity.this, "Debe ingresar su Apellido.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistrarseActivity.this, "Debe completar los campos.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }    //Finaliza OnCreate

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    private void apiRequest(String email,String password,String name,String last_name,String gender){
        ////ENVIANDO REGISTRO A LA API

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"email\": \""+email+"\",\n    \"password\": \""+password+"\",\n    \"firstName\": \""+name+"\",\n    \"lastName\": \""+last_name+"\",\n    \"gender\": "+gender+"\n}");
        Request request = new Request.Builder()
                .url("https://www.import-mag.com/rest/register")
                .method("POST", body)
                .build();
       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               e.printStackTrace();
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
                response = client.newCall(request).execute();
                Log.println(Log.INFO,"",""+response.header("\'psdata\'"));


           }
       });
    }
}