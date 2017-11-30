package ar.com.emsoft.salvaguarda;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import ar.com.emsoft.salvaguarda.service.ApiClient;
import ar.com.emsoft.salvaguarda.service.utils.NetworkUtils;
import ar.com.emsoft.salvaguarda.tareas.TimeService;

public class WelcomeActivity extends AppCompatActivity {

    public static final long NOTIFY_INTERVAL = 15 * 1000; // 10 seconds

    TextView precipitacionVerde;
    TextView temperaturaVerde;
    TextView precipitacionAmarilla;
    TextView temperaturaAmarilla;
    Button botonCentroEvacuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //startService(new Intent(this, TimeService.class));
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
                Log.d(TAG, jsonObject.toString());
                if(nivelAlerta.equals(VERDE)){
                    setContentView(R.layout.activity_alarma_verde);
                    precipitacionVerde = findViewById(R.id.precipitacion_verde);
                    temperaturaVerde = findViewById(R.id.temperatura_verde);
                    precipitacionVerde.setText(precipitacion);
                    temperaturaVerde.setText(temperatura);

                }else if(nivelAlerta.equals(AMARILLO)){
                    setContentView(R.layout.activity_alarma_amarilla);
                    precipitacionAmarilla = findViewById(R.id.precipitacion_amarilla);
                    temperaturaAmarilla = findViewById(R.id.temperatura_amarilla);
                    precipitacionAmarilla.setText(precipitacion);
                    temperaturaAmarilla.setText(temperatura);
                }else {
                    setContentView(R.layout.activity_alarma_roja);
                    botonCentroEvacuacion = findViewById(R.id.boton_centro_evacuacion);
                    botonCentroEvacuacion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(WelcomeActivity.this, PuntoEvacuacionActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
