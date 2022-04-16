package com.import_mag.importmag;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.import_mag.importmag.Activities.BuscarProdsActivity;
import com.import_mag.importmag.Activities.VentanaBuscarActivity;
import com.import_mag.importmag.databinding.ActivityMainBinding;
import com.import_mag.importmag.Activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        //IMPLEMENTACION BOTONES REDES SOCIALES
        ImageView ins, wpp, fb;
        ins = binding.imgINSicono;
        wpp = binding.imgWPPicono;
        fb = binding.imgFBicono;

        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String insId = "https://instagram.com/_u/importmagecuador/";
                String urlPage = "https://www.instagram.com/importmagecuador/";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(insId)));
                } catch (Exception e) {
                    Log.e(TAG, "Aplicación no Encontrada.");
                    //Abre url de pagina.
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                }

            }
        });

        wpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://wa.me/593994013402?text="));
                startActivity(intent);

            }

        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookId = "fb://page/102219398958150";
                String urlPage = "https://www.facebook.com/PruebaImport-102219398958150";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId)));
                } catch (Exception e) {
                    Log.e(TAG, "Aplicación no Encontrada.");
                    //Abre url de pagina.
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                }

            }
        });


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        BottomNavigationView navView = findViewById(R.id.nav_view2);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_categorias, R.id.nav_todos_productos, R.id.nav_favoritos)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itm_login:
                startActivity(new Intent(this, LoginActivity.class));
                return true;

            case R.id.itm_buscar:
                startActivity(new Intent(this, VentanaBuscarActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}