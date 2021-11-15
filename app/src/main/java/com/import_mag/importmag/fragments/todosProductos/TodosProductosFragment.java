package com.import_mag.importmag.fragments.todosProductos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.import_mag.importmag.adapter.TodosProductosAdapter;

import java.util.ArrayList;
import java.util.List;

//IMPORTACIONES DE SLIDER
import com.import_mag.importmag.databinding.FragmentTodosProductosBinding;
import com.import_mag.importmag.models.Productos;

public class TodosProductosFragment extends Fragment {

    private FragmentTodosProductosBinding binding;

    //VARIABLES DEL CARRUSEL PRODUCTOS DESCUENTO
    RecyclerView recyclerViewTodosProductos;
    TodosProductosAdapter todosProductosAdapter;
    List<Productos> prodsList;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTodosProductosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        //IMPLEMENTACIÓN CARRUSEL PRODUCTOS EN DESCUENTO
        recyclerViewTodosProductos = binding.recyclerTodosProductos;

        setProductosRecycler(recyclerViewTodosProductos);

        return view;
    }


    /**
     * Método que genera un carrusel de Productos Destacados
     */
    private void setProductosRecycler(RecyclerView recyclerViewTodosProductos) {
        /*
        //CONSUMO DE LA API Bootstrap > featuredProductsList (Lista de productos destacados)//
        String TAG = "";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.import-mag.com/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetServiceClient getServiceProductos = retrofit.create(GetServiceClient.class);
        Call<List<ProductosDestacados>> call = getServiceProductos.find();
        call.enqueue(new Callback<List<ProductosDestacados>>() {
            @Override
            public void onResponse(Call<List<ProductosDestacados>> call, retrofit2.Response<List<ProductosDestacados>> response) {

                List<ProductosDestacados> prodsList = response.body();

                prodDescuntoList = new ArrayList<>();
                //Recorrido de los datos extraidos de la api e inserción en el View Slider
                for (ProductosDestacados p : prodsList) {
                    Log.println(Log.INFO, TAG, "ID:PRODUCTO" + p.getId_product());

                    //prodDescuntoList.add(new ProductosDestacados(p.getId_product(), p.getName(), p.getPrice(), p.getRegular_price(), p.getF_discount(), p.getF_new(), p.getF_on_sale(), p.getUrlImage()));

                }
                /*RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                recyclerViewcprodDestacados.setLayoutManager(layoutManager);
                productosDestacadosAdapter = new ProductosDestacadosAdapter(getActivity(), prodDescuntoList);
                recyclerViewcprodDestacados.setAdapter(productosDestacadosAdapter);
            }

            @Override
            public void onFailure(Call<List<ProductosDestacados>> call, Throwable t) {

                Log.println(Log.INFO, TAG, "ERROR CONSUMIENDO LA API");
                Toast.makeText(getActivity(), "Error al consumir api", Toast.LENGTH_SHORT).show();
            }
        });
*/

        prodsList = new ArrayList<>();
        prodsList.add(new Productos(1, "Dispensador de alcohol", "8,34$", "8,34$", "-23%", null, null, "https://import-mag.com/46-large_default/dispensador-personal-bh414-.jpg"));
        prodsList.add(new Productos(2, "Dispensador de alcohol personal BH414", "10,25$", "10,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/44-large_default/dispensador-personal-de-alcohol.jpg"));
        prodsList.add(new Productos(4, "Dispensador de Alcohol BH406", "100,25$", "8,34$", "-23%", null, "¡En Oferta!", "https://import-mag.com/48-large_default/dosificador-de-alcohol-personal-bh2019.jpg"));
        prodsList.add(new Productos(5, "Dispensador de alcohol personal BH2019", "10,25$", "100,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/49-large_default/dosificador-de-alcohol-personal-bh413.jpg"));
        prodsList.add(new Productos(7, "Dispensador de alcohol personal BH413", "10,25$", "8,34$", null, null, "¡En Oferta!", "https://import-mag.com/50-large_default/collar-de-desinfeccion-.jpg"));
        prodsList.add(new Productos(1, "Collar de desinfección", "8,34$", "8,34$", "-23%", null, null, "https://import-mag.com/51-large_default/purificador-de-ambientes-dual-modelo-t100.jpg"));
        prodsList.add(new Productos(2, "Purificador de ambientes dual MODELO T100", "10,25$", "10,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/52-large_default/ozonificador-100.jpg"));
        prodsList.add(new Productos(4, "Dispensador de alcohol", "100,25$", "8,34$", "-23%", null, "¡En Oferta!", "https://import-mag.com/46-large_default/dispensador-personal-bh414-.jpg"));
        prodsList.add(new Productos(5, "Dispensador de alcohol personal BH414", "10,25$", "100,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/44-large_default/dispensador-personal-de-alcohol.jpg"));
        prodsList.add(new Productos(7, "Ejemplo", "10,25$", "8,34$", null, null, "¡En Oferta!", "https://import-mag.com/48-large_default/dosificador-de-alcohol-personal-bh2019.jpg"));
        prodsList.add(new Productos(1, "Dispensador de Alcohol BH406", "8,34$", "8,34$", "-23%", null, null, "https://import-mag.com/49-large_default/dosificador-de-alcohol-personal-bh413.jpg"));
        prodsList.add(new Productos(2, "Pntalon", "10,25$", "10,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/50-large_default/collar-de-desinfeccion-.jpg"));
        prodsList.add(new Productos(4, "Medias", "100,25$", "8,34$", "-23%", null, "¡En Oferta!", "https://import-mag.com/51-large_default/purificador-de-ambientes-dual-modelo-t100.jpg"));
        prodsList.add(new Productos(5, "Purificador de ambientes dual MODELO T100", "10,25$", "100,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/52-large_default/ozonificador-100.jpg\n"));
        prodsList.add(new Productos(7, "Ejemplo", "10,25$", "8,34$", null, null, "¡En Oferta!", "https://import-mag.com/48-large_default/dosificador-de-alcohol-personal-bh2019.jpg"));
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 2);
        recyclerViewTodosProductos.setLayoutManager(layoutManager2);
        todosProductosAdapter = new TodosProductosAdapter(getActivity(), prodsList);
        recyclerViewTodosProductos.setAdapter(todosProductosAdapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}