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

import java.util.ArrayList;
import java.util.Locale;

import kotlin.contracts.Returns;

public class lista_productos extends AppCompatActivity {
    Bundle parametros = new Bundle();
    DB db;
    ListView lts;
    Cursor cProductos;
    final ArrayList<productos> alProductos = new ArrayList<productos>();
    final ArrayList<productos> alProductosCopy = new ArrayList<productos>();
    productos datosProductos;
    FloatingActionButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_productos);
        btn = findViewById(R.id.fabAgregarProductos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion","nuevo");
                abrirActividad(parametros);
            }
        });
        obtener_productos();
        buscar_productos();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        cProductos.moveToPosition(info.position);
        menu.setHeaderTitle("Que deseas hacer con "+ cProductos.getString(1));
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
                    String[] productos = {
                            cProductos.getString(0),//IDPRODUCTO
                            cProductos.getString(1),//NOMBREPRODUCTO
                            cProductos.getString(2),//MARCA
                            cProductos.getString(3),//DESCRIPCION
                            cProductos.getString(4),//PRECIO
                            cProductos.getString(5),//FOTO
                    };
                    parametros.putString("accion", "modificar");
                    parametros.putStringArray("productos", productos);
                    abrirActividad(parametros);
                    break;
                case R.id.mnxEliminar:
                    eliminarProducto();
                    break;
            }
            return true;
        }catch (Exception e){
            mostrarMsg("Error al seleccionar el item: "+ e.getMessage());
            return super.onContextItemSelected(item);
        }
    }
    private void eliminarProducto(){
        try {
            AlertDialog.Builder confirmar = new AlertDialog.Builder(lista_productos.this);
            confirmar.setTitle("Esta seguro de eliminar el producto: ");
            confirmar.setMessage(cProductos.getString(1));
            confirmar.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String respuesta = db.administrar_productos("eliminar", new String[]{cProductos.getString(0)});
                    if( respuesta.equals("ok") ){
                        mostrarMsg("Producto eliminado con exito");
                        obtener_productos();
                    }else{
                        mostrarMsg("Error al eliminar el producto: "+ respuesta);
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
    private void buscar_productos(){
        TextView tempVal = findViewById(R.id.txtBuscar);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    alProductos.clear();
                    String valor = tempVal.getText().toString().trim().toLowerCase();
                    if( valor.length()<=0 ){
                        alProductos.addAll(alProductos);
                    }else{
                        for (productos producto : alProductosCopy){
                            String nombre = producto.getNombreProducto();
                            String marca = producto.getMarca();
                            String descripcion = producto.getDescripcion();
                            String precio = producto.getPrecio();
                            if( nombre.trim().toLowerCase().contains(valor) ||
                                    marca.trim().toLowerCase().contains(valor) ||
                                    descripcion.trim().contains(valor) ||
                                    precio.trim().toLowerCase().contains(valor)){
                                alProductos.add(producto);
                            }
                        }
                        adaptadorImagenes adImagenes=new adaptadorImagenes(getApplicationContext(), alProductos);
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
    private void obtener_productos(){
        try{
            alProductos.clear();
            alProductosCopy.clear();

            db = new DB(getApplicationContext(),"", null, 1);
            cProductos = db.obtener_productos();
            if( cProductos.moveToFirst() ){
                lts = findViewById(R.id.ltsProductos);
                do{
                    datosProductos = new productos(
                            cProductos.getString(0),//idProducto
                            cProductos.getString(1),//nombreProducto
                            cProductos.getString(2),//marca
                            cProductos.getString(3),//descripcion
                            cProductos.getString(4),//precio
                            cProductos.getString(5)//foto
                    );
                    alProductos.add(datosProductos);
                }while (cProductos.moveToNext());
                alProductosCopy.addAll(alProductos);

                adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alProductos);
                lts.setAdapter(adImagenes);

                registerForContextMenu(lts);
            }else {
                parametros.putString("accion","nuevo");
                abrirActividad(parametros);
                mostrarMsg("No hay datos de Productos.");
            }
        }catch (Exception e){
            mostrarMsg("Error al obtener los productos: "+ e.getMessage());
        }
    }
    private void mostrarMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}