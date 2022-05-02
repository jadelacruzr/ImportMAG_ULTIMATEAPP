package com.import_mag.importmag.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.import_mag.importmag.Activities.RegistrarseActivity;
import com.import_mag.importmag.Adapters.ProductosAdapter;

import java.util.ArrayList;
import java.util.List;

//IMPORTACIONES DE SLIDER
import com.import_mag.importmag.Models.ProdsDestacado;
import com.import_mag.importmag.R;
import com.import_mag.importmag.databinding.FragmentTodosproductosBinding;

import com.import_mag.importmag.Interfaces.GetServiceProds;
import com.import_mag.importmag.Models.ProdAll;

import org.jsoup.Jsoup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodosProductosFragment extends Fragment {

    private FragmentTodosproductosBinding binding;

    //VARIABLES DEL RECYCLERVIEW
    ProductosAdapter productosAdapter;
    RecyclerView recyclerViewcAllProds;
    ImageView caragndo2;
    View viewmsg;

    //LISTA DE PRODUCTOS
    ArrayList<ProdsDestacado> prodsList = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTodosproductosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //IMPLEMENTACIÓN Y LLAMADO AL RECYCLERVIEW
        recyclerViewcAllProds = binding.recyclerTodosProductos;
        caragndo2 = binding.imgCargando2;


        recyclerViewcAllProds.setVisibility(View.INVISIBLE);


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
        Call<List<ProdAll>> call = getServiceProds.find();
        if (isOnlineNet() == true) {
            call.enqueue(new Callback<List<ProdAll>>() {

                @Override
                public void onResponse(Call<List<ProdAll>> call, retrofit2.Response<List<ProdAll>> response) {
                    List<ProdAll> Prods = response.body();
                    //RECORRIDO DE LOS DATOS EXTRAIDOS DE LA API E INSERCIÓN EN EL VIEW SLIDER
                    for (ProdAll s : Prods) {
                        String nuevapalabra = "";
                        String name = s.getName();
                        if (name.contains("Ã¡") || name.contains("Ã©") || name.contains("Ã\u00AD") || name.contains("Ã³") || name.contains("Ãº")) {
                            nuevapalabra = name.replaceAll("Ã¡", "á").replaceAll("Ã©", "é").replaceAll
                                    ("Ã\u00AD", "í").replaceAll("Ã³", "ó").replaceAll
                                    ("Ãº", "ú");

                        } else nuevapalabra = s.getName();
                        prodsList.add(new ProdsDestacado(s.getId_product(), nuevapalabra, "https://import-mag.com/" + s.getId_image() + "-large_default/" + s.getLink_rewrite() + ".jpg"));
                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                    recyclerViewcAllProds.setLayoutManager(layoutManager);
                    productosAdapter = new ProductosAdapter(getActivity(), prodsList);
                    recyclerViewcAllProds.setAdapter(productosAdapter);

                    recyclerViewcAllProds.setVisibility(View.VISIBLE);

                    caragndo2.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<List<ProdAll>> call, Throwable t) {

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

    public Boolean isOnlineNet() {

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}