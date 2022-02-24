package com.import_mag.importmag.fragments.todosProductos;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.import_mag.importmag.adapter.ProductosAdapter;

import java.util.ArrayList;
import java.util.List;

//IMPORTACIONES DE SLIDER
import com.import_mag.importmag.databinding.FragmentRecyclerproductosBinding;

import com.import_mag.importmag.interfaces.GetServiceProds;
import com.import_mag.importmag.models.AllProds;
import com.import_mag.importmag.models.ProdsDestacados;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodosProductosFragment extends Fragment {

    private FragmentRecyclerproductosBinding binding;

    //VARIABLES DEL RECYCLERVIEW
    ProductosAdapter productosAdapter;
    RecyclerView recyclerViewcAllProds;

    //LISTA DE PRODUCTOS
    ArrayList<ProdsDestacados> prodsList = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecyclerproductosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //IMPLEMENTACIÓN Y LLAMADO AL RECYCLERVIEW
        recyclerViewcAllProds = binding.recyclerTodosProductos;
        recyclerAllProducts(recyclerViewcAllProds);

        return view;
    }

    /**
     * Método que genera un recycler view para mostrar los productos
     */
    private void recyclerAllProducts(RecyclerView recyclerViewTodosProductos) {

        //CONSUMO DE LA API PRODUCTOS//
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.import-mag.com/getSlider/prods.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetServiceProds getServiceProds = retrofit.create(GetServiceProds.class);
        Call<List<AllProds>> call = getServiceProds.find();
        call.enqueue(new Callback<List<AllProds>>() {

            @Override
            public void onResponse(Call<List<AllProds>> call, retrofit2.Response<List<AllProds>> response) {
                List<AllProds> Prods = response.body();
                //RECORRIDO DE LOS DATOS EXTRAIDOS DE LA API E INSERCIÓN EN EL VIEW SLIDER
                for (AllProds s : Prods) {

                    prodsList.add(new ProdsDestacados(s.getId_product(), s.getName(), "https://import-mag.com/" + s.getId_image() + "-large_default/" + s.getLink_rewrite() + ".jpg"));
                }
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                recyclerViewcAllProds.setLayoutManager(layoutManager);
                productosAdapter = new ProductosAdapter(getActivity(), prodsList);
                recyclerViewcAllProds.setAdapter(productosAdapter);
            }

            @Override
            public void onFailure(Call<List<AllProds>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al consumir api", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}