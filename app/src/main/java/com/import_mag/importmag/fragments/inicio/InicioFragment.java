package com.import_mag.importmag.fragments.inicio;

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
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.import_mag.importmag.adapter.ProductosDestacadosAdapter;
import com.import_mag.importmag.databinding.FragmentInicioBinding;

import java.util.ArrayList;
import java.util.List;

//IMPORTACIONES DE SLIDER
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.import_mag.importmag.interfaces.GetServiceClient;
import com.import_mag.importmag.interfaces.GetServiceSlider;
import com.import_mag.importmag.models.Bootstrap;
import com.import_mag.importmag.models.Productos;
import com.import_mag.importmag.models.ProductosDestacados;
import com.import_mag.importmag.models.Slider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;

    //VARIABLES DEL CARRUSEL PRODUCTOS DESCUENTO
    RecyclerView recyclerViewcprodDestacados ,recyclerViewcprodDestacados2;
    ProductosDestacadosAdapter productosDestacadosAdapter;
    List<ProductosDestacados> prodDescuntoList;



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
        ImageView ins,wpp,fb;
        ins= binding.imgINSicono;
        wpp=binding.imgWPPicono;
        fb=binding.imgFBicono;


        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String insId = "https://instagram.com/_u/importmagecuador/";
                String urlPage = "https://www.instagram.com/importmagecuador/";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(insId )));
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
                boolean instalado = appInstalledOrNot ("com.whatsapp");
                if(instalado){
                    Intent intent = new Intent (Intent.ACTION_VIEW);

                    intent.setData(Uri.parse("https://wa.me/593994013402?text="));
                    startActivity(intent);
                }else{
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
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId )));
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
     * Método que genera un carrusel de Productos Destacados
     */
    private void setProductosDestacadosRecycler(RecyclerView recyclerViewcprodDestacados) {
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

        prodDescuntoList = new ArrayList<>();
        prodDescuntoList.add(new ProductosDestacados(1, "Dispensador de alcohol", "8,34$", "8,34$", "-23%", null, null, "https://import-mag.com/46-large_default/dispensador-personal-bh414-.jpg"));
        prodDescuntoList.add(new ProductosDestacados(2, "Dispensador de alcohol personal BH414", "10,25$", "10,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/44-large_default/dispensador-personal-de-alcohol.jpg"));
        prodDescuntoList.add(new ProductosDestacados(4, "Dispensador de Alcohol BH406", "100,25$", "8,34$", "-23%", null, "¡En Oferta!", "https://import-mag.com/48-large_default/dosificador-de-alcohol-personal-bh2019.jpg"));
        prodDescuntoList.add(new ProductosDestacados(5, "Dispensador de alcohol personal BH2019", "10,25$", "100,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/49-large_default/dosificador-de-alcohol-personal-bh413.jpg"));
        prodDescuntoList.add(new ProductosDestacados(7, "Dispensador de alcohol personal BH413", "10,25$", "8,34$", null, null, "¡En Oferta!", "https://import-mag.com/50-large_default/collar-de-desinfeccion-.jpg"));
        prodDescuntoList.add(new ProductosDestacados(1, "Collar de desinfección", "8,34$", "8,34$", "-23%", null, null, "https://import-mag.com/51-large_default/purificador-de-ambientes-dual-modelo-t100.jpg"));
        prodDescuntoList.add(new ProductosDestacados(2, "Purificador de ambientes dual MODELO T100", "10,25$", "10,34$", null, "Nuevo", "¡En Oferta!", "https://import-mag.com/52-large_default/ozonificador-100.jpg"));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewcprodDestacados.setLayoutManager(layoutManager);

        productosDestacadosAdapter = new ProductosDestacadosAdapter(getActivity(),prodDescuntoList);
        recyclerViewcprodDestacados.setAdapter(productosDestacadosAdapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}