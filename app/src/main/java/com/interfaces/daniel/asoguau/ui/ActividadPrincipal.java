package com.interfaces.daniel.asoguau.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.interfaces.daniel.asoguau.R;
import com.interfaces.daniel.asoguau.libreria.VolleyAPI;

public class ActividadPrincipal extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);

        agregarToolBar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            preparaDrawer(navigationView);
            //Seleccionar el Item por Defecto
            seleccionarItem(navigationView.getMenu().getItem(0));

        }

        TextView txtNombre = (TextView) findViewById(R.id.nombre_usuario);

        final ImageView circleImageView = (ImageView) findViewById(R.id.avatar2);

        SharedPreferences preferences = getSharedPreferences("DatosUsuario", MODE_PRIVATE);

        String nombre = preferences.getString("nombre", "Errores");
        String apellido = preferences.getString("apellido", "Errores");

        txtNombre.setText(nombre + " " + apellido);

        Glide.with(this)
                .load(VolleyAPI.URL_CARPETA_IMAGENES_USUARIOS + "/" + preferences.getString("idusuario", "0") + ".jpg")
                .placeholder(R.drawable.perfil)
                .error(R.drawable.perfil)
                .dontAnimate()
                .into(circleImageView);

    }

    private void preparaDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                seleccionarItem(menuItem);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void seleccionarItem(MenuItem menuItem) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (menuItem.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new FragmentoInicio();
                break;
            case R.id.item_cuenta:
                // Fragmento para la sección Cuenta
                fragmentoGenerico = new FragmentoCuenta();
                break;
            case R.id.item_categorias:
                fragmentoGenerico = new FragmentoPeliculas();
                // Fragmento para la sección Categorías
                break;
            case R.id.item_ajustes:
                startActivity(new Intent(this, ActivityCambioIdioma.class));
                // Fragmento para la sección Categorías
                break;
            case R.id.item_Salir:
                finish();

        }

        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }
        setTitle(menuItem.getTitle());
    }

    private void agregarToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
