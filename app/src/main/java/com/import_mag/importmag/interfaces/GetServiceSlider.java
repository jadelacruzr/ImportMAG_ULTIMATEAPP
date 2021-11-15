package com.import_mag.importmag.interfaces;
import com.import_mag.importmag.models.Slider;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetServiceSlider {
    //HACIENDO GET A LA API revSlider
    @GET("revSlider.php") Call<List<Slider>> find (/*@Query("q") String q*/);

}
