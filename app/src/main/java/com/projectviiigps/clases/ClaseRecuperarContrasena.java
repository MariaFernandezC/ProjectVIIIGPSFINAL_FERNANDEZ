package com.projectviiigps.clases;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ClaseRecuperarContrasena extends StringRequest {

    private static final  String ruta = "http://appgpsmovil.000webhostapp.com/webservices/RecuperarContrasena.php";
    private Map<String, String> parametros;

    public ClaseRecuperarContrasena(String correo, Response.Listener<String> listener){
        super(Request.Method.POST, ruta,listener,null);
        parametros =new HashMap<>();
        parametros.put("correo",correo);

    }

    @Override
    protected Map<String, String> getParams() {

        return parametros;
    }



}
