package com.import_mag.importmag.Interfaces;
import com.import_mag.importmag.Models.Slider;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetServiceSlider {
    //HACIENDO GET A LA API revSlider
    @GET("revSlider.php") Call<List<Slider>> find (/*@Query("q") String q*/);

}
