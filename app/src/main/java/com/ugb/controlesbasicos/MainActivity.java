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
    conversor objParcial = new conversor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhParcial);
        tbh.setup();
        tbh.addTab(tbh.newTabSpec("TAR").setIndicator("TARIFA AGUA", null).setContent(R.id.tabTarifaAgua));
        tbh.addTab(tbh.newTabSpec("ARE").setIndicator("AREA", null).setContent(R.id.tabArea));

        btn=findViewById(R.id.btnCalcularAgua);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempVal=findViewById(R.id.txtCantidadAgua);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                double cuotafija = 6.0;
                double tasaExceso = 0.45;
                double sobreExceso = 0.65;

                double ValorAPagar;

                if (cantidad <= 18){
                    ValorAPagar = cuotafija;
                } else if (cantidad <= 28){
                    double exceso = cantidad - 18;
                    double cargoExceso = exceso * tasaExceso;
                    ValorAPagar = cuotafija + cargoExceso;
                } else{
                    double exceso = cantidad - 28;
                    double cargaExceso = exceso * sobreExceso;
                    ValorAPagar = cuotafija + cargaExceso;
                }

                Toast.makeText(getApplicationContext(), "Respuesta: "+ ValorAPagar, Toast.LENGTH_LONG).show();

            }
        });

        btn = findViewById(R.id.btnCalcularArea);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spn = findViewById(R.id.spndeArea);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnAArea);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtCantidadArea);
                double cantidad= Double.parseDouble(tempVal.getText().toString());
                double resp = objParcial.convertirArea(0, de, a, cantidad);

                Toast.makeText(getApplicationContext(), "Respuesta: "+ resp, Toast.LENGTH_LONG).show();
            }
        });
    }
}
class conversor{
    double[][] valoresArea = {
            {1,10.763,1.43,1.19599,0.0022883295194508,0.000143082804880,0.0001}
    };

    public double convertirArea(int opcion, int de, int a, double cantidad){
        return valoresArea[opcion][a] / valoresArea[opcion][de] * cantidad;
    }
}