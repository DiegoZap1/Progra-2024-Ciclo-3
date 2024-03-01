package com.ugb.controlesbasicos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btn;
    FloatingActionButton fab;
    TextView tempVal;
    String accion = "nuevo";
    String id="", rev="", idVehiculo="";
    String urlCompletaFoto;
    Intent tomarFotoIntent;
    ImageView img;
    utilidades utls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utls = new utilidades();
        fab = findViewById(R.id.fabListarVehiculos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActividad();
            }
        });
        btn = findViewById(R.id.btnGuardarVehiculos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tempVal = findViewById(R.id.txtmarca);
                    String marca = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtmotor);
                    String motor = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtchasis);
                    String chasis = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtvin);
                    String vin = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtcombustion);
                    String combustion = tempVal.getText().toString();

                    //guardar datos en el servidor
                    JSONObject datosVehiculos = new JSONObject();
                    if(accion.equals("modificar")){
                        datosVehiculos.put("_id", id);
                        datosVehiculos.put("_rev", rev);
                    }
                    datosVehiculos.put("idVehiculo", idVehiculo);
                    datosVehiculos.put("marca", marca);
                    datosVehiculos.put("motor", motor);
                    datosVehiculos.put("chasis", chasis);
                    datosVehiculos.put("vin", vin);
                    datosVehiculos.put("combustion", combustion);
                    datosVehiculos.put("urlCompletaFoto", urlCompletaFoto);

                    String respuesta = "";
                    enviarDatosServidor objGuardarDatosServidor = new enviarDatosServidor(getApplicationContext());
                    respuesta = objGuardarDatosServidor.execute(datosVehiculos.toString()).get();

                    JSONObject respuestaJSONObject = new JSONObject(respuesta);
                    if( respuestaJSONObject.getBoolean("ok") ){
                        id = respuestaJSONObject.getString("id");
                        rev = respuestaJSONObject.getString("rev");
                    }else{
                        mostrarMsg("Error al guardar datos en el servidor");
                    }
                    DB db = new DB(getApplicationContext(), "",null, 1);
                    String[] datos = new String[]{id, rev, idVehiculo,marca,motor,chasis,vin,combustion, urlCompletaFoto};
                    respuesta = db.administrar_vehiculos(accion, datos);
                    if(respuesta.equals("ok")){
                        Toast.makeText(getApplicationContext(), "Vehiculo guardado con exito", Toast.LENGTH_LONG).show();
                        abrirActividad();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error al intentar guardar el vehiculo: "+ respuesta, Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        img = findViewById(R.id.btnImgVehiculo);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFotoVehiculo();
            }
        });
        mostrarDatosVehiculos();
    }
    private void tomarFotoVehiculo(){
        tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File fotoVehiculo = null;
        try{
            fotoVehiculo = crearImagenVehiculo();
            if( fotoVehiculo!=null ){
                Uri uriFotoVehiculo = FileProvider.getUriForFile(MainActivity.this,
                        "com.ugb.controlesbasicos.fileprovider", fotoVehiculo);
                tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoVehiculo);
                startActivityForResult(tomarFotoIntent, 1);
            }else{
                mostrarMsg("No se pudo creaar la foto");
            }
        }catch (Exception e){
            mostrarMsg("Error al abrir la camara: "+ e.getMessage());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode==1 && resultCode==RESULT_OK){
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaFoto);
                img.setImageBitmap(imageBitmap);
            }else{
                mostrarMsg("El usuario cancelo la toma de la foto");
            }
        }catch (Exception e){
            mostrarMsg("Error al obtener la foto de la camara");
        }
    }
    private File crearImagenVehiculo() throws Exception{
        String fechaHoraMs = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()),
                fileName = "imagen_"+ fechaHoraMs +"_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if( dirAlmacenamiento.exists()==false ){
            dirAlmacenamiento.mkdirs();
        }
        File imagen = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
        urlCompletaFoto = imagen.getAbsolutePath();
        return imagen;
    }
    private void mostrarDatosVehiculos(){
        try{
            Bundle parametros = getIntent().getExtras();//Recibir los parametros...
            accion = parametros.getString("accion");

            if(accion.equals("modificar")){
                JSONObject jsonObject = new JSONObject(parametros.getString("vehiculos")).getJSONObject("value");
                id = jsonObject.getString("_id");
                rev = jsonObject.getString("_rev");
                idVehiculo = jsonObject.getString("idVehiculo");

                tempVal = findViewById(R.id.txtmarca);
                tempVal.setText(jsonObject.getString("marca"));

                tempVal = findViewById(R.id.txtmotor);
                tempVal.setText(jsonObject.getString("motor"));

                tempVal = findViewById(R.id.txtchasis);
                tempVal.setText(jsonObject.getString("chasis"));

                tempVal = findViewById(R.id.txtvin);
                tempVal.setText(jsonObject.getString("vin"));

                tempVal = findViewById(R.id.txtcombustion);
                tempVal.setText(jsonObject.getString("combustion"));

                urlCompletaFoto = jsonObject.getString("urlCompletaFoto");
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaFoto);
                img.setImageBitmap(imageBitmap);
            }else{//nuevo registro
                idVehiculo = utls.generarIdUnico();
            }
        }catch (Exception e){
            mostrarMsg("Error al mostrar datos: "+ e.getMessage());
        }
    }
    private void mostrarMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void abrirActividad(){
        Intent abrirActividad = new Intent(getApplicationContext(), lista_vehiculos.class);
        startActivity(abrirActividad);
    }
}