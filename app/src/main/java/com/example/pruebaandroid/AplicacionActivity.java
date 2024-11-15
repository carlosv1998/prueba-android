package com.example.pruebaandroid;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class AplicacionActivity extends AppCompatActivity{

    // se declaran las variables para realizar la carga simulada de la imagen con threads
    private TextView textoCargandoLogo;
    private ImageView logoInicio;
    private ProgressBar barraProgresoLogo;
    MediaPlayer mediaPlayer;

    VideoView videoView;

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

        videoView = findViewById(R.id.video);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();


        WebView webView = findViewById(R.id.link);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String videoUrl = "https://www.youtube.com/embed/wf4F2-9UXUo";
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(videoUrl);



        misProductosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(AplicacionActivity.this, R.raw.cambio);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Intent intent = new Intent(AplicacionActivity.this, MisProductosActivity.class);
                startActivity(intent);
            }
        });

        perfilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(AplicacionActivity.this, R.raw.cambio);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Intent intent = new Intent(AplicacionActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        mapaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(AplicacionActivity.this, R.raw.cambio);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Intent intent = new Intent(AplicacionActivity.this, MapaActivity.class);
                startActivity(intent);
            }
        });
    }
}
