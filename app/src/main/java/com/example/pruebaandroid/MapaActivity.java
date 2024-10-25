package com.example.pruebaandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import android.preference.PreferenceManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

// obtener ubicacion actual
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import android.content.pm.PackageManager;
import android.location.Location;
import android.Manifest;


public class MapaActivity extends AppCompatActivity  {

    // declaración de variables para mostrar el marcador con la posición actual en el mapa
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // variables para la "barra de navegacion"
        LinearLayout inicioLayout = findViewById(R.id.inicioLayoutMapa);
        LinearLayout perfilLayout = findViewById(R.id.perfilLayoutMapa);
        LinearLayout productosLayout = findViewById(R.id.misProductosLayoutMapa);


        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        // se establece la variable mapView y las características que tendrá el mapa
        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // se establece el spinner y se crea un array de strings que serán posteriormente asignados a las opciones del spinner
        Spinner mapTypeSpinner = findViewById(R.id.mapTypeSpinner);
        String[] mapTypes = { "Mapa normal", "Mapa de transporte", "Mapa topografico" };

        // se crea un adapter para el spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // se establece el adapter al spinner que tendra las opciones creadas en el array
        mapTypeSpinner.setAdapter(adapter);

        // se agrega un evento al spinner para cuando cambie de selección
        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                // en la posición 0 (por defecto) mostrará el mapa normal
                switch (position){
                    case 0:
                        mapView.setTileSource(TileSourceFactory.MAPNIK);
                        break;
                // en la posición 1, mostrará el mapa de transporte público
                    case 1:
                        mapView.setTileSource(new XYTileSource(
                                "PublicTransport",
                                0,
                                18,
                                256,
                                ".png",
                                new String[]{"https://tile.memomaps.de/tilegen/"}
                        ));
                        break;
                // en la posicion 2, mostrará un mapa topográfico
                    case 2:
                        mapView.setTileSource(new XYTileSource(
                                "ISGS_Satellite",
                                0,
                                18,
                                256,
                                ".png",
                                new String[]{
                                        "https://a.tile.opentopomap.org/",
                                        "https://b.tile.opentopomap.org/",
                                        "https://c.tile.opentopomap.org/"
                                }
                        ));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }

        });

        // se establece la variable fusedLocationClient a la ubicación actual del usuario
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // se pregunta al usuario por los permisos de ubicación, si están activados se llama a la función obtenerUbicacion, si no, se pregunta por los permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            obtenerUbicacion();
        }


        inicioLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapaActivity.this, AplicacionActivity.class);
                startActivity(intent);
            }
        });

        productosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapaActivity.this, MisProductosActivity.class);
                startActivity(intent);
            }
        });

        perfilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapaActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

    }

    // al iniciar la activity, se sobreescribe el evento de onRequestPermissionsResults, para que vuelva a preguntar por los permisos en caso de que el usuario
    // seleccione "no", si selecciona si se llama a obtenerUbicacion, si no se le mostrará un mensaje de explicación, si el usuario rechazó los permisos, el mapa
    // se mostrará, pero sin la ubicación actual del usuario.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, ahora podemos obtener la ubicación
                obtenerUbicacion();
            } else {
                // Permiso denegado
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // El usuario rechazó el permiso anteriormente, pero no marcó "No volver a preguntar"
                    // Muestra una explicación para pedir nuevamente el permiso
                    mostrarDialogoExplicacion();
                } else {
                    // El usuario ha marcado "No volver a preguntar"
                    // Mostrar un mensaje informativo
                    mostrarMensajePermisoDenegado();
                }
            }
        }
    }

    private void mostrarDialogoExplicacion() {
        new AlertDialog.Builder(this)
                .setTitle("Permiso de ubicación necesario")
                .setMessage("Esta aplicación requiere acceso a tu ubicación para mostrar tu posición en el mapa. Por favor concede el permiso.")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Volver a pedir el permiso
                        ActivityCompat.requestPermissions(MapaActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // El usuario rechazó nuevamente el permiso
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void mostrarMensajePermisoDenegado() {
        new AlertDialog.Builder(this)
                .setTitle("Permiso de ubicación denegado")
                .setMessage("El permiso de ubicación ha sido denegado permanentemente. Puedes habilitarlo manualmente en los ajustes de la aplicación.")
                .setPositiveButton("Ir a ajustes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Redirigir a los ajustes de la aplicación
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // El usuario decidió no ir a los ajustes
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void obtenerUbicacion() {
        // si los permisos estan activados se continúa con el código
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permisos concedidos, podemos obtener la ubicación
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Verificar si la ubicación no es nula
                    if (location != null) {

                        // se obtiene la longitud y latitud actual del usuario
                        double currentLatitude = location.getLatitude();
                        double currentLongitude = location.getLongitude();

                        // Crear un punto GeoPoint con la ubicación actual
                        GeoPoint currentLocationPoint = new GeoPoint(currentLatitude, currentLongitude);

                        // Centrar el mapa en la ubicación actual
                        mapView.getController().setZoom(15.0);
                        mapView.getController().setCenter(currentLocationPoint);

                        // Agregar un marcador en la ubicación actual
                        Marker marcador = new Marker(mapView);
                        marcador.setPosition(currentLocationPoint);
                        marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        marcador.setTitle("Ubicación Actual");
                        marcador.setSnippet("Esta es tu posición actual");

                        mapView.getOverlays().add(marcador);
                    }
                }
            });
        } else {
            // Si los permisos no han sido concedidos, solicitarlos
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
}
