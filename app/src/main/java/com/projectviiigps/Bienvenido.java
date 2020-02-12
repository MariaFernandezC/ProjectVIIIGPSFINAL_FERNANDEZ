package com.projectviiigps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.projectviiigps.Adapter.ItemClickListener;
import com.projectviiigps.Adapter.RecAdapterHijos;
import com.projectviiigps.Adapter.RecAdapterPadres;
import com.projectviiigps.Data.dataHijos;
import com.projectviiigps.Data.dataPadre;
import com.projectviiigps.Others.Preferences;
import com.projectviiigps.WebService.Asynchtask;
import com.projectviiigps.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Bienvenido extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Asynchtask, ItemClickListener {
    ConstraintLayout expandableView;
    private RecAdapterPadres recAdapterPadres;
    private RecAdapterHijos recAdapterHijos;
    private LinearLayoutManager linearLayoutManager;
    private List<dataPadre> dataPadres = new ArrayList<>();
    private List<dataHijos> dataHijos = new ArrayList<>();

    CardView cardView;
    Intent intent =getIntent();
    int idpadre;
    MenuItem logoutbtn;
    private RecyclerView recyclerView;
    private RecyclerView recyclerhijos;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open
                , R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navView = findViewById(R.id.navview);
        Menu menu = navView.getMenu();

        MenuItem nav_camara = menu.findItem(R.id.menu_seccion_2);
        nav_camara.setVisible(true);
        navView.setNavigationItemSelectedListener(this);

        logoutbtn = menu.findItem(R.id.btnlogout);

        View headView = navView.getHeaderView(0);
        ImageView imgProfile = headView.findViewById(R.id.profile_image);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Bienvenido.this,Perfil.class);
                startActivity(i);
            }
        });



        logoutbtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Bienvenido.this);
                alertDialogBuilder.setTitle("Importante");
                alertDialogBuilder
                        .setMessage("¿Deseas cerrar sesion?")
                        .setCancelable(false)
                        .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Preferences.savePreferenceStringId(Bienvenido.this,"",
                                        Preferences.PREFERENCE_USUARIO_LOGIN_ID);
                                Preferences.savePreferenceStringNombre(Bienvenido.this,"",
                                        Preferences.PREFERENCE_USUARIO_LOGIN_NOMBRE);
                                Preferences.savePreferenceStringContrasenia(Bienvenido.this,"",
                                        Preferences.PREFERENCE_USUARIO_LOGIN_NOMBRE);
                                Intent i = new Intent(Bienvenido.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        }).create().show();
                return false;
            }
        });
        recyclerView = findViewById(R.id.recview);
        recyclerhijos = findViewById(R.id.recview2);
        Intent i = this.getIntent();
        String id = Preferences.obtenerPreferenceStringId(this, Preferences.PREFERENCE_USUARIO_LOGIN_ID);
        String usuario = Preferences.obtenerPreferenceStringUsuario(this, Preferences.PREFERENCE_USUARIO_LOGIN_USUARIO);
        //llenar
        String clave = Preferences.obtenerPreferenceStringContrasenia(this, Preferences.PREFERENCE_USUARIO_LOGIN_CONTRASENIA);
        int telefono = Integer.valueOf(Preferences.obtenerPreferenceStringTelefono(this, Preferences.PREFERENCE_USUARIO_LOGIN_TELEFONO));
        String correo = Preferences.obtenerPreferenceStringCorreo(this, Preferences.PREFERENCE_USUARIO_LOGIN_CORREO);
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("user",usuario.trim());
        datos.put("pass",clave.trim());
        datos.put("id_usuario",id.trim());
        WebService ws = new WebService("https://appgpsmovil.000webhostapp.com/webservices/hijos_padre.php",datos,this,this);
        //WebService ws = new WebService("https://appgpsmovil.000webhostapp.com/webservices/hijos_padre.php?id_usuario="+id+"&&user='"+usuario+"'&&pass="+clave,datos,this,this);
        ws.execute("");

    }


   /*/protected void onResume() {
        super.onResume();
        final TextView cabeceranombreU= findViewById(R.id.txtusuario1);
        String nombrecabecera=Preferences.obtenerPreferenceStringNombre(this, Preferences.PREFERENCE_USUARIO_LOGIN_NOMBRE);
        cabeceranombreU.setText(cabeceranombreU.getText()+" "+nombrecabecera+"");
        cargada = true;

    }/*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_seccion_1) {
            Intent i = new Intent(Bienvenido.this,Perfil.class);
            startActivity(i);

        } else if (id == R.id.menu_seccion_2) {

            Intent i = new Intent(Bienvenido.this,RegistroHijo.class);
            int idp = i.getIntExtra("idpadre",-1);//esto
            i.putExtra("idpadre",idpadre);
            startActivity(i);

        } else if (id == R.id.menu_seccion_3) {
            Intent i = new Intent(Bienvenido.this,localizacion.class);
            startActivity(i);
        } else if (id == R.id.menu_seccion_4) {
            Intent i = new Intent(Bienvenido.this,Reportes.class);
            startActivity(i);
        }
        else if (id == R.id.btnactualizar) {
            Intent i = new Intent(Bienvenido.this,ActualizarGeneral.class);
            startActivity(i);
        }
        else if (id == R.id.btnayuda) {
            Intent i = new Intent(Bienvenido.this,VisorPdf.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void lanzarcodigo ()
    {
        final TextView cabeceranombreU= findViewById(R.id.txtusuario1);
        String nombrecabecera=Preferences.obtenerPreferenceStringNombre(this, Preferences.PREFERENCE_USUARIO_LOGIN_NOMBRE);
        cabeceranombreU.setText(cabeceranombreU.getText()+" "+nombrecabecera+"");
    }


    @Override
    public void processFinish(String result) throws JSONException {
        //este metodo se ejecuta al final cuando se llama a un web service, el result es el json

        JSONObject jsonIssues = new JSONObject(result);
        JSONArray jsonArrayIssue = jsonIssues.getJSONArray("Padre");
        JSONArray jsonArrayIssuehijo = jsonIssues.getJSONArray("Hijos");
        for(int i=0; i< jsonArrayIssue.length();i++)
        {
            JSONObject objIssue = jsonArrayIssue.getJSONObject(i);
            dataPadre dt;

            dt= new dataPadre(objIssue.getString("nombre")+ " "+ objIssue.getString("apellido"),objIssue.getString("telefono"),objIssue.getString("direccion"),objIssue.getString("correo"));
            dataPadres.add(dt);
        }

        recAdapterPadres= new RecAdapterPadres(dataPadres,this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recAdapterPadres);
        recyclerView.setHasFixedSize(true);

        for(int i=0; i< jsonArrayIssuehijo.length();i++)
        {
            JSONObject objIssue = jsonArrayIssuehijo.getJSONObject(i);
            dataHijos dt;

            dt= new dataHijos(objIssue.getString("nombreh")+ " "+ objIssue.getString("apellidoh"),objIssue.getString("edadh"),objIssue.getString("direccionh"),objIssue.getString("tiposangre"),objIssue.getString("enfermedad"),objIssue.getString("alergia"));
            dataHijos.add(dt);
        }
        recAdapterHijos= new RecAdapterHijos(dataHijos,this);
        LinearLayoutManager secondManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerhijos.setLayoutManager(secondManager);
        recyclerhijos.setAdapter(recAdapterHijos);
        recyclerhijos.setHasFixedSize(true);
        recAdapterHijos.setClickListener(this);
    }
    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(Bienvenido.this, localizacion.class);
        intent.putExtra("nombre",dataHijos.get(position).getNombre());
        intent.putExtra("key","true");
        startActivity(intent);
    }
}