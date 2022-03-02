package com.import_mag.importmag.Interfaces;
import com.import_mag.importmag.Models.Categoria;
import com.import_mag.importmag.Models.ProdAll;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetServiceCategorias {
    //HACIENDO GET A LA API que obtiene las ids de las categorias

    @GET("cat.php") Call<List<Categoria>> find ();
}
