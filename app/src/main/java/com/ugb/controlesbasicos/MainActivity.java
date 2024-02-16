package com.ugb.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TabHost tbh;
    TextView tempVal;
    Spinner spn;
    Button btn;
    conversores objConversor = new conversores();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhConversores);
        tbh.setup();
        tbh.addTab(tbh.newTabSpec("LON").setIndicator("LONGITUD", null).setContent(R.id.tabLogitud));
        tbh.addTab(tbh.newTabSpec("MON").setIndicator("MONEDAS", null).setContent(R.id.tabMonedas));
        tbh.addTab(tbh.newTabSpec("ALM").setIndicator("ALMACENAMIENTO", null).setContent(R.id.tabAlmacenamiento));
        tbh.addTab(tbh.newTabSpec("MAS").setIndicator("MASA", null).setContent(R.id.tabPeso));
        tbh.addTab(tbh.newTabSpec("TIE").setIndicator("TIEMPO", null).setContent(R.id.tabTiempo));
        tbh.addTab(tbh.newTabSpec("VOL").setIndicator("VOLUMEN", null).setContent(R.id.tabVolumen));



        btn = findViewById(R.id.btnCalcularLongitud);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spn = findViewById(R.id.spnDeLongitud);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnALongitud);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtCantidadLongitud);
                double cantidad= Double.parseDouble(tempVal.getText().toString());
                double resp = objConversor.convertir(0, de, a, cantidad);

                Toast.makeText(getApplicationContext(), "Respuesta: "+ resp, Toast.LENGTH_LONG).show();
            }
        });

        btn = findViewById(R.id.btnCalcularMoneda);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spn = findViewById(R.id.spndeMonedas);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnAmonedas);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtCantidadMonedas);
                double cantidad= Double.parseDouble(tempVal.getText().toString());
                double resp = objConversor.convertirMoneda(0, de, a, cantidad);

                Toast.makeText(getApplicationContext(), "Respuesta: "+ resp, Toast.LENGTH_LONG).show();
            }
        });
        btn = findViewById(R.id.btnCalcularAlmacenamiento);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spn = findViewById(R.id.spndeAlmacenamiento);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnAalmacenamiento);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtCantidadAlmacenamiento);
                double cantidad= Double.parseDouble(tempVal.getText().toString());
                double resp = objConversor.convertirAlmacenamiento(0, de, a, cantidad);

                Toast.makeText(getApplicationContext(), "Respuesta: "+ resp, Toast.LENGTH_LONG).show();
            }
        });

        btn = findViewById(R.id.btnCalcularPeso);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spn = findViewById(R.id.spndeDePeso);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnAPeso);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtCantidadPeso);
                double cantidad= Double.parseDouble(tempVal.getText().toString());
                double resp = objConversor.convertirPeso(0, de, a, cantidad);

                Toast.makeText(getApplicationContext(), "Respuesta: "+ resp, Toast.LENGTH_LONG).show();
            }
        });
        btn = findViewById(R.id.btnCalcularTiempo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spn = findViewById(R.id.spndeDeTiempo);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnATiempo);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtCantidadTiempo);
                double cantidad= Double.parseDouble(tempVal.getText().toString());
                double resp = objConversor.convertirTiempo(0, de, a, cantidad);

                Toast.makeText(getApplicationContext(), "Respuesta: "+ resp, Toast.LENGTH_LONG).show();
            }
        });
        btn = findViewById(R.id.btnCalcularVolumen);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spn = findViewById(R.id.spndeDeVolumen);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnAVolumen);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtCantidadVolumen);
                double cantidad= Double.parseDouble(tempVal.getText().toString());
                double resp = objConversor.convertirVolumen(0, de, a, cantidad);

                Toast.makeText(getApplicationContext(), "Respuesta: "+ resp, Toast.LENGTH_LONG).show();
            }
        });
    }
}
class conversores{
    double[][] valores = {
            {1, 100, 39.3701, 3.28084, 1.193, 1.09361, 0.001, 0.000621371}
    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return valores[opcion][a] / valores[opcion][de] * cantidad;
    }
    double[][] valoresMoneda = {
            {1, 0.93, 149.90, 0.79, 17.04, 834.58, 1324.37, 7.22}
    };
    public double convertirMoneda(int opcion, int de, int a, double cantidad){
        return valoresMoneda[opcion][a] / valoresMoneda[opcion][de] * cantidad;
    }
    double[][] valoresAlmacenamiento = {
            {1, 0.125, 0.000125, 1.25e-7, 1.25e-10, 1.25e-13, 1.25e-16}
    };
    public double convertirAlmacenamiento(int opcion, int de, int a, double cantidad){
        return valoresAlmacenamiento[opcion][a] / valoresAlmacenamiento[opcion][de] * cantidad;
    }
    double[][] valoresPeso = {
            {1, 1000, 1e-6, 0.001, 0.00220462, 0.0352739200000000003}
    };
    public double convertirPeso(int opcion, int de, int a, double cantidad){
        return valoresPeso[opcion][a] / valoresPeso[opcion][de] * cantidad;
    }
    double[][] valoresTiempo = {
            {1000, 1, 0.01667, 0.0002778, 1.157E-5, 1.653E-6, 3.803E-7, 3.169E-8}
    };
    public double convertirTiempo(int opcion, int de, int a, double cantidad){
        return valoresTiempo[opcion][a] / valoresTiempo[opcion][de] * cantidad;
    }
    double[][] valoresVolumen = {
            {1, 1000, 0.219969, 3.51951, 35.1951, 56.3121, 0.0353147, 61.0237}
    };
    public double convertirVolumen(int opcion, int de, int a, double cantidad){
        return valoresVolumen[opcion][a] / valoresVolumen[opcion][de] * cantidad;
    }
}
