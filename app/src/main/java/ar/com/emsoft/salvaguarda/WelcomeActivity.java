package ar.com.emsoft.salvaguarda;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import ar.com.emsoft.salvaguarda.service.ApiClient;
import ar.com.emsoft.salvaguarda.service.utils.NetworkUtils;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        loadAlerta();
    }

    private void loadAlerta() {
        FetchAlertaAsyncTask fetchAlertaAsyncTask = new FetchAlertaAsyncTask();
        fetchAlertaAsyncTask.execute();
    }

    public class FetchAlertaAsyncTask extends AsyncTask<Void, Void, JSONObject> {

        private final String TAG = FetchAlertaAsyncTask.class.getSimpleName();
        private final String VERDE = "Verde";
        private final String AMARILLO = "Amarillo";
        private final String ROJO = "Rojo";

        ApiClient apiClient;

        @Override
        protected JSONObject doInBackground(Void... voids) {
            apiClient = new ApiClient();
            String response = apiClient.getUltimaAlerta();
            JSONObject jsonResponse = null;
            try {
                jsonResponse = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Log.d(TAG, jsonObject.toString());
            JSONObject alerta;
            try {
                alerta = jsonObject.getJSONObject("entity");
                String nivelAlerta = alerta.getString("nivelAlerta");
                String precipitacion = alerta.getString("precipitacion");
                String temperatura = alerta.getString("temperatura");
                String fechaAlerta = alerta.getString("fechaAlerta");
                Log.d(TAG, jsonObject.toString());
                if(nivelAlerta.equals(VERDE)){
                    Intent intent = new Intent(WelcomeActivity.this, AlarmaVerdeActivity.class);
                    startActivity(intent);
                }else if(nivelAlerta.equals(AMARILLO)){
                    Intent intent = new Intent(WelcomeActivity.this, AlarmaAmarillaActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(WelcomeActivity.this, AlarmaRojaActivity.class);
                    startActivity(intent);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
