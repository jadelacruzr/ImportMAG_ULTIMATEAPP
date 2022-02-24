package com.import_mag.importmag.fragments.sobrenos;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.import_mag.importmag.R;
import com.import_mag.importmag.databinding.FragmentRecyclerproductosBinding;
import com.import_mag.importmag.databinding.FragmentSobrenosBinding;
import com.squareup.picasso.Picasso;

public class SobreNosFragment extends Fragment {
    private FragmentSobrenosBinding binding;
    ImageView imgSN1,imgSN2,imgSN3,imgSN4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSobrenosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        imgSN1=binding.imgSobrenos;
        imgSN2=binding.imgSobrenos2;
        imgSN3=binding.imgSobrenos3;
        imgSN4=binding.imgSobrenos4;

        imgSN1.setImageDrawable(getResources().getDrawable(R.drawable.sobrenos1));
        imgSN2.setImageDrawable(getResources().getDrawable(R.drawable.sobrenos2));
        imgSN3.setImageDrawable(getResources().getDrawable(R.drawable.sobrenos3));
        imgSN4.setImageDrawable(getResources().getDrawable(R.drawable.sobrenos4));

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}