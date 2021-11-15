package com.import_mag.importmag.fragments.detallesprods;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.import_mag.importmag.R;
import com.import_mag.importmag.databinding.FragmentProductoDetallesBinding;

public class ProductoDetallesFragment extends Fragment {
    private FragmentProductoDetallesBinding binding;

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Intent i = get;
        String name = i.getStringExtra("name");
*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producto_detalles, container, false);
    }
}