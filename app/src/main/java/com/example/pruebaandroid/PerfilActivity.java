package com.example.pruebaandroid;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        LinearLayout inicioLayout = findViewById(R.id.inicioLayoutPerfil);
        LinearLayout misProductosLayout = findViewById(R.id.misProductosLayoutPerfil);
        LinearLayout mapaLayout = findViewById(R.id.mapaLayoutPerfil);

        TextView perfilUsuario = findViewById(R.id.perfilUsuario);
        TextView perfilCorreo = findViewById(R.id.perfilCorreo);
        TextView perfilContra = findViewById(R.id.perfilContra);

        UsuarioGlobal usuarioLogueado = UsuarioGlobal.getInstance();

        String nombreUsuario = usuarioLogueado.getUsuarioGlobal();
        String correoUsuario = usuarioLogueado.getCorreoGlobal();
        String contraUsuario = usuarioLogueado.getContraGlobal();

        perfilUsuario.setText(nombreUsuario);
        perfilCorreo.setText("Correo: " + correoUsuario);
        perfilContra.setText("Contraseña: " + contraUsuario);


        inicioLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(PerfilActivity.this, R.raw.cambio);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Intent intent = new Intent(PerfilActivity.this, AplicacionActivity.class);
                startActivity(intent);
            }
        });

        misProductosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(PerfilActivity.this, R.raw.cambio);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Intent intent = new Intent(PerfilActivity.this, MisProductosActivity.class);
                startActivity(intent);
            }
        });

        mapaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(PerfilActivity.this, R.raw.cambio);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Intent intent = new Intent(PerfilActivity.this, MapaActivity.class);
                startActivity(intent);
            }
        });
    }
}
