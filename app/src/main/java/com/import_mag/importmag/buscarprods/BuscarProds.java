package com.import_mag.importmag.buscarprods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.import_mag.importmag.R;
import com.import_mag.importmag.adapter.ProductosAdapter;
import com.import_mag.importmag.models.Productos;

import java.util.ArrayList;
import java.util.List;

public class BuscarProds extends AppCompatActivity {

    //VARIABLES DEL CARRUSEL PRODUCTOS DESCUENTO
    RecyclerView rvTodosProductos;
    ProductosAdapter productosAdapter;
    List<Productos> prodsEnconList;
    private ImageView cerrar3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_prods);

        //IMPLEMENTACIÓN CARRUSEL PRODUCTOS EN DESCUENTO
        rvTodosProductos = findViewById(R.id.recyclerTodosProductos);
        setProductosRecycler(rvTodosProductos);

        cerrar3=findViewById(R.id.salirBusqueda);



        /**
         * Método que cierra la actividad de Registro
         */
        cerrar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    /**
     * Método que genera un carrusel de Productos Encontrados
     */
    private void setProductosRecycler(RecyclerView recyclerViewTodosProductos) {

        prodsEnconList = new ArrayList<>();
        prodsEnconList.add(new Productos(1, "Dispensador de alcohol", "8,34$", "8,34$", "-23%", null, null, "https://import-mag.com/46-large_default/dispensador-personal-bh414-.jpg"));
        prodsEnconList.add(new Productos(2, "Dispensador de alcohol personal BH414", "10,25$", "10,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/44-large_default/dispensador-personal-de-alcohol.jpg"));
        prodsEnconList.add(new Productos(4, "Dispensador de Alcohol BH406", "100,25$", "8,34$", "-23%", null, "¡En Oferta!", "https://import-mag.com/48-large_default/dosificador-de-alcohol-personal-bh2019.jpg"));
        prodsEnconList.add(new Productos(5, "Dispensador de alcohol personal BH2019", "10,25$", "100,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/49-large_default/dosificador-de-alcohol-personal-bh413.jpg"));
        prodsEnconList.add(new Productos(7, "Dispensador de alcohol personal BH413", "10,25$", "8,34$", null, null, "¡En Oferta!", "https://import-mag.com/50-large_default/collar-de-desinfeccion-.jpg"));
        prodsEnconList.add(new Productos(1, "Collar de desinfección", "8,34$", "8,34$", "-23%", null, null, "https://import-mag.com/51-large_default/purificador-de-ambientes-dual-modelo-t100.jpg"));
        prodsEnconList.add(new Productos(2, "Purificador de ambientes dual MODELO T100", "10,25$", "10,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/52-large_default/ozonificador-100.jpg"));
        prodsEnconList.add(new Productos(4, "Dispensador de alcohol", "100,25$", "8,34$", "-23%", null, "¡En Oferta!", "https://import-mag.com/46-large_default/dispensador-personal-bh414-.jpg"));
        prodsEnconList.add(new Productos(5, "Dispensador de alcohol personal BH414", "10,25$", "100,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/44-large_default/dispensador-personal-de-alcohol.jpg"));
        prodsEnconList.add(new Productos(7, "Ejemplo", "10,25$", "8,34$", null, null, "¡En Oferta!", "https://import-mag.com/48-large_default/dosificador-de-alcohol-personal-bh2019.jpg"));
        prodsEnconList.add(new Productos(1, "Dispensador de Alcohol BH406", "8,34$", "8,34$", "-23%", null, null, "https://import-mag.com/49-large_default/dosificador-de-alcohol-personal-bh413.jpg"));
        prodsEnconList.add(new Productos(2, "Pntalon", "10,25$", "10,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/50-large_default/collar-de-desinfeccion-.jpg"));
        prodsEnconList.add(new Productos(4, "Medias", "100,25$", "8,34$", "-23%", null, "¡En Oferta!", "https://import-mag.com/51-large_default/purificador-de-ambientes-dual-modelo-t100.jpg"));
        prodsEnconList.add(new Productos(5, "Purificador de ambientes dual MODELO T100", "10,25$", "100,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/52-large_default/ozonificador-100.jpg\n"));
        prodsEnconList.add(new Productos(7, "Ejemplo", "10,25$", "8,34$", null, null, "¡En Oferta!", "https://import-mag.com/48-large_default/dosificador-de-alcohol-personal-bh2019.jpg"));
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(this, 2);
        recyclerViewTodosProductos.setLayoutManager(layoutManager2);
        productosAdapter = new ProductosAdapter(this, prodsEnconList);
        recyclerViewTodosProductos.setAdapter(productosAdapter);
    }

}