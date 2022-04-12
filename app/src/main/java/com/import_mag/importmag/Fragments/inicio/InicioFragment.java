package com.import_mag.importmag.Fragments.inicio;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.import_mag.importmag.Adapters.ProductosDestacadosAdapter;
import com.import_mag.importmag.Models.ProdsDestacado;
import com.import_mag.importmag.databinding.FragmentInicioBinding;

import java.util.ArrayList;
import java.util.List;

//IMPORTACIONES DE SLIDER
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.import_mag.importmag.Interfaces.GetServiceSlider;
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

    //VARIABLES DEL RECYCLER VIEW
    RecyclerView recyclerViewProds;

    //RECYCLER PARA PRODCUTOS DESTACADOS
    ProductosDestacadosAdapter productosDestacadosAdapter;
    List<ProdsDestacado> featured_products;
    //VARIABLE DEL SLIDER
    ImageSlider slider;
    LinearLayout ll_home;
    ImageView img;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            binding = FragmentInicioBinding.inflate(inflater, container, false);
            View view = binding.getRoot();
            if (isOnlineNet() == true) {
                //IMPLEMENTACIÓN DEL SLIDER
                slider = binding.imageSlider;
                SliderView(slider);

                //IMPLEMENTACIÓN CARRUSEL PRODUCTOS DESTACADOS
                recyclerViewProds = binding.recyclerProdDestacados;
                setProductosDestacadosRecycler(recyclerViewProds);
            }   else {
                Toast.makeText(getActivity(), "Revisa tu conexión a Internet", Toast.LENGTH_SHORT).show();
            }



            ll_home = binding.llHome;
            img = binding.imgCargando2;
            ll_home.setVisibility(View.INVISIBLE);
            return view;

        } catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }
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

                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Método que genera un carrusel de ProdsDestacado Destacados
     */
    private void setProductosDestacadosRecycler(RecyclerView recyclerViewcprodDestacados) {
        String url = "https://import-mag.com/rest/featuredproducts";

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
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
                        String name = psdata.getString("name");
                        JSONObject imgs = psdata.getJSONObject("default_image");
                        String url_image = imgs.getString("url");

                        featured_products.add(new ProdsDestacado(id_product, name, url_image));

                    }

                    productosDestacadosAdapter = new ProductosDestacadosAdapter(getActivity(), featured_products);
                    recyclerViewcprodDestacados.setAdapter(productosDestacadosAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewcprodDestacados.setLayoutManager(layoutManager);


                    ll_home.setVisibility(View.VISIBLE);
                    img.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest request2 = new StringRequest(Request.Method.GET, url,
                responseListener, errorListener) {
        };
        Volley.newRequestQueue(getActivity()).add(request2);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}