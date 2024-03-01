package com.ugb.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import kotlin.contracts.Returns;

public class lista_vehiculos extends AppCompatActivity {
    Bundle parametros = new Bundle();
    DB db;
    ListView lts;
    Cursor cVehiculos;
    final ArrayList<vehiculos> alVehiculos = new ArrayList<vehiculos>();
    final ArrayList<vehiculos> alVehiculosCopy = new ArrayList<vehiculos>();
    vehiculos datosVehiculos;
    FloatingActionButton btn;
    JSONArray datosJSON; //para los datos que vienen del servidor.
    JSONObject jsonObject;
    obtenerDatosServidor datosServidor;
    detectarInternet di;
    int posicion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_vehiculos);
        db = new DB(getApplicationContext(),"", null, 1);
        btn = findViewById(R.id.fabAgregarVehiculos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion","nuevo");
                abrirActividad(parametros);
            }
        });
        di = new detectarInternet(getApplicationContext());
        if( di.hayConexionInternet() ){
            obtenerDatosVehiculosServidor();
        }else{
            mostrarMsg("No hay conexion, datos en local");
            obtenerVehiculos();//offline
        }
        buscarVehiculos();
    }
    private void obtenerDatosVehiculosServidor(){
        try{
            datosServidor = new obtenerDatosServidor();
            String data = datosServidor.execute().get();

            jsonObject =new JSONObject(data);
            datosJSON = jsonObject.getJSONArray("rows");
            mostrarDatosVehiculos();
        }catch (Exception e){
            mostrarMsg("Error al obtener datos de los vehiculos del servidor: "+ e.getMessage());
        }
    }
    private void mostrarDatosVehiculos(){
        try{
            if( datosJSON.length()>0 ){
                lts = findViewById(R.id.ltsVehiculos);
                alVehiculos.clear();
                alVehiculosCopy.clear();

                JSONObject misDatosJSONObject;
                for (int i=0; i<datosJSON.length(); i++){
                    misDatosJSONObject = datosJSON.getJSONObject(i).getJSONObject("value");
                    datosVehiculos = new vehiculos(
                            misDatosJSONObject.getString("_id"),
                            misDatosJSONObject.getString("_rev"),
                            misDatosJSONObject.getString("idVehiculo"),
                            misDatosJSONObject.getString("marca"),
                            misDatosJSONObject.getString("motor"),
                            misDatosJSONObject.getString("chasis"),
                            misDatosJSONObject.getString("vin"),
                            misDatosJSONObject.getString("combustion"),
                            misDatosJSONObject.getString("urlCompletaFoto")
                    );
                    alVehiculos.add(datosVehiculos);
                }
                alVehiculosCopy.addAll(alVehiculos);

                adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alVehiculos);
                lts.setAdapter(adImagenes);

                registerForContextMenu(lts);
            }else{
                mostrarMsg("No hay datos que mostrar");
            }
        }catch (Exception e){
            mostrarMsg("Error al mostrar datos: "+ e.getMessage());
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicion = info.position;
            menu.setHeaderTitle("Que deseas hacer con " + datosJSON.getJSONObject(posicion).getJSONObject("value").getString("marca"));
        }catch (Exception e){
            mostrarMsg("Error al mostrar el menu: "+ e.getMessage());
        }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try{
            switch (item.getItemId()){
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo");
                    abrirActividad(parametros);
                    break;
                case R.id.mnxModificar:
                    parametros.putString("accion", "modificar");
                    parametros.putString("vehiculos", datosJSON.getJSONObject(posicion).toString());
                    abrirActividad(parametros);
                    break;
                case R.id.mnxEliminar:
                    eliminarVehiculo();
                    break;
            }
            return true;
        }catch (Exception e){
            mostrarMsg("Error al seleccionar el item: "+ e.getMessage());
            return super.onContextItemSelected(item);
        }
    }
    private void eliminarVehiculo(){
        try {
            AlertDialog.Builder confirmar = new AlertDialog.Builder(lista_vehiculos.this);
            confirmar.setTitle("Esta seguro de eliminar el vehiculo: ");
            confirmar.setMessage(datosJSON.getJSONObject(posicion).getJSONObject("value").getString("marca"));
            confirmar.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        String respuesta = db.administrar_vehiculos("eliminar", new String[]{"", "", datosJSON.getJSONObject(posicion).getJSONObject("value").getString("idVehiculo")});
                        if (respuesta.equals("ok")) {
                            mostrarMsg("Vehiculo eliminado con exito");
                            obtenerVehiculos();
                        } else {
                            mostrarMsg("Error al eliminar el vehiculos: " + respuesta);
                        }
                    }catch (Exception e){
                        mostrarMsg("Error al eliminar datos: "+ e.getMessage());
                    }
                }
            });
            confirmar.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            confirmar.create().show();
        }catch (Exception e){
            mostrarMsg("Error al eliminar: "+ e.getMessage());
        }
    }
    private void buscarVehiculos(){
        TextView tempVal = findViewById(R.id.txtBuscarVehiculos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    alVehiculos.clear();
                    String valor = tempVal.getText().toString().trim().toLowerCase();
                    if( valor.length()<=0 ){
                        alVehiculos.addAll(alVehiculosCopy);
                    }else{
                        for (vehiculos vehiculo : alVehiculosCopy){
                            String marca = vehiculo.getMarca();
                            String motor = vehiculo.getMotor();
                            String chasis = vehiculo.getChasis();
                            String vin = vehiculo.getVin();
                            if( marca.trim().toLowerCase().contains(valor) ||
                                    motor.trim().toLowerCase().contains(valor) ||
                                    chasis.trim().contains(valor) ||
                                    vin.trim().toLowerCase().contains(valor)){
                                alVehiculos.add(vehiculo);
                            }
                        }
                        adaptadorImagenes adImagenes=new adaptadorImagenes(getApplicationContext(), alVehiculos);
                        lts.setAdapter(adImagenes);
                    }
                }catch (Exception e){
                    mostrarMsg("Error al buscar: "+ e.getMessage());
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void abrirActividad(Bundle parametros){
        Intent abrirActividad = new Intent(getApplicationContext(), MainActivity.class);
        abrirActividad.putExtras(parametros);
        startActivity(abrirActividad);
    }
    private void obtenerVehiculos(){ //offline
        try{
            cVehiculos = db.obtener_vehiculos();
            if( cVehiculos.moveToFirst() ){
                datosJSON = new JSONArray();
                do{
                    jsonObject = new JSONObject();
                    JSONObject jsonObjectValue = new JSONObject();

                    jsonObject.put("_id", cVehiculos.getString(0));
                    jsonObject.put("_rev", cVehiculos.getString(1));
                    jsonObject.put("idVehiculo", cVehiculos.getString(2));
                    jsonObject.put("marca", cVehiculos.getString(3));
                    jsonObject.put("motor", cVehiculos.getString(4));
                    jsonObject.put("chasis", cVehiculos.getString(5));
                    jsonObject.put("vin", cVehiculos.getString(6));
                    jsonObject.put("combustion", cVehiculos.getString(7));
                    jsonObject.put("urlCompletaFoto", cVehiculos.getString(8));
                    jsonObjectValue.put("value", jsonObject);

                    datosJSON.put(jsonObjectValue);
                }while (cVehiculos.moveToNext());
                mostrarMsg("Punto");
                mostrarDatosVehiculos();
            }else {
                parametros.putString("accion", "nuevo");
                abrirActividad(parametros);
                mostrarMsg("No hay datos de vehiculos.");
            }
        }catch (Exception e){
            mostrarMsg("Error al obtener los vehiculos : "+ e.getMessage());
        }
    }
    private void mostrarMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}