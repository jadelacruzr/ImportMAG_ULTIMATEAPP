package com.import_mag.importmag.Fragments.inicio;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.import_mag.importmag.Adapters.ProductosAdapter;
import com.import_mag.importmag.databinding.FragmentInicioBinding;

import java.util.ArrayList;
import java.util.List;

//IMPORTACIONES DE SLIDER
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.import_mag.importmag.Interfaces.GetServiceSlider;
import com.import_mag.importmag.Models.ProdsDestacados;
import com.import_mag.importmag.Models.Slider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;

    //VARIABLES DEL CARRUSEL PRODUCTOS DESCUENTO
    RecyclerView recyclerViewcprodDestacados;
    ProductosAdapter productosDestacadosAdapter;
    List<ProdsDestacados> featured_products;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //IMPLEMENTACIÓN DEL SLIDER
        ImageSlider slider = binding.imageSlider;
        SliderView(slider);

        //IMPLEMENTACIÓN CARRUSEL PRODUCTOS DESTACADOS
        recyclerViewcprodDestacados = binding.recyclerProdDescuentos;
        setProductosDestacadosRecycler(recyclerViewcprodDestacados);


        //IMPLEMENTACION BOTONES REDES SOCIALES
        ImageView ins, wpp, fb;
        ins = binding.imgINSicono;
        wpp = binding.imgWPPicono;
        fb = binding.imgFBicono;


        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String insId = "https://instagram.com/_u/importmagecuador/";
                String urlPage = "https://www.instagram.com/importmagecuador/";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(insId)));
                } catch (Exception e) {
                    Log.e(TAG, "Aplicación no Encontrada.");
                    //Abre url de pagina.
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                }

            }
        });

        wpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean instalado = appInstalledOrNot("com.whatsapp");
                if (instalado) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    intent.setData(Uri.parse("https://wa.me/593994013402?text="));
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "WhatsApp no está instalado en este dispositivo", Toast.LENGTH_SHORT).show();
                }
            }

        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookId = "fb://page/102219398958150";
                String urlPage = "https://www.facebook.com/PruebaImport-102219398958150";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId)));
                } catch (Exception e) {
                    Log.e(TAG, "Aplicación no Encontrada.");
                    //Abre url de pagina.
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                }

            }
        });


        return view;
    }

    //MÉTODO QUE VERIFICA SI ESTÁ O NO INSTALADA UNA APLICACIÓN
    private boolean appInstalledOrNot(String url) {
        PackageManager packageManager = getActivity().getPackageManager();
        boolean app_instaled;
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_instaled = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_instaled = false;
        }
        return app_instaled;
    }

    /**
     * Método que genera un View Slider
     *
     * @param slider
     */
    private void SliderView(ImageSlider slider) {
        //ARRAY QUE ALMACENARÁ LAS IMÁGENES DEL SLIDER

        ArrayList<SlideModel> remoteimages = new ArrayList();
        //CONSUMO DE LA API SLIDER//
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.import-mag.com/getSlider/revSlider.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetServiceSlider getServiceSlider = retrofit.create(GetServiceSlider.class);
        Call<List<Slider>> call = getServiceSlider.find();
        call.enqueue(new Callback<List<Slider>>() {

            @Override
            public void onResponse(Call<List<Slider>> call, retrofit2.Response<List<Slider>> response) {
                List<Slider> sliderList = response.body();
                //Recorrido de los datos extraidos de la api e inserción en el View Slider
                for (Slider s : sliderList) {
                    remoteimages.add(new SlideModel("https://www.import-mag.com/modules/ps_imageslider/images/" + s.getImage(), s.getLegend(), ScaleTypes.FIT));
                }
                slider.setImageList(remoteimages);
                slider.startSliding(1500);
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {

                Toast.makeText(getActivity(), "Error al consumir api", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Método que genera un carrusel de ProdsDestacados Destacados
     */
    private void setProductosDestacadosRecycler(RecyclerView recyclerViewcprodDestacados) {
        String url = "https://import-mag.com/rest/featuredproducts";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jasonArray = jsonObject.getJSONArray("psdata"); //encabezado de WS
                    int tam = jasonArray.length();

                    featured_products = new ArrayList<>();
                    for (int i = 0; i < tam; i++) {
                        JSONObject psdata = jasonArray.getJSONObject(i);
                        Integer id_product = psdata.getInt("id_product");
                        //String description = psdata.getString("description");
                        String name = psdata.getString("name");

                        JSONObject imgs = psdata.getJSONObject("default_image");
                        String url_image = imgs.getString("url");

                        featured_products.add(new ProdsDestacados(id_product, name, url_image));

                    }
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewcprodDestacados.setLayoutManager(layoutManager);
                    productosDestacadosAdapter = new ProductosAdapter(getActivity(), featured_products);
                    recyclerViewcprodDestacados.setAdapter(productosDestacadosAdapter);
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
        Volley.newRequestQueue(getActivity()).add(postRequest);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}