package com.import_mag.importmag.Fragments;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.import_mag.importmag.Adapters.CategoriasAdapter;
import com.import_mag.importmag.Adapters.CategoriasAdapterInicio;
import com.import_mag.importmag.Adapters.ProductosDestacadosAdapter;
import com.import_mag.importmag.Interfaces.GetServiceCategorias;
import com.import_mag.importmag.Models.Categoria;
import com.import_mag.importmag.Models.ProdsDestacado;
import com.import_mag.importmag.R;
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
    //RECYCLER PARA CATEGORIAS
    CategoriasAdapterInicio CatAdapter;
    List<Categoria> listCategoria = new ArrayList<>();
    RecyclerView recyclerViewCat;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            binding = FragmentInicioBinding.inflate(inflater, container, false);
            View view = binding.getRoot();
            if (isOnlineNet() == true) {
                //IMPLEMENTACIÓN DEL SLIDER
                slider = binding.imageSlider;
                SliderView(slider);

                ////IMPLEMENTACIÓN CARRUSEL DE PRODUCTOS
                recyclerViewCat = binding.recyclerCategorias;
                setCategoriasRecycler(recyclerViewCat);

                //IMPLEMENTACIÓN CARRUSEL PRODUCTOS DESTACADOS
                recyclerViewProds = binding.recyclerProdDestacados;
                setProductosDestacadosRecycler(recyclerViewProds);
            } else {
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Revisa tu conexión a Internet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mensajeinfo));
                snackbar.show();


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
                    remoteimages.add(new SlideModel("https://www.import-mag.com/modules/ps_imageslider/images/"
                            + s.getImage(), s.getLegend(), ScaleTypes.FIT));
                }
                slider.setImageList(remoteimages);
                slider.startSliding(1500);
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Error de conexión con el servidor", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mensajeinfo));
                snackbar.show();

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
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);
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
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Error de conexión con el servidor", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mensajeinfo));
                snackbar.show();
            }
        };

        StringRequest request2 = new StringRequest(Request.Method.GET, url,
                responseListener, errorListener) {
        };
        Volley.newRequestQueue(getActivity()).add(request2);

    }

    /**
     * MÉTODO QUE GENERA UN RECYCLERVIEW DE CATEGORIAS
     */
    private void setCategoriasRecycler(RecyclerView recyclerViewcategorias) {

        //CONSUMO DE LA API CATEGORIAS//
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://import-mag.com/getSlider/cat.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetServiceCategorias getServiceCategorias = retrofit.create(GetServiceCategorias.class);
        Call<List<Categoria>> call = getServiceCategorias.find();

        if (isOnlineNet() == true) {
            call.enqueue(new Callback<List<Categoria>>() {
                @Override
                public void onResponse(Call<List<Categoria>> call, retrofit2.Response<List<Categoria>> response) {
                    List<Categoria> categoriaList = response.body();

                    for (Categoria s : categoriaList) {
                        String link_r = s.getLink_rewwrite();
                        String descr = s.getName().toString();
                        String nuevapalabra = descr;
                        if (descr.contains("Ã¡") || descr.contains("Ã©") || descr.contains("Ã\u00AD") || descr.contains("Ã³") || descr.contains("Ãº")) {
                            nuevapalabra = descr.replaceAll("Ã¡", "á").replaceAll("Ã©", "é").replaceAll
                                    ("Ã\u00AD", "í").replaceAll("Ã³", "ó").replaceAll
                                    ("Ãº", "ú");
                        }
                        listCategoria.add(new Categoria(s.getId_category(), nuevapalabra, link_r));
                    }
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewcategorias.setLayoutManager(layoutManager);
                    CatAdapter = new CategoriasAdapterInicio(listCategoria, getActivity());
                    recyclerViewcategorias.setAdapter(CatAdapter);
                }

                @Override
                public void onFailure(Call<List<Categoria>> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Error de conexión con el servidor", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mensajeinfo));
                    snackbar.show();
                }
            });
        } else {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Revisa tu conexión a Internet", Snackbar.LENGTH_LONG)
                    .setAction("Action", null);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mensajeinfo));
            snackbar.show();
        }
    }

    /**
     * MÉTODO QUE VERIFICA CONECTIVIDAD DEL DISPOSITIVO
     */
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