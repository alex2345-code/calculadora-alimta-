package com.example.calculadoradematerias;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    String[] codenames = { "pye", "fis", "qui", "ing", "ojyp", "mp", "ss", "isb", "lab", "pi" };
    String[] general = { "gen0", "gen1", "gen2", "gen3" };
    String[] materias = { "Probabilidad y Estadistica", "Fisica IV", "Quimica IV", "Ingles IV", "Orientacion Juvenil y Profesional IV", "Metodos Agiles de Programacion", "Soporte de Software", "Ingenieria de Software Basica", "Laboratorio", "Proyecto Integrador" };
    DecimalFormat df = new DecimalFormat("0.00");
    String zzz = "Nada destacable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton boton = findViewById(R.id.boton);
        boton.setOnClickListener(ejecutar);
    }

    public View.OnClickListener ejecutar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String materia;
            Double primerParcial[] = new Double[10];
            Double segundoParcial[] = new Double[10];
            Double tercerParcial[] = new Double[10];
            Double cuartoParcial[] = new Double[10];

            // Calcular promedio por materia
            for (int a = 0; a < 10; a++){
                materia = codenames[a];
                double parciales[] = new double[3];
                for (int i = 0;i < 3; i++){
                    int id = getResources().getIdentifier(materia+i, "id",getPackageName());
                    EditText actual = findViewById(id);
                    String contenido = actual.getText().toString();
                    if(contenido.matches("")){
                        contenido = "0.00";
                        actual.setText("0");
                    }else if (Double.parseDouble(contenido) > 10){
                        contenido = "10.00";
                        actual.setText("10");
                    }
                    parciales[i] = Double.parseDouble(contenido);
                    if(i == 0){
                        primerParcial[a] = parciales[i];
                    }else if (i == 1){
                        segundoParcial[a] = parciales[i];
                    }else{
                        tercerParcial[a] = parciales[i];
                    }
                }
                double promedio = (parciales[0] + parciales[1] + parciales[2])/3;
                int id = getResources().getIdentifier(materia+'3', "id",getPackageName());
                TextView cuarto = findViewById(id);
                if(promedio < 6){
                    cuarto.setTextColor(Color.RED);
                }else{
                    cuarto.setTextColor(Color.parseColor("#000000"));
                }
                cuartoParcial[a] = promedio;
                cuarto.setText(df.format(promedio));
            }

            // Calcular promedio general
            promedioGeneral(general[0], primerParcial);
            promedioGeneral(general[1], segundoParcial);
            promedioGeneral(general[2], tercerParcial);
            promedioGeneral(general[3], cuartoParcial);

            // Obtener la mejor materia
            mejor("mej1", primerParcial);
            peor("peo1", primerParcial);
            mejor("mej2", segundoParcial);
            peor("peo2", segundoParcial);
            mejor("mej3", tercerParcial);
            peor("peo3", tercerParcial);
            mejor("mej4", cuartoParcial);
            peor("peo4", cuartoParcial);

            // Que materias hacen extra
            extraordinarios(cuartoParcial);
        }

    };

    public void promedioGeneral(String columna, Double [] array){
        double suma = 0;
        for(int i = 0; i < 10; i++){
            suma = suma + array[i];
        }
        double promedio = suma / 10;
        int id = getResources().getIdentifier(columna, "id",getPackageName());
        TextView campo = findViewById(id);
        if(promedio < 6){
            campo.setTextColor(Color.RED);
        }else{
            campo.setTextColor(Color.parseColor("#000000"));
        }
        campo.setText(df.format(promedio));

    }

    public void mejor(String columna, Double array []){
        double mejor = 0.00;
        int posicion = 11;
        for(int i = 0; i < 10; i++){
            if(array[i] > mejor){
                mejor = array[i];
                posicion = i;
            }
        }
        int id = getResources().getIdentifier(columna, "id",getPackageName());
        TextView campo = findViewById(id);
        if(posicion != 11){
            campo.setText(materias[posicion]);
        }else{
            campo.setText(zzz);
        }
    }

    public void peor(String columna, Double array []){
        double peor = 11;
        int posicion = 11;
        for(int i = 0; i < 10; i++){
            if(array[i] < peor && array[i] != 0.00){
                peor = array[i];
                posicion = i;
            }
        }
        int id = getResources().getIdentifier(columna, "id",getPackageName());
        TextView campo = findViewById(id);
        if(posicion != 11){
            campo.setText(materias[posicion]);
        }else{
            campo.setText(zzz);
        }
    }

    public void extraordinarios(Double array []){
        String reprobadas = "";
        for(int i = 0; i < 10; i++){
            if(array[i] < 6){
                if(i != 9){
                    reprobadas = reprobadas + materias[i] + "\n";
                }else{
                    reprobadas = reprobadas + materias[i];
                }
            }
        }
        TextView campo = findViewById(R.id.extras);
        if(reprobadas != ""){
            campo.setText(reprobadas);
            campo.setTextColor(Color.RED);
        }else{
            campo.setText("-");
            campo.setTextColor(Color.parseColor("#000000"));
        }
    }
}