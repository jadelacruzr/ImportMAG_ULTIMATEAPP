package com.import_mag.importmag.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Bootstrap {
    String success;
    String code;
    //ArrayList<psdata>psdata;
    /*public class  psdata {
        String numberOfFeaturedProd;
        ArrayList<ProductosDestacados>featuredProductsList;

    }
*/
    public Bootstrap(String success, String code /*,ArrayList<Bootstrap.psdata> psdata*/) {
        this.success = success;
        this.code = code;
        //this.psdata = psdata;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

   /* public ArrayList<Bootstrap.psdata> getPsdata() {
        return psdata;
    }

    public void setPsdata(ArrayList<Bootstrap.psdata> psdata) {
        this.psdata = psdata;
    }*/
}
