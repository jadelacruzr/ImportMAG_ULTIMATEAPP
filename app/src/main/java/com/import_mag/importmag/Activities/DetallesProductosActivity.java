package com.import_mag.importmag.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    LinearLayout lin;
    ConstraintLayout crt;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setStatusBarColor(ContextCompat.getColor(DetallesProductosActivity.this, R.color.white));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_productos);
        //IMPLEMENTACIÓN DEL SLIDER
        ImageSlider slider = findViewById(R.id.image_sliderDetalles);

        consumoDetallesprods(slider);

        nomb = findViewById(R.id.txtDetNombre);
        desc = findViewById(R.id.txtDetDescripcion);
        subnombre = findViewById(R.id.txtDesc_Short);
        btnCotizar = findViewById(R.id.btnCotizar);
        crt = findViewById(R.id.clblu);
        cargando2 = findViewById(R.id.img_cargando2);
        lin = findViewById(R.id.ll);
        btnregresar = findViewById(R.id.btnCerrar);


        nomb.setVisibility(View.INVISIBLE);
        desc.setVisibility(View.INVISIBLE);
        subnombre.setVisibility(View.INVISIBLE);
        btnCotizar.setVisibility(View.INVISIBLE);
        crt.setVisibility(View.INVISIBLE);
        lin.setVisibility(View.INVISIBLE);


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


        final String url = "https://import-mag.com/rest/productdetail?product_id=" + id_product;


        final Response.Listener<String> responseListener = new Response.Listener<String>() {
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
                    lin.setVisibility(View.VISIBLE);
                    getWindow().setNavigationBarColor(ContextCompat.getColor(DetallesProductosActivity.this, R.color.azulIntenso));

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetallesProductosActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest request2 = new StringRequest(Request.Method.GET, url,
                responseListener, errorListener) {
        };
        Volley.newRequestQueue(DetallesProductosActivity.this).add(request2);
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).wholeText();
    }
}