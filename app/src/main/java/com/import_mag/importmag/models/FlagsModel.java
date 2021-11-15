package com.import_mag.importmag.models;

import com.google.gson.annotations.SerializedName;

public class FlagsModel {
    // flags

    @SerializedName("discount")
    String f_discount ;// Indicador de Descuento
    @SerializedName("new")
    String f_new ;// Indicador de nuevo;
    @SerializedName("on_sale")
    String f_on_sale; //Indicador de oferta;

}
