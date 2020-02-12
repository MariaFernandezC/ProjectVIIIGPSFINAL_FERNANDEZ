package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.projectviiigps.Others.Preferences;
import com.projectviiigps.WebService.Asynchtask;
import com.projectviiigps.WebService.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActualizarDatos extends AppCompatActivity implements Asynchtask, View.OnClickListener{

    TextView txtCI, txtNombre, txtApellido, txtDireccion, txtTelefono, txtEdad, txtCorreo;
    String idpadre;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos);

        txtCI = (TextView)findViewById(R.id.txtCI);
        txtNombre = (TextView)findViewById(R.id.txtNombre);
        txtApellido = (TextView)findViewById(R.id.txtApellido);
        txtDireccion = (TextView)findViewById(R.id.txtDireccion);
        txtTelefono = (TextView)findViewById(R.id.txtTelefono);
        txtEdad = (TextView)findViewById(R.id.txtEdad);
        txtCorreo = (TextView)findViewById(R.id.txtCorreo);

        idpadre= Preferences.obtenerPreferenceStringId(this,Preferences.PREFERENCE_USUARIO_LOGIN_ID);

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("https://appgpsmovil.000webhostapp.com/webservices/datosUsuario.php?usuario="+idpadre+"", datos,
                ActualizarDatos.this,  ActualizarDatos.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.i("processFinish", result);
        JSONObject jsonObj= new JSONObject(result);
        txtCI.setText(jsonObj.getString("cedula").trim());
        txtNombre.setText(jsonObj.getString("nombre").trim());
        txtApellido.setText(jsonObj.getString("apellido").trim());
        txtDireccion.setText(jsonObj.getString("direccion").trim());
        txtTelefono.setText("0"+jsonObj.getString("telefono").trim());
        txtEdad.setText(jsonObj.getString("edad").trim());
        txtCorreo.setText(jsonObj.getString("correo").trim());
    }

    @Override
    public void onClick(View v) {
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("nombre",txtNombre.getText().toString().trim());
        datos.put("apellido",txtApellido.getText().toString().trim());
        datos.put("edad",txtEdad.getText().toString().trim());
        datos.put("direccion",txtDireccion.getText().toString().trim());
        datos.put("correo",txtCorreo.getText().toString().trim());
        datos.put("idpadre",idpadre);

        WebService ws= new WebService("https://appgpsmovil.000webhostapp.com/webservices/actualizarUsuario.php", datos,
                ActualizarDatos.this, ActualizarDatos.this);
        ws.execute("");

        Toast.makeText(getApplicationContext(),"Datos actualizados", Toast.LENGTH_LONG).show();

    }

}
