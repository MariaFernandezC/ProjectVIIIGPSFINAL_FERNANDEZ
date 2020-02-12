package com.projectviiigps.WebService2;


import org.json.JSONException;

public interface Asynchtask2 {
    /**
     * ESta funcion retorna los datos devueltos por el ws
     * @param result
     */
    void processFinish(String result) throws JSONException;

    void processFinish2(String result2) throws JSONException;
}
