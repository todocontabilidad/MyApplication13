package com.facturacion.copacabana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button btn1;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn = findViewById(R.id.btn_play_audio);
        btn1= findViewById(R.id.btn_facturacion);
        mediaPlayer = new MediaPlayer();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseSong();
                } else {
                    playSong();
                }
            }
        });


        // Configurar el MediaPlayer
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Facturas.class));
            }
        });
    }

    private void playSong() {
        try {
            // Establecer la fuente de datos del MediaPlayer desde Firebase Storage
            String audioUrl = "https://firebasestorage.googleapis.com/v0/b/facturas-ad05f.appspot.com/o/sound.mp3?alt=media&token=e23e7528-e301-4b1c-8886-7c05fb6218b0";
            mediaPlayer.setDataSource(MainActivity.this, Uri.parse(audioUrl));

            // Preparar el MediaPlayer de forma asíncrona
            mediaPlayer.prepareAsync();

            // Configurar un listener para cuando el MediaPlayer esté preparado
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // Iniciar la reproducción del audio
                    mediaPlayer.start();
                    isPlaying = true;
                    btn.setText("Pausar");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error al configurar el audio: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void pauseSong() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            btn.setText("Reanudar");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar los recursos del MediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
