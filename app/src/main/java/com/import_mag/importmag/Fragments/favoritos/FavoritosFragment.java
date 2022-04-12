package com.import_mag.importmag.Fragments.favoritos;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.import_mag.importmag.Activities.EditarListFavsActivity;
import com.import_mag.importmag.Activities.FavoritoDetallesActivity;
import com.import_mag.importmag.Activities.NuevaListaActivity;
import com.import_mag.importmag.Adapters.FavoritosAdapter;
import com.import_mag.importmag.Models.Favoritos;
import com.import_mag.importmag.databinding.FragmentFavoritosBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FavoritosFragment extends Fragment {
    public FavoritosFragment() {
    }

    private FragmentFavoritosBinding binding;

    //VARIABLES DEL RECYCLERVIEW
    FavoritosAdapter favsAdapter;
    RecyclerView recyclerViewcFavs;
    ImageView caragndo2;
    LinearLayout ll;
    TextView crearNewList;

    //LISTA DE FAVORITOS
    ArrayList<Favoritos> favsList = new ArrayList();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            binding = FragmentFavoritosBinding.inflate(inflater, container, false);
            View view = binding.getRoot();

            //IMPLEMENTACIÓN Y LLAMADO AL RECYCLERVIEW
            recyclerViewcFavs = binding.recyclerFavoritos;
            caragndo2 = binding.imgCargandoFav;
            ll = binding.llHome;

            crearNewList=binding.txtCrearLista;
            crearNewList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), NuevaListaActivity.class);
                    startActivity(i);

                }
            });

            caragndo2.setVisibility(View.VISIBLE);
            recyclerViewcFavs.setVisibility(View.INVISIBLE);


            if (isOnlineNet() == true) {
                consultaFavs(recyclerViewcFavs);
            }   else {
            Toast.makeText(getActivity(), "Revisa tu conexión a Internet", Toast.LENGTH_SHORT).show();
        }


            return view;
        } catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }
    }

    private void consultaFavs(RecyclerView recyclerViewcFavs) {

        final String url = "https://import-mag.com/rest/wishlist?action=list";

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jasonArray = jsonObject.getJSONArray("psdata"); //encabezado de WS

                    int tam = jasonArray.length();

                    favsList = new ArrayList<>();
                    for (int i = 0; i < tam; i++) {
                        JSONObject psdata = jasonArray.getJSONObject(i);
                        String id_wish = psdata.getString("id_wishlist");
                        String name = psdata.getString("name");
                        if(name.contains("My wishlist")){
                            String nuevoName= name.replaceAll("My wishlist", "Mis favoritos");
                            name=nuevoName;

                        }
                        String cantList = psdata.getString("nbProducts");
                        System.out.println(id_wish + " " + name);
                        favsList.add(new Favoritos(id_wish, name, cantList));

                    }

                    favsAdapter = new FavoritosAdapter(getActivity(), favsList);
                    recyclerViewcFavs.setAdapter(favsAdapter);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerViewcFavs.setLayoutManager(layoutManager);
                    ll.setVisibility(View.VISIBLE);
                    caragndo2.setVisibility(View.GONE);
                    recyclerViewcFavs.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Handle your errors
            }
        };

        StringRequest request2 = new StringRequest(Request.Method.GET, url,
                responseListener, errorListener) {
        };
        Volley.newRequestQueue(getContext()).add(request2);


    }
    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void onResume() {
        super.onResume();
        try {

            //IMPLEMENTACIÓN Y LLAMADO AL RECYCLERVIEW
            recyclerViewcFavs = binding.recyclerFavoritos;
            caragndo2 = binding.imgCargandoFav;
            ll = binding.llHome;

            caragndo2.setVisibility(View.VISIBLE);
            recyclerViewcFavs.setVisibility(View.INVISIBLE);


            if (isOnlineNet() == true) {
                consultaFavs(recyclerViewcFavs);
            }   else {
                Toast.makeText(getActivity(), "Revisa tu conexión a Internet", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}


