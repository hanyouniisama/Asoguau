package com.interfaces.daniel.asoguau.libreria;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by hanyou on 28/09/15.
 */
public final class VolleyAPI {
/*
    public static final String URL_WEBSERVICE = "http://192.168.56.1/Asoguau2/websevice";
    public static final String URL_CARPETA_IMAGENES_NOTICIAS = "http://192.168.56.1/Asoguau2/imagenes/noticia";
    public static final String URL_CARPETA_IMAGENES_USUARIOS = "http://192.168.56.1/Asoguau2/imagenes/usuario";
*/
/*
    public static final String URL_WEBSERVICE = "http://192.168.0.157/asoguau/android/websevice";
    public static final String URL_CARPETA_IMAGENES_NOTICIAS = "http://192.168.0.157/asoguau/android/imagenes/noticia";
    public static final String URL_CARPETA_IMAGENES_USUARIOS = "http://192.168.0.157/asoguau/android/imagenes/usuario";
*/

    public static final String URL_WEBSERVICE = "http://asoguau.esy.es/android/websevice";
    public static final String URL_CARPETA_IMAGENES_NOTICIAS = "http://asoguau.esy.es/android/imagenes/noticia";
    public static final String URL_CARPETA_IMAGENES_USUARIOS = "http://asoguau.esy.es/android/imagenes/usuario";

    public static final String URL_ACTUALIZAR_PERFIL = "/ActualizarPerfilUsuario.php";
    public static final String URL_PROCESAR_LOGIN = "/ProcesarLogin.php";
    public static final String URL_NOTICIAS = "/ObtenerTodasNoticias.php";
    public static final String URL_INSERTAR_NOTICIAS = "/InsertarNoticia.php";
    public static final String URL_REGISTRO = "/InsertarUsuario.php";
    public static final String URL_NOTICIAS_USUARIO = "/obtenernoticiausuario.php";
    public static final String URL_SUBIR_DONACION = "/InsertarDonacion.php";
    public static final String URL_OBTENER_DONACIONES = "/ObtenerTodasDonacion.php";

    public static final String TAG = "PostAdapter";
    private static VolleyAPI volleyAPI;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context context;


    public VolleyAPI(Context context) {
        volleyAPI.context = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });


    }

    public static synchronized VolleyAPI getInstance(Context context) {
        if (volleyAPI == null) {
            volleyAPI = new VolleyAPI(context);
        }

        return volleyAPI;
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
