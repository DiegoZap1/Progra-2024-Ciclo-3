package com.ugb.controlesbasicos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class adaptadorImagenes extends BaseAdapter {
    Context context;
    ArrayList<vehiculos> datosVehiculosArrayList;
    vehiculos datosVehiculos;
    LayoutInflater layoutInflater;
    public adaptadorImagenes(Context context, ArrayList<vehiculos> datosVehiculosArrayList) {
        this.context = context;
        this.datosVehiculosArrayList = datosVehiculosArrayList;
    }
    @Override
    public int getCount() {
        return datosVehiculosArrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return datosVehiculosArrayList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i; //Long.parseLong(datosAmigosArrayList.get(i).getIdAmigo());
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.listview_imagenes, viewGroup, false);
        try{
            datosVehiculos = datosVehiculosArrayList.get(i);

            TextView tempVal = itemView.findViewById(R.id.lblmarca);
            tempVal.setText(datosVehiculos.getMarca());

            tempVal = itemView.findViewById(R.id.lblmotor);
            tempVal.setText(datosVehiculos.getMotor());

            tempVal = itemView.findViewById(R.id.lblchasis);
            tempVal.setText(datosVehiculos.getChasis());

            Bitmap imageBitmap = BitmapFactory.decodeFile(datosVehiculos.getUrlFotoVehiculo());
            ImageView img = itemView.findViewById(R.id.imgFotoVehiculo);
            img.setImageBitmap(imageBitmap);
        }catch (Exception e){
            Toast.makeText(context, "Error al mostrar los datos: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemView;
    }
}