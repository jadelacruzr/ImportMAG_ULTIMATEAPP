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
import com.import_mag.importmag.Adapters.CategoriasAdapter;
import com.import_mag.importmag.Adapters.ProductosAdapter;
import com.import_mag.importmag.Interfaces.GetServiceCategorias;
import com.import_mag.importmag.Models.Categoria;
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
import org.jsoup.Jsoup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;

    //VARIABLES DEL RECYCLER VIEW
    RecyclerView recyclerViewProds,recyclerViewCat;
    //RECYCLER PARA PRODCUTOS DESTACADOS
    ProductosAdapter productosDestacadosAdapter;
    List<ProdsDestacado> featured_products;
    //RECYCLER PARA CATEGORIAS DE PRODUCTOS
    CategoriasAdapter CatAdapter;
    List<Categoria> listCategoria=new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //IMPLEMENTACIÓN DEL SLIDER
        ImageSlider slider = binding.imageSlider;
        SliderView(slider);

        //IMPLEMENTACIÓN CARRUSEL PRODUCTOS DESTACADOS
        recyclerViewProds = binding.recyclerProdDescuentos;
        setProductosDestacadosRecycler(recyclerViewProds);

        //IMPLEMENTACIÓN CARRUSEL CATEGORIAS
        recyclerViewCat = binding.recyclerCategorias;
        setCategoriasRecycler(recyclerViewCat);
        return view;
    }

    /**
     * MÉTODO QUE GENERA UN RECYCLER VIEW DE CATEGORIA DE PRODUCTOS
     */
    private void setCategoriasRecycler(RecyclerView recyclerViewcategorias) {

//CONSUMO DE LA API SLIDER//
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://import-mag.com/getSlider/cat.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetServiceCategorias getServiceCategorias = retrofit.create(GetServiceCategorias.class);
        Call<List<Categoria>> call = getServiceCategorias.find();
        call.enqueue(new Callback<List<Categoria>>() {

            @Override
            public void onResponse(Call<List<Categoria>> call, retrofit2.Response<List<Categoria>> response) {
                List<Categoria> categoriaList = response.body();
                //Recorrido de los datos extraidos de la api e inserción en el View Slider
                for (Categoria s : categoriaList) {
                    String descr = html2text(s.getName().toString());
                    listCategoria.add(new Categoria(s.getId_category(),descr));
                }
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                recyclerViewcategorias.setLayoutManager(layoutManager);
                CatAdapter = new CategoriasAdapter(listCategoria,getActivity());
                recyclerViewcategorias.setAdapter(CatAdapter);

            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

                Toast.makeText(getActivity(), "Error al consumir api", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static String html2text(String html) {
        return Jsoup.parse(html).wholeText();
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
     * Método que genera un carrusel de ProdsDestacado Destacados
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

                        featured_products.add(new ProdsDestacado(id_product, name, url_image));

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