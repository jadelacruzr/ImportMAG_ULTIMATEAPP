package com.import_mag.importmag.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.import_mag.importmag.R;
import com.import_mag.importmag.Models.Slider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

public class DetallesProductosActivity extends AppCompatActivity {
    ImageView img, btnregresar, cargando2;
    TextView nomb, subnombre, desc;

    Button btnCotizar;
    String url_prod;

    ConstraintLayout crt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_productos);
        //IMPLEMENTACIÓN DEL SLIDER
        ImageSlider slider = findViewById(R.id.image_sliderDetalles);

        consumoDetallesprods(slider);

        nomb = findViewById(R.id.txtDetNombre);
        // img = findViewById(R.id.imgDetProds);
        desc = findViewById(R.id.txtDetDescripcion);
        subnombre = findViewById(R.id.txtDesc_Short);
        btnCotizar = findViewById(R.id.btnCotizar);
        btnregresar = findViewById(R.id.btnAtras);
        crt = findViewById(R.id.clblu);
        cargando2 = findViewById(R.id.img_cargando2);


        nomb.setVisibility(View.INVISIBLE);
        desc.setVisibility(View.INVISIBLE);
        subnombre.setVisibility(View.INVISIBLE);
        btnCotizar.setVisibility(View.INVISIBLE);
        btnregresar.setVisibility(View.INVISIBLE);
        crt.setVisibility(View.INVISIBLE);


        btnCotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String cotizacion = "Saludos, Estoy interesado en cotizar este(s) producto(s): " + url_prod;
                intent.setData(Uri.parse("https://wa.me/593994013402?text=" + cotizacion));
                startActivity(intent);
            }
        });

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Método que hace el consumo de la api Detalle de los productos
     */
    private void consumoDetallesprods(ImageSlider slider) {

        //IDS OBTENIDOS POR SELECCION
        Intent i = getIntent();
        Integer id_product = i.getIntExtra("id_product", 0);


        String url = "https://import-mag.com/rest/productdetail?product_id=" + id_product;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject psdata = jsonObject.getJSONObject("psdata");
                    String name, description, descr_short, url_product;

                    name = psdata.getString("name");
                    description = psdata.getString("description");
                    descr_short = psdata.getString("description_short");
                    url_product = psdata.getString("product_url");
                    url_prod = url_product;
                    String descr = html2text(description);
                    String desc_sho = html2text(descr_short);

                    JSONArray images = psdata.getJSONArray("images");
                    List<Slider> sliderList = new ArrayList();
                    for (int i = 0; i < images.length(); i++) {
                        JSONObject aux = images.getJSONObject(i);
                        String url = aux.getString("src");
                        sliderList.add(new Slider(url));
                    }
                    ArrayList<SlideModel> remoteimages = new ArrayList();

                    for (Slider s : sliderList) {
                        remoteimages.add(new SlideModel(s.getImage(), ScaleTypes.CENTER_INSIDE));

                    }
                    slider.setImageList(remoteimages);
                    slider.stopSliding();

                    nomb.setText(name);
                    subnombre.setText(desc_sho);
                    desc.setText(descr);
                    cargando2.setVisibility(View.INVISIBLE);
                    nomb.setVisibility(View.VISIBLE);
                    desc.setVisibility(View.VISIBLE);
                    subnombre.setVisibility(View.VISIBLE);
                    btnCotizar.setVisibility(View.VISIBLE);
                    btnregresar.setVisibility(View.VISIBLE);
                    crt.setVisibility(View.VISIBLE);

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error: ", error.getMessage());
            }
        });
        Volley.newRequestQueue(DetallesProductosActivity.this).add(postRequest);
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).wholeText();
    }

    //MÉTODO QUE VERIFICA SI ESTÁ O NO INSTALADA UNA APLICACIÓN
    private boolean appInstalledOrNot(String nombrePaquete, Context context) {

        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(nombrePaquete, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}