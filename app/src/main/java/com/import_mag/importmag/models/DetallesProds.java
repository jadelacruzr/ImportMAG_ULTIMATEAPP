package com.import_mag.importmag.models;

import com.google.gson.annotations.SerializedName;

public class DetallesProds {
    @SerializedName("id_product")
    Integer id_product;
    @SerializedName("name")
    String name_product; //Nombre del Producto
    @SerializedName("description_short")
    String description_short; //Subtítulo
    @SerializedName("description")
    String description; //Descricion del Producto
    @SerializedName("images")
    String imageDefault; //Imagen de portada del producto
    @SerializedName("images")
    String otrasImages; //Imagen de portada del producto


    public DetallesProds(Integer id_product, String name_product, String description_short, String description, String imageDefault, String otrasImages) {
        this.id_product = id_product;
        this.name_product = name_product;
        this.description_short = description_short;
        this.description = description;
        this.imageDefault = imageDefault;
        this.otrasImages = otrasImages;
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

    public String getDescription_short() {
        return description_short;
    }

    public void setDescription_short(String description_short) {
        this.description_short = description_short;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageDefault() {
        return imageDefault;
    }

    public void setImageDefault(String imageDefault) {
        this.imageDefault = imageDefault;
    }

    public String getOtrasImages() {
        return otrasImages;
    }

    public void setOtrasImages(String otrasImages) {
        this.otrasImages = otrasImages;
    }
}
