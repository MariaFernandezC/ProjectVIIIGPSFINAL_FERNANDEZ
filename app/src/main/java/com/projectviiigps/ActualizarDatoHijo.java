package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.projectviiigps.Others.Preferences;
import com.projectviiigps.WebService.Asynchtask;
import com.projectviiigps.WebService.WebService;
import com.projectviiigps.WebService2.Asynchtask2;
import com.projectviiigps.WebService2.WebService2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActualizarDatoHijo extends AppCompatActivity  implements AdapterView.OnItemSelectedListener, Asynchtask, Asynchtask2,View.OnClickListener{


    String[] nombres, idhijos;
    String idpadre, idhijo;

    TextView txtNombre, txtApellido,txtEdad, txtDireccion, txtTipoSangre, txtEnfermedad, txtAlergia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_dato_hijo);

        txtNombre = (TextView)findViewById(R.id.txtNombre);
        txtApellido = (TextView)findViewById(R.id.txtApellido);
        txtEdad = (TextView)findViewById(R.id.txtEdad);
        txtDireccion = (TextView)findViewById(R.id.txtDireccion);
        txtTipoSangre = (TextView)findViewById(R.id.txtTipoSangre);
        txtEnfermedad = (TextView)findViewById(R.id.txtEnfermedad);
        txtAlergia = (TextView)findViewById(R.id.txtAlergia);


        idpadre= Preferences.obtenerPreferenceStringId(this,Preferences.PREFERENCE_USUARIO_LOGIN_ID);


        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("https://appgpsmovil.000webhostapp.com/webservices/hijos.php?usuario="+idpadre+"", datos,
                ActualizarDatoHijo.this, ActualizarDatoHijo.this);
        ws.execute("");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        LlamarDatos(position);

    }


    private void LlamarDatos(int position){
        idhijo=idhijos[position];
        Map<String, String> auxiliar = new HashMap<String, String>();
        WebService2 ws2= new WebService2("https://appgpsmovil.000webhostapp.com/webservices/datosHijo.php?hijo="+idhijos[position]+"", auxiliar,
                ActualizarDatoHijo.this, ActualizarDatoHijo.this);
        ws2.execute("");
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getApplicationContext(),null, Toast.LENGTH_LONG).show();

    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.i("processFinish", result);
        JSONArray jsonArray = new JSONArray(result);
        //JSONObject jsonObject=new JSONObject(String.valueOf(jsonArray));
        idhijos=new String[jsonArray.length()];
        nombres=new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject=jsonArray.getJSONObject(i);
            idhijos[i]=jsonObject.getString("idhijo");
            nombres[i]=jsonObject.getString("nombreh");
            //noticias.add(new Noticias(datos.getJSONObject(i)));
        }

        //Adaptador
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,nombres);

        //AdapterView
        //Spinner
        Spinner cmbOpciones = (Spinner)findViewById(R.id.cmbOpciones);
        adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        cmbOpciones.setAdapter(adaptador);
        cmbOpciones.setOnItemSelectedListener(this);
    }

    @Override
    public void processFinish2(String result2) throws JSONException {
        Log.i("processFinish", result2);
        JSONObject jsonObj= new JSONObject(result2);
        txtNombre.setText(jsonObj.getString("nombreh").trim());
        txtApellido.setText(jsonObj.getString("apellidoh").trim());
        txtEdad.setText(jsonObj.getString("edadh").trim());
        txtDireccion.setText(jsonObj.getString("direccionh").trim());
        txtEdad.setText(jsonObj.getString("edadh").trim());
        txtTipoSangre.setText(jsonObj.getString("tiposangre").trim());
        txtEnfermedad.setText(jsonObj.getString("enfermedad").trim());
        txtAlergia.setText(jsonObj.getString("alergia").trim());
    }

    @Override
    public void onClick(View v) {
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("nombreh",txtNombre.getText().toString().trim());
        datos.put("apellidoh",txtApellido.getText().toString().trim());
        datos.put("edadh",txtEdad.getText().toString().trim());
        datos.put("direccionh",txtDireccion.getText().toString().trim());
        datos.put("tiposangre",txtTipoSangre.getText().toString().trim());
        datos.put("enfermedad",txtEnfermedad.getText().toString().trim());
        datos.put("alergia",txtAlergia.getText().toString().trim());
        datos.put("idhijo",idhijo);

        WebService2 ws2= new WebService2("https://appgpsmovil.000webhostapp.com/webservices/actualizarHijo.php", datos,
                ActualizarDatoHijo.this, ActualizarDatoHijo.this);
        ws2.execute("");

        Toast.makeText(getApplicationContext(),"Datos actualizados", Toast.LENGTH_LONG).show();

    }
}
