package com.import_mag.importmag.Models;

public class Categoria {
    private String id_category, name;

    public Categoria(String id_category, String name) {
        this.id_category = id_category;
        this.name = name;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
