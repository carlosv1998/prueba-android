package com.example.pruebaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class AplicacionActivity extends AppCompatActivity{

    // se declaran las variables para realizar la carga simulada de la imagen con threads
    private TextView textoCargandoLogo;
    private ImageView logoInicio;
    private ProgressBar barraProgresoLogo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplicacion);

        UsuarioGlobal usuarioLogueado = UsuarioGlobal.getInstance();

        String nombreUsuario = usuarioLogueado.getUsuarioGlobal();

        TextView textViewUsuario = findViewById(R.id.textoBienvenida);

        textViewUsuario.setText(nombreUsuario);

        // variables para la "barra de navegación"
        LinearLayout misProductosLayout = findViewById(R.id.misProductosLayoutInicio);
        LinearLayout perfilLayout = findViewById(R.id.perfilLayoutInicio);
        LinearLayout mapaLayout = findViewById(R.id.mapaLayoutInicio);


        // se establecen las variables para la carga de la imagen
        textoCargandoLogo = findViewById(R.id.textoCargandoLogo);
        logoInicio = findViewById(R.id.logoInicio);
        barraProgresoLogo = findViewById(R.id.barraProgresoLogo);


        // se crea un thread y se sobreescribe el método run, que simulará un retardo de 4 segundos para posteriormente seguir con el código
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // cuando pasen los 4 segundos se establecerá el mensaje y la barra de carga en invisible
                            // y la imagen se establecerá como visible, para simular una carga
                            barraProgresoLogo.setVisibility(View.GONE);
                            textoCargandoLogo.setVisibility(View.GONE);
                            logoInicio.setVisibility(View.VISIBLE);
                            logoInicio.setImageResource(R.drawable.logo);
                        }
                    });
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        // se inicia el thread al cargar la activity
        thread.start();


        misProductosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AplicacionActivity.this, MisProductosActivity.class);
                startActivity(intent);
            }
        });

        perfilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AplicacionActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        mapaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AplicacionActivity.this, MapaActivity.class);
                startActivity(intent);
            }
        });
    }
}
