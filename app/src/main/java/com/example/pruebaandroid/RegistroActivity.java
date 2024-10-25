package com.example.pruebaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegistroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        UsuarioGlobal nuevoUsuario =  UsuarioGlobal.getInstance();
        EditText registrarUsuario = findViewById(R.id.registrarUsuario);
        EditText registrarCorreo = findViewById(R.id.registrarCorreo);
        EditText registrarContra = findViewById(R.id.registrarContra);
        Button botonRegistrar = findViewById(R.id.registrarBoton);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = registrarUsuario.getText().toString();
                String correo = registrarCorreo.getText().toString();
                String contra = registrarContra.getText().toString();

                if (!correo.contains("@")){
                    Toast.makeText(RegistroActivity.this, "Correo inválido, falta el @", Toast.LENGTH_SHORT).show();
                }else {
                    nuevoUsuario.setUsuarioGlobal(usuario);
                    nuevoUsuario.setCorreoGlobal(correo);
                    nuevoUsuario.setContraGlobal(contra);

                    Toast.makeText(RegistroActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });



        TextView linkLogin = findViewById(R.id.linkLogin);

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}