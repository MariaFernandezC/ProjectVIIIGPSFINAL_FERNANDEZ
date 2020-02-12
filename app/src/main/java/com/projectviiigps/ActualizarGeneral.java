package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActualizarGeneral extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_general);


    }
    public void onClickPadre(View v) {
        Intent i = new Intent(ActualizarGeneral.this, ActualizarDatos.class);
        startActivity(i);

    }

    public void onClickHijo(View v) {
        Intent i = new Intent(ActualizarGeneral.this, ActualizarDatoHijo.class);
        startActivity(i);

    }
}
