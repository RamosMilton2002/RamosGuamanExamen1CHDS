package com.example.ramosguaman1shds;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView Texto ,txtsm;
     Button btnTex, btnmil, btnsm, btns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Texto = findViewById(R.id.txtYo);
        btnTex = findViewById(R.id.btnYo);
        btnmil = findViewById(R.id.btnMil);
        btnsm = findViewById(R.id.btnSum);
        txtsm = findViewById(R.id.txtSuma);
        btns = findViewById(R.id.btnSuma);

        btnTex.setOnClickListener(v -> obtenerServicioWeb("http://10.10.33.47:3001/nombre"));
        btnmil.setOnClickListener(v -> obtenerServicioWeb2("http://10.10.33.47:3001/milton"));
        btnsm.setOnClickListener(v -> obtenerServicioWeb3("http://10.10.33.47:3001/suma"));
        btns.setOnClickListener(v -> {
            // Obtener el valor de txtsm y pasarlo como parámetro
            String numero = txtsm.getText().toString().trim();
            if (!numero.isEmpty()) {
                obtenerServicioWeb4("http://10.10.33.47:3001/suma/" + numero);
            } else {
                Toast.makeText(getApplicationContext(), "Por favor ingresa un número", Toast.LENGTH_SHORT).show();
            }
        });

    }
   private void obtenerServicioWeb (String url){
       StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                     response -> {
                         try {
                             JSONArray j=new JSONArray(response.toString());
                             for (int i = 0; i <= j.length()-1; i++) {
                                 JSONObject res = j.getJSONObject(i);
                                 Texto.setText(res+"");
                             }
                         } catch (JSONException e) {
                             throw new RuntimeException(e);
                         }
                         Texto.setText(response);
               },error -> {
           Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
       }) {
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               return new HashMap<>();
           }
       };
       RequestQueue requestQueue = Volley.newRequestQueue(this);
       requestQueue.add(stringRequest);
   }


    private void obtenerServicioWeb2(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Mostrar directamente la respuesta en el TextView
                    Texto.setText(response);
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return new HashMap<>();
            }
        };

        // Agregar la solicitud a la cola
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void obtenerServicioWeb3(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Mostrar directamente el resultado en el TextView
                    Texto.setText("La suma es: " + response);
                },
                error -> {
                    // Mostrar error si falla la solicitud
                    Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return new HashMap<>();
            }
        };

        // Agregar la solicitud a la cola
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void obtenerServicioWeb4(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> Texto.setText("Resultado: " + response),
                error -> Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}



