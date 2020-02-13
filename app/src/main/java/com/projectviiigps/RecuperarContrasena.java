package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.projectviiigps.clases.ClaseRecuperarContrasena;
import com.projectviiigps.clases.MailJob;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class RecuperarContrasena extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);

        Button btnRecuperarContrasena = findViewById(R.id.btnRecuperarContrasena);
        final EditText correo = findViewById(R.id.ingreseCorreo);


        btnRecuperarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {


                final String repcorreo = correo.getText().toString();

                if(validarEmail(repcorreo)){
                    Response.Listener<String> respuesta = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                if (response.equals(""))
                                    {
                                JSONArray jsonRespuesta = new JSONArray(response);


                                if ( true ){

                                    for (int i=0; i<jsonRespuesta.length();i++){
                                        JSONObject jsonObject = jsonRespuesta.getJSONObject(i);
                                        String correo = String.valueOf(jsonObject.getString("correo"));
                                        String clave = String.valueOf(jsonObject.getString("clave"));

                                        new MailJob("parapruebas650@gmail.com", "Pruebas650").execute(
                                                new MailJob.Mail("parapruebas650@gmail.com", correo, "Restablecer  contraseña", "Su contraseña:" +clave)
                                        );
                                        AlertDialog.Builder alerta =new AlertDialog.Builder( RecuperarContrasena.this);
                                        alerta.setMessage("enviado a su correo, reviselo!!")
                                                .setNegativeButton( "ok", null)
                                                .create()
                                                .show();
                                    }
                                }
                                else {
                                    AlertDialog.Builder alerta =new AlertDialog.Builder( RecuperarContrasena.this);
                                    alerta.setMessage("Fallo en el login")
                                            .setNegativeButton( "Reintentar", null)
                                            .create()
                                            .show();
                                }
                                    }
                                else {
                                    AlertDialog.Builder alerta =new AlertDialog.Builder( RecuperarContrasena.this);
                                    alerta.setMessage("El correo ingresado no existe")
                                            .setNegativeButton( "Reintentar", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e){
                                e.getMessage();
                                AlertDialog.Builder alerta =new AlertDialog.Builder( RecuperarContrasena.this);
                                alerta.setMessage(e.getMessage())
                                        .setNegativeButton( "Reintentar", null)
                                        .create()
                                        .show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }



                    };

                    ClaseRecuperarContrasena r = new ClaseRecuperarContrasena(repcorreo, respuesta);
                    RequestQueue cola = Volley.newRequestQueue( RecuperarContrasena.this);
                    cola.add(r);
                }
                else{
                    Toast.makeText(RecuperarContrasena.this, "Email no valido", Toast.LENGTH_SHORT).show();
                }
            }


        });



    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}


