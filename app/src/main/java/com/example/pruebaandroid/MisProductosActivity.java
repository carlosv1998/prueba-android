package com.example.pruebaandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MisProductosActivity extends AppCompatActivity {
    DataHelper dh;
    SQLiteDatabase bd;

    EditText nuevoProductoNombre, nuevoProductoPrecio, nuevoProductoCantidad;
    TableLayout tablaProductos;

    Button botonAgregar, botonEliminar, botonModificar;

    MediaPlayer mediaPlayer;

    private List<CheckBox> checkBoxes = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_productos);

        LinearLayout inicioLayout = findViewById(R.id.inicioLayoutProductos);
        LinearLayout perfilLayout = findViewById(R.id.perfilLayoutProductos);
        LinearLayout mapaLayout = findViewById(R.id.mapaLayoutProductos);

        nuevoProductoNombre = findViewById(R.id.nuevoProductoNombre);
        nuevoProductoPrecio = findViewById(R.id.nuevoProductoPrecio);
        nuevoProductoCantidad = findViewById(R.id.nuevoProductoCantidad);


        dh = new DataHelper(this, "app.db", null, 2);
        bd = dh.getWritableDatabase();

        tablaProductos = findViewById(R.id.tablaProductos);

        cargarLista();

        botonEliminar = findViewById(R.id.botonEliminar);
        botonModificar = findViewById(R.id.botonModificar);

        botonAgregar = findViewById(R.id.botonAgregar);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAgregar(view);
            }
        });

        botonEliminar.setOnClickListener(v -> {
            List<CheckBox> checkBoxesParaEliminar = new ArrayList<>();
            mediaPlayer = MediaPlayer.create(MisProductosActivity.this, R.raw.sonido);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) {
                    checkBoxesParaEliminar.add(checkBox);
                }
            }

            for (CheckBox checkBox : checkBoxesParaEliminar) {
                int productoId = (int) checkBox.getTag();
                eliminarProducto(productoId);
            }
        });

        botonModificar.setOnClickListener(v -> {
            mediaPlayer = MediaPlayer.create(MisProductosActivity.this, R.raw.sonido);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            if (!verificarInputs()) return;
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) {

                    int productoId = (int) checkBox.getTag();
                    String idFinal = String.valueOf(productoId);
                    ContentValues reg = new ContentValues();

                    int precio = 0;
                    try {
                        precio = Integer.parseInt(nuevoProductoPrecio.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Por favor, ingrese un precio válido", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int cantidad = 0;
                    try {
                        cantidad = Integer.parseInt(nuevoProductoCantidad.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Por favor, ingrese una cantidad válida", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    reg.put("nombre", nuevoProductoNombre.getText().toString());
                    reg.put("precio", precio);
                    reg.put("cantidad", cantidad);

                    long respuesta = bd.update("producto", reg, "id=?", new String[]{idFinal});

                    if (respuesta == -1){
                        Toast.makeText(this,"Dato No Modificado", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this,"Dato Modificado", Toast.LENGTH_LONG).show();
                    }
                    limpiar();
                    cargarLista();

                    return;
                }
            }
        });


        inicioLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(MisProductosActivity.this, R.raw.cambio);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Intent intent = new Intent(MisProductosActivity.this, AplicacionActivity.class);
                startActivity(intent);
            }
        });

        perfilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(MisProductosActivity.this, R.raw.cambio);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Intent intent = new Intent(MisProductosActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        mapaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(MisProductosActivity.this, R.raw.cambio);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Intent intent = new Intent(MisProductosActivity.this, MapaActivity.class);
                startActivity(intent);
            }
        });

    }

    private List<Productos> obtenerProductos() {
        Cursor c = bd.rawQuery("Select id, nombre, precio, cantidad from producto", null);
        List<Productos> listaProductos = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                listaProductos.add(new Productos(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), "Disponible"));
            }while (c.moveToNext());
        }
        return listaProductos;
    }

    private void cargarLista(){
        checkBoxes.clear();
        List<Productos> listaProductos = obtenerProductos();
        Log.d("CargarLista", "Cantidad de productos: " + listaProductos.size());

        for (int i = tablaProductos.getChildCount() - 1; i > 0; i--) {
            tablaProductos.removeViewAt(i);
        }

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
            checkBox.setTag(producto.id);
            checkBoxes.add(checkBox);
            fila.addView(checkBox);

            TextView nombre = new TextView(this);
            nombre.setText(producto.nombre);
            nombre.setPadding(10, 10, 10, 10);
            nombre.setTextSize(18);
            fila.addView(nombre);

            TextView precio = new TextView(this);
            precio.setText(String.valueOf(producto.precio));
            precio.setPadding(10, 10, 10, 10);
            precio.setTextSize(18);
            precio.setTextColor(Color.parseColor("#00FF00"));
            fila.addView(precio);

            TextView cantidad = new TextView(this);
            cantidad.setText(String.valueOf(producto.cantidad));
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
            estado.setTextColor(Color.parseColor("#00FF00"));

            fila.addView(estado);


            tablaProductos.addView(fila);
        }
    }

    private void limpiar(){
        nuevoProductoNombre.setText("");
        nuevoProductoPrecio.setText("");
        nuevoProductoCantidad.setText("");
    }

    private void eliminarProducto(int id){
        long respuesta = bd.delete("producto", "id=" + id, null);
        if (respuesta == -1){
            Toast.makeText(this,"Dato No Eliminado", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Dato Eliminado", Toast.LENGTH_LONG).show();
        }
        cargarLista();
    }

    private Boolean verificarInputs(){
        if (TextUtils.isEmpty(nuevoProductoNombre.getText().toString()) ||
                TextUtils.isEmpty(nuevoProductoPrecio.getText().toString()) ||
                TextUtils.isEmpty(nuevoProductoCantidad.getText().toString())
        ) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void onClickAgregar (View view){
        mediaPlayer = MediaPlayer.create(MisProductosActivity.this, R.raw.sonido);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        if (!verificarInputs()) return;

        int precio = 0;
        try {
            precio = Integer.parseInt(nuevoProductoPrecio.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese un precio válido", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = 0;
        try {
            cantidad = Integer.parseInt(nuevoProductoCantidad.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese una cantidad válida", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues reg= new ContentValues();
        reg.put("nombre", nuevoProductoNombre.getText().toString());
        reg.put("precio", precio);
        reg.put("cantidad", cantidad);
        long resp = bd.insert("producto", null, reg);
        if(resp==-1){
            Toast.makeText(this,"No se pudo agregar el producto", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Producto agregado", Toast.LENGTH_LONG).show();
        }
        cargarLista();
        limpiar();
    }
}
