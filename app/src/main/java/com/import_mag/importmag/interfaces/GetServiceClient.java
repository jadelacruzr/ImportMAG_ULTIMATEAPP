package com.import_mag.importmag.interfaces;
import com.import_mag.importmag.models.Bootstrap;
import com.import_mag.importmag.models.ProductosDestacados;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetServiceClient {
    //HACIENDO GET A LA API bootstrap que contiene la featuredProductList (Lista de productos destacados)

    @GET("bootstrap") Call<List<ProductosDestacados>> find ();
}
