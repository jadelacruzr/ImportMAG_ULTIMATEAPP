package com.import_mag.importmag;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.import_mag.importmag.databinding.ActivityMainBinding;
import com.import_mag.importmag.login.Login;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        /*/BOTÓN FLOTANTE
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        boolean instalado = appInstalledOrNot ("com.whatsapp");
                        if(instalado){
                            Intent intent = new Intent (Intent.ACTION_VIEW);
                            String cotizacion ="Saludos, ";
                            intent.setData(Uri.parse("https://wa.me/593994013402?text="+cotizacion));
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "WhatsApp no está instalado en este dispositivo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_gallery, R.id.nav_todos_productos)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_login:
                startActivity(new Intent(this, Login.class));
                return true;
            /*case R.id.help:
                startActivity(new Intent(this, Help.class));
                return true;*/
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


    //MÉTODO QUE VERIFICA SI ESTÁ O NO INSTALADA UNA APLICACIÓN
    private boolean appInstalledOrNot (String url){
        PackageManager packageManager = getPackageManager();
        boolean app_instaled;
        try{
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_instaled=true;
        }catch (PackageManager.NameNotFoundException e){
            app_instaled=false;
        }
        return app_instaled;
    }
}