package com.example.pruebaandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class MisProductosActivity extends AppCompatActivity {

    private List<CheckBox> checkBoxes = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_productos);

        LinearLayout inicioLayout = findViewById(R.id.inicioLayoutProductos);
        LinearLayout perfilLayout = findViewById(R.id.perfilLayoutProductos);
        LinearLayout mapaLayout = findViewById(R.id.mapaLayoutProductos);

        TableLayout tablaProductos = findViewById(R.id.tablaProductos);
        List<Productos> listaProductos = new ArrayList<>();

        listaProductos.add(new Productos("Producto 1", "$10", "5", "Disponible"));
        listaProductos.add(new Productos("Producto 2", "$15", "3", "Agotado"));
        listaProductos.add(new Productos("Producto 3", "$8.244", "10", "Disponible"));
        listaProductos.add(new Productos("Producto 4", "$8.444", "24", "Disponible"));
        listaProductos.add(new Productos("Producto 5", "$8.000", "1", "Disponible"));
        listaProductos.add(new Productos("Producto 6", "$16.000", "10", "Disponible"));
        listaProductos.add(new Productos("Producto 7", "$42.000", "32", "Agotado"));
        listaProductos.add(new Productos("Producto 8", "$500", "100", "Disponible"));

        float density = getResources().getDisplayMetrics().density;
        int anchoDp = 42;
        int altoDp = 42;

        int anchoPx = (int) (anchoDp * density);
        int altoPx = (int) (altoDp * density);

        for (Productos producto : listaProductos){
            TableRow fila = new TableRow(this);
            CheckBox checkBox = new CheckBox(this);
            TableRow.LayoutParams layoutParamsCheckBox = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            layoutParamsCheckBox.gravity = Gravity.CENTER;
            checkBox.setLayoutParams(layoutParamsCheckBox);
            checkBoxes.add(checkBox);
            fila.addView(checkBox);

            TextView nombre = new TextView(this);
            nombre.setText(producto.nombre);
            nombre.setPadding(10, 10, 10, 10);
            nombre.setTextSize(18);
            fila.addView(nombre);

            TextView precio = new TextView(this);
            precio.setText(producto.precio);
            precio.setPadding(10, 10, 10, 10);
            precio.setTextSize(18);
            precio.setTextColor(Color.parseColor("#00FF00"));
            fila.addView(precio);

            TextView cantidad = new TextView(this);
            cantidad.setText(producto.cantidad);
            cantidad.setPadding(10, 10, 10, 10);
            cantidad.setTextSize(18);
            cantidad.setGravity(Gravity.CENTER);
            fila.addView(cantidad);

            ImageView imagen = new ImageView(this);
            imagen.setImageResource(R.drawable.box);
            TableRow.LayoutParams layoutParamsImage = new TableRow.LayoutParams(anchoPx, altoPx);
            imagen.setLayoutParams(layoutParamsImage);
            fila.addView(imagen);

            TextView estado = new TextView(this);
            estado.setText(producto.estado);
            estado.setPadding(10, 10, 10, 10);
            estado.setTextSize(18);

            if (producto.estado.equals("Agotado")){
                estado.setTextColor(Color.parseColor("#FF0000"));
            }else {
                estado.setTextColor(Color.parseColor("#00FF00"));
            }
            fila.addView(estado);


            tablaProductos.addView(fila);
        }

        Button botonEliminar = findViewById(R.id.botonEliminar);

        botonEliminar.setOnClickListener(v -> {
            List<CheckBox> checkBoxesParaEliminar = new ArrayList<>();

            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) {
                    checkBoxesParaEliminar.add(checkBox);
                }
            }

            for (CheckBox checkBox : checkBoxesParaEliminar) {
                TableRow fila = (TableRow) checkBox.getParent();
                tablaProductos.removeView(fila);
                checkBoxes.remove(checkBox);
            }
        });




        inicioLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MisProductosActivity.this, AplicacionActivity.class);
                startActivity(intent);
            }
        });

        perfilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MisProductosActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        mapaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MisProductosActivity.this, MapaActivity.class);
                startActivity(intent);
            }
        });

    }
}
