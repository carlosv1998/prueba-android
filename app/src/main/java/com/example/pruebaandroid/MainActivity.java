package com.example.pruebaandroid;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView linkRegistro = findViewById(R.id.linkRegistro);

        EditText loginCorreo = findViewById(R.id.loginCorreo);
        EditText loginContra = findViewById(R.id.loginContra);
        Button loginBoton = findViewById(R.id.loginBoton);

        linkRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });


        loginBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correoIngresado = loginCorreo.getText().toString();
                String contraIngresada = loginContra.getText().toString();

                UsuarioGlobal usuarioRegistrado = UsuarioGlobal.getInstance();

                String correoRegistrado = usuarioRegistrado.getCorreoGlobal();
                String contraRegistrada = usuarioRegistrado.getContraGlobal();

                if (correoIngresado.equals(correoRegistrado) && contraIngresada.equals(contraRegistrada)) {
                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, AplicacionActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}