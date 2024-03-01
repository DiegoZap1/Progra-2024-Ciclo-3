package com.ugb.controlesbasicos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private static final String dbname = "gerson";
    private static final int v =1;
    private static final String SQLdb = "CREATE TABLE herson(id text, rev text, idProducto text, " +
            "nombre text, marca text, descripcion text, costo text, precio text, stock text, foto text)";
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLdb);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //cambiar estructura de la BD
    }
    public String administrar_productos(String accion, String[] datos){
        try {
            SQLiteDatabase db = getWritableDatabase();
            String sql = "";
            if( accion.equals("nuevo") ){
                sql = "INSERT INTO herson(id,rev,idProducto,nombre,marca,descripcion,costo,precio,stock,foto) VALUES('"+ datos[0] +"','"+ datos[1] +"','"+ datos[2] +"', '"+
                        datos[3] +"', '"+ datos[4] +"','"+ datos[5] +"','"+ datos[6] +"', '"+ datos[7] +"', '"+ datos[8] +"', '"+ datos[9] +"' )";
            } else if (accion.equals("modificar")) {
                sql = "UPDATE herson SET id='"+ datos[0] +"',rev='"+ datos[1] +"',nombre='"+ datos[3] +"', marca='"+ datos[4] +"', descripcion='"+ datos[5] +"', costo='"+ datos[6] +"', precio=" +
                        "'"+ datos[7] +"', stock='"+ datos[8] +"', foto='"+ datos[9] +"' WHERE idProducto='"+ datos[2] +"'";
            } else if (accion.equals("eliminar")) {
                sql = "DELETE FROM herson WHERE idProducto='"+ datos[2] +"'";
            }
            db.execSQL(sql);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public Cursor obtener_productos(){
        Cursor cursor;

        SQLiteDatabase db = getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM herson ORDER BY nombre", null);
        return cursor;
    }
}