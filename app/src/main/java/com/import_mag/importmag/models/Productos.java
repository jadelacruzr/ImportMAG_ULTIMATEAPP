package com.import_mag.importmag.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Productos {
    @SerializedName("id_product")
    Integer id_product;
    @SerializedName("name")
    String name_product; //nombreProducto
    @SerializedName("price")
    String precioVenta; //Precio de Venta (con descuento)
    @SerializedName("regular_price")
    String precioNormal;//Precio Normal (sin descuentos)
        /*Double PrecioNormal;//regular_price_amount
    Double PrecioVenta;  //price_amount*/


    @SerializedName("discount")
    String f_discount;// Indicador de Descuento
    @SerializedName("new")
    String f_new;// Indicador de nuevo;
    @SerializedName("on_sale")
    String f_on_sale; //Indicador de oferta;

    @SerializedName("images")
    String images;

    public Productos(Context contexto, ArrayList<com.import_mag.importmag.models.ProductosDestacados> psdata) {


    }

    public Productos(Integer id_product, String name_product, String precioVenta, String precioNormal, String f_discount, String f_new, String f_on_sale, String images) {
        this.id_product = id_product;
        this.name_product = name_product;
        this.precioVenta = precioVenta;
        this.precioNormal = precioNormal;
        this.f_discount = f_discount;
        this.f_new = f_new;
        this.f_on_sale = f_on_sale;
        this.images = images;
    }

    public Integer getId_product() {
        return id_product;
    }

    public void setId_product(Integer id_product) {
        this.id_product = id_product;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getPrecioNormal() {
        return precioNormal;
    }

    public void setPrecioNormal(String precioNormal) {
        this.precioNormal = precioNormal;
    }

    public String getF_discount() {
        return f_discount;
    }

    public void setF_discount(String f_discount) {
        this.f_discount = f_discount;
    }

    public String getF_new() {
        return f_new;
    }

    public void setF_new(String f_new) {
        this.f_new = f_new;
    }

    public String getF_on_sale() {
        return f_on_sale;
    }

    public void setF_on_sale(String f_on_sale) {
        this.f_on_sale = f_on_sale;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
