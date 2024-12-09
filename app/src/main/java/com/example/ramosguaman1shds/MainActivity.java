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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView Texto;
     Button btnTex;

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

        btnTex.setOnClickListener(v -> obtenerServicioWeb("http://10.10.35.220:3000/ramos"));
    }
   private void obtenerServicioWeb (String url){
       StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
               response -> {

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
   }

