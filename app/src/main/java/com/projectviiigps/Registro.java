package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.projectviiigps.clases.RegistroRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {

     EditText nombret, apellidost,cedulat,edadt,direcciont,telefonot,correoelectronicot,usuariot,clavet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        final TextView regresoIncioSesion = findViewById(R.id.btn_ir_inicioSesion);
         nombret              = findViewById(R.id.RegistroNombres);
         apellidost              = findViewById(R.id.RegistroApellidos);
         cedulat              = findViewById(R.id.RegistroCedula);
         edadt                = findViewById(R.id.RegistroEdad);
         direcciont           = findViewById(R.id.RegistroDireccion);
         telefonot            = findViewById(R.id.RegistroTelefono);
         correoelectronicot   = findViewById(R.id.RegistroCorreo);
         usuariot             = findViewById(R.id.RegistroUsuario);
         clavet               = findViewById(R.id.RegistroContrasena);
        Button btnRegistro                  = findViewById(R.id.btnRegistro);

        regresoIncioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registro.this, MainActivity.class);
                Registro.this.startActivity(i);
                Registro.this.finish();
            }
        });


        btnRegistro.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {
                String nombre       = nombret.getText().toString();
                String apellido       = apellidost.getText().toString();
                int cedula          = Integer.parseInt( cedulat.getText().toString());
                int    edad         =  Integer.parseInt( edadt.getText().toString());
                String direccion    = direcciont.getText().toString();;
                int telefono        = Integer.parseInt(telefonot.getText().toString());
                String correo       = correoelectronicot.getText().toString();
                String usuario      = usuariot.getText().toString();
                String clave        = clavet.getText().toString();



                if (nombret.getText().toString().isEmpty()){
                    AlertDialog.Builder alerta =new AlertDialog.Builder( Registro.this);
                    alerta.setMessage("Se encuentra vacio el campo Nombre")
                            .setNegativeButton( "Reintertar", null)
                            .create()
                            .show();
                }
                else {
                    if (apellidost.getText().toString().isEmpty()){
                        AlertDialog.Builder alerta =new AlertDialog.Builder( Registro.this);
                        alerta.setMessage("Se encuentra vacio el campo Apellido")
                                .setNegativeButton( "Reintertar", null)
                                .create()
                                .show();
                    }
                    else{
                        if (cedulat.getText().toString().isEmpty()){
                            AlertDialog.Builder alerta =new AlertDialog.Builder( Registro.this);
                            alerta.setMessage("Se encuentra vacio el campo Cedula")
                                    .setNegativeButton( "Reintertar", null)
                                    .create()
                                    .show();
                        }else {
                            if (edadt.getText().toString().isEmpty()){
                                AlertDialog.Builder alerta =new AlertDialog.Builder( Registro.this);
                                alerta.setMessage("Se encuentra vacio el campo Edad")
                                        .setNegativeButton( "Reintertar", null)
                                        .create()
                                        .show();
                            }else{
                                if (direcciont.getText().toString().isEmpty()){
                                    AlertDialog.Builder alerta =new AlertDialog.Builder( Registro.this);
                                    alerta.setMessage("Se encuentra vacio el campo Direccion")
                                            .setNegativeButton( "Reintertar", null)
                                            .create()
                                            .show();
                                }else{
                                    if (telefonot.getText().toString().isEmpty()){
                                        AlertDialog.Builder alerta =new AlertDialog.Builder( Registro.this);
                                        alerta.setMessage("Se encuentra vacio el campo Telefono")
                                                .setNegativeButton( "Reintertar", null)
                                                .create()
                                                .show();
                                    }else{
                                        if (correoelectronicot.getText().toString().isEmpty()){
                                            AlertDialog.Builder alerta =new AlertDialog.Builder( Registro.this);
                                            alerta.setMessage("Se encuentra vacio el campo Correo electronico")
                                                    .setNegativeButton( "Reintertar", null)
                                                    .create()
                                                    .show();
                                        }else{
                                            if (usuariot.getText().toString().isEmpty()){
                                                AlertDialog.Builder alerta =new AlertDialog.Builder( Registro.this);
                                                alerta.setMessage("Se encuentra vacio el campo Usuario")
                                                        .setNegativeButton( "Reintertar", null)
                                                        .create()
                                                        .show();
                                            }else {
                                                if (clavet.getText().toString().isEmpty()){
                                                    AlertDialog.Builder alerta =new AlertDialog.Builder( Registro.this);
                                                    alerta.setMessage("Se encuentra vacio el campo Clave")
                                                            .setNegativeButton( "Reintertar", null)
                                                            .create()
                                                            .show();
                                                }
                                             }
                                          }
                                        }
                                      }
                                    }
                                 }
                               }
                             }
                if (!nombret.getText().toString().isEmpty()&&!apellidost.getText().toString().isEmpty()&&!cedulat.getText().toString().isEmpty()&&
                        !edadt.getText().toString().isEmpty()&&!direcciont.getText().toString().isEmpty()&&!telefonot.getText().toString().isEmpty()&&
                        !correoelectronicot.getText().toString().isEmpty()&&!usuariot.getText().toString().isEmpty()&&!clavet.getText().toString().isEmpty())
                {
                Response.Listener <String> respuesta= new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonRespuesta =new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");


                            if ( ok){

                                final ProgressDialog progressDialog = new ProgressDialog(Registro.this);
                                progressDialog.setIcon(R.mipmap.ic_launcher);
                                progressDialog.setMessage("Cargando...");
                                progressDialog.show();
                                Intent i = new Intent(Registro.this, MainActivity.class);
                                Registro.this.startActivity(i);
                                Registro.this.finish();
                                progressDialog.dismiss();

                            }else {
                                AlertDialog.Builder alerta =new AlertDialog.Builder( Registro.this);
                                alerta.setMessage("Fallo en el Registro")
                                        .setNegativeButton( "Reintertar", null)
                                        .create()
                                        .show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };
                RegistroRequest r = new RegistroRequest(nombre,apellido,cedula,edad,direccion,telefono,correo,usuario,clave, respuesta);

                RequestQueue cola = Volley.newRequestQueue( Registro.this);
                cola.add(r);
            }
            }
        });

    }
    public void Enviar(View v){

        if (nombret.getText().toString().isEmpty()){
            Toast.makeText(this,"Campo Nombre vacio",Toast.LENGTH_LONG).show();
        }
    }


}










