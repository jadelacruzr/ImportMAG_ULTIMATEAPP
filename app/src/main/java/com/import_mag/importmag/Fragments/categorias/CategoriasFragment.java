package com.import_mag.importmag.Fragments.categorias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.import_mag.importmag.Activities.RegistrarseActivity;
import com.import_mag.importmag.Adapters.CategoriasAdapter;
import com.import_mag.importmag.Interfaces.GetServiceCategorias;
import com.import_mag.importmag.Models.Categoria;
import com.import_mag.importmag.R;
import com.import_mag.importmag.databinding.FragmentCategoriasBinding;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CategoriasFragment extends Fragment {
    private FragmentCategoriasBinding binding;

    //RECYCLER PARA CATEGORIAS DE PRODUCTOS
    ImageView cargando;
    CategoriasAdapter CatAdapter;
    List<Categoria> listCategoria = new ArrayList<>();
    RecyclerView recyclerViewCat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoriasBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //IMPLEMENTACIÓN CARRUSEL CATEGORIAS
        recyclerViewCat = binding.recyclerCategorias;
        cargando = binding.imgCargando2;

        recyclerViewCat.setVisibility(View.INVISIBLE);
        setCategoriasRecycler(recyclerViewCat);


        return view;
    }

    /**
     * MÉTODO QUE GENERA UN RECYCLER VIEW DE CATEGORIA DE PRODUCTOS
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
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerViewcategorias.setLayoutManager(layoutManager);
                    CatAdapter = new CategoriasAdapter(listCategoria, getActivity());
                    recyclerViewcategorias.setAdapter(CatAdapter);
                    recyclerViewCat.setVisibility(View.VISIBLE);
                    cargando.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<Categoria>> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Revisa tu conexión a Internet", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}