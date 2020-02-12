package com.projectviiigps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.*;
import com.projectviiigps.Data.dataPadre;
import com.projectviiigps.Others.Preferences;
import com.projectviiigps.WebService.Asynchtask;
import com.projectviiigps.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class localizacion extends AppCompatActivity implements OnMapReadyCallback , Asynchtask {

    private EditText edtlatitud, edtlongitud;
    //private EditText edtnombre;
    private Spinner spinerHijos;
    private ArrayAdapter adapter;
    private Button btncargar2;
    private GoogleMap mMap;
    private String array_spinner[];
    private static String var = "";
    private static int band = 0;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);

        Bundle b = getIntent().getExtras();

        //llamar web service a llenar los hijos y tomar el total de los hijos
        spinerHijos= (Spinner) findViewById(R.id.edtnombrenino);
        //edtnombre = (EditText) findViewById(R.id.edtnombrenino);
        edtlatitud = (EditText) findViewById(R.id.edtlatitud);
        edtlongitud = (EditText) findViewById(R.id.edtlongitud);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btncargar2 = (Button) findViewById(R.id.btncargar2);
        btncargar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    buscarnino("http://appgpsmovil.000webhostapp.com/webservices/datos.php?nombrenino="
                            + spinerHijos.getSelectedItem().toString() + "");
                }
                catch (Exception ex)
                {

                }
            }
        });

        band = 0;
        Map<String, String> datos = new HashMap<String, String>();
        String idpadre = Preferences.obtenerPreferenceStringId(this,Preferences.PREFERENCE_USUARIO_LOGIN_ID);
        WebService ws = new WebService("https://appgpsmovil.000webhostapp.com/webservices/listarhijos.php?id_usuario="+idpadre,datos,this,this);
        ws.execute("");

        try{
            if(b.getString("key").equals("true"))
            {
                var = b.getString("nombre");
                band=1;
                //btncargar2.callOnClick();
            }
        }
        catch (Exception ex)
        {
        }

        spinerHijos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
                if(band==0){
                    String nom = adapter.getItemAtPosition(i).toString();
                    //spinerHijos.notifyAll();
                    buscarnino("http://appgpsmovil.000webhostapp.com/webservices/datos.php?nombrenino="
                            + nom);
                }
                else {
                }
                //or this can be also right: selecteditem = level[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
            }
        });
    }

    private void buscarnino(String URL) {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        //edtnombre.setText(jsonObject.getString("nombreh"));
                        edtlatitud.setText(jsonObject.getString("latitud"));
                        edtlongitud.setText(jsonObject.getString("longitud"));
                        Double latitud = Double.parseDouble(edtlatitud.getText().toString());
                        Double longitud = Double.parseDouble(edtlongitud.getText().toString());
                        LatLng latLng = new LatLng(latitud,longitud);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.addMarker(new MarkerOptions().position(latLng).title("Punto. Lat: "+ latitud +", Lon:" + longitud));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                    } catch (JSONException e) {
                        Toast.makeText(localizacion.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(localizacion.this, "Error de conexion", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        enableMyLocationIfPermitted();
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "Permiso de ubicación no concedido, " +
                        "mostrando la ubicación por defecto",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(-1.0128, -79.4693);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }
        }
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject jsonIssues = new JSONObject(result);
        JSONArray jsonArrayIssue = jsonIssues.getJSONArray("Hijos");
        array_spinner=new String[jsonArrayIssue.length()];
        for(int i=0; i< jsonArrayIssue.length();i++)
        {
            JSONObject objIssue = jsonArrayIssue.getJSONObject(i);

            array_spinner[i]= objIssue.getString("nombreh");
        }
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, array_spinner);
        spinerHijos.setAdapter(adapter);


        //edtnombre.setText(b.getString("nombre"));
        if(band==1)
        {
            try {
                int spinnerPosition = adapter.getPosition(var);
                spinerHijos.setSelection(spinnerPosition);
                String userseleccionado = spinerHijos.getSelectedItem().toString();
                //spinerHijos.notifyAll();
                buscarnino("http://appgpsmovil.000webhostapp.com/webservices/datos.php?nombrenino="
                        + userseleccionado);
                band=0;
            }
            catch (Exception ex)             {
                Toast.makeText(this,"Error: "+ex.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    }


}

