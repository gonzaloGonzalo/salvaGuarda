package ar.com.emsoft.salvaguarda.service;

import android.util.Log;

import java.net.URL;

import ar.com.emsoft.salvaguarda.service.utils.NetworkUtils;

/**
 * Created by admin on 28/11/2017.
 */

public class ApiClient {

    private static final String TAG = ApiClient.class.getSimpleName();

    private static final String IP = "http://192.168.0.38";
    private static final String PORT = "8080";
    private static final String DP = ":";
    private static final String BR = "/";
    private static final String SERVICE = "salvaguarda";
    private static final String ALERTA = "alerta";
    private static final String ALERTAS = "alertas";
    private static final String MENSAJE = "mensaje";
    public String getUltimaAlerta(){
        return getResponse(IP+DP+PORT+BR+SERVICE+BR+ALERTA);
    }

    public String getTodasAlertas(){
        return getResponse(IP+DP+PORT+BR+SERVICE+BR+ALERTA);
    }

    public String getUltimoMensaje(){
        return getResponse(IP+DP+PORT+BR+SERVICE+BR+MENSAJE);
    }

    private String getResponse(String endpoint) {
        Log.d(TAG, "Endpoint: "+endpoint);
        URL url = NetworkUtils.buildUrl(endpoint);
        String response = NetworkUtils.getResponseFromHttpUrl(url);
        Log.d(TAG, "Response: "+response);
        return response;
    }
}
