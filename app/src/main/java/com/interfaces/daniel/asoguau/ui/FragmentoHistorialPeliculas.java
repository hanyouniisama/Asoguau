package com.interfaces.daniel.asoguau.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.interfaces.daniel.asoguau.R;
import com.interfaces.daniel.asoguau.libreria.GsonRequest;
import com.interfaces.daniel.asoguau.libreria.VolleyAPI;
import com.interfaces.daniel.asoguau.modelo.Errores;
import com.interfaces.daniel.asoguau.modelo.Noticias;
import com.interfaces.daniel.asoguau.utilidades.DialogoCarga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanyou on 16/09/15.
 */
public class FragmentoHistorialPeliculas extends Fragment implements View.OnClickListener {

    private LinearLayoutManager layoutManager;

    public FragmentoHistorialPeliculas() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View view;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        final RecyclerView reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);

        final DialogoCarga dialogoCarga = new DialogoCarga(getActivity());
        dialogoCarga.mostrarDialogo("Cargando Noticias");

        Map<String, String> parametros = new HashMap<String, String>();

        SharedPreferences preferences = getActivity().getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);

        parametros.put("idusuario", preferences.getString("idusuario", String.valueOf(0)));
        parametros.put("idtiponoticia", String.valueOf(2));

        VolleyAPI.getInstance(getActivity().getBaseContext()).addToRequestQueue(
                new GsonRequest<Noticias>(
                        Request.Method.POST,
                        VolleyAPI.URL_WEBSERVICE + VolleyAPI.URL_NOTICIAS,
                        Noticias.class,
                        null,
                        new Response.Listener<Noticias>() {
                            @Override
                            public void onResponse(Noticias response) {

                                if (response != null && response.getItems().size() > 0) {

                                    AdaptadorHistorialPeliculas adaptador = new AdaptadorHistorialPeliculas(response.getItems(), getActivity());
                                    reciclador.setAdapter(adaptador);
                                    reciclador.addItemDecoration(new DecoracionLineaDivisoria(getActivity()));
                                } else {
                                    List<Errores> errores = new ArrayList<Errores>();
                                    errores.add(new Errores("No ha publicado noticias"));
                                    AdaptadorError error = new AdaptadorError(errores, getActivity());
                                    reciclador.setAdapter(error);
                                }
                                dialogoCarga.ocultarDialogo();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialogoCarga.ocultarDialogo();
                            }
                        },
                        parametros
                ));




        FloatingActionButton agregar = (FloatingActionButton) view.findViewById(R.id.mas);
        agregar.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        Intent intent = new Intent(v.getContext(), NuevaNoticia.class);
        startActivity(intent);
/*
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, noticia, "DialogoNoticia")
                .addToBackStack(null)
                .commit();
*/
    }
}
