package vista;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerald.informedcity.R;
import com.example.gerald.informedcity.modelo.Evento;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import controlador.Singleton;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private ArrayList<Evento> listaEventos;

    private TextView textViewUser;
    private TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);
        textViewUser = hView.findViewById(R.id.textViewUser);
        textViewEmail = hView.findViewById(R.id.textViewEmail);

        textViewUser.setText(Singleton.getInstance().getControlador().getUsuario().getUserName());
        textViewEmail.setText(Singleton.getInstance().getControlador().getUsuario().getCorreo());

        navigationView.setNavigationItemSelectedListener(this);

        listaEventos = new ArrayList<>();
        cargarEventosActuales();
        cargarEventosFuturos();
        //PARA EL MAPA
        Singleton.getInstance().getControlador().setListaEventos(listaEventos);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapPrincipal);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(getApplicationContext(),AcercaDe.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                //Donde se capturan los datos
                String marcaSeleccionada = arg0.getTitle();
                if(!marcaSeleccionada.equals("Usted está acá")){
                    Evento evento = buscarEvento(marcaSeleccionada);
                    Singleton.getInstance().getControlador().getGestorEventos().setEventoSeleccionado(evento);
                    Intent intent = new Intent(getApplicationContext(), TabEventos.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                mMap.clear();

                for (int i = 0; i < listaEventos.size(); i++) {
                    Evento evento = listaEventos.get(i);
                    LatLng posicion = new LatLng(Float.parseFloat(evento.getPosicionX()), Float.parseFloat(evento.getPosicionY()));
                    MarkerOptions marca = new MarkerOptions();
                    if(evento.getTipo().equals("ACTUAL")){
                        mMap.addMarker(new MarkerOptions().position(posicion).title(evento.getNombre()).snippet(evento.getCategoria())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }else {
                        mMap.addMarker(new MarkerOptions().position(posicion).title(evento.getNombre()).snippet(evento.getCategoria())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }
                }

                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Usted está acá"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                mMap.clear();

                for (int i = 0; i < listaEventos.size(); i++) {
                    Evento evento = listaEventos.get(i);
                    LatLng posicion = new LatLng(Float.parseFloat(evento.getPosicionX()), Float.parseFloat(evento.getPosicionY()));
                    MarkerOptions marca = new MarkerOptions();
                    if(evento.getTipo().equals("ACTUAL")){
                        mMap.addMarker(new MarkerOptions().position(posicion).title(evento.getNombre()).snippet(evento.getCategoria())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }else {
                        mMap.addMarker(new MarkerOptions().position(posicion).title(evento.getNombre()).snippet(evento.getCategoria())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }

                }

                LatLng location = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(location).title("Usted está acá"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.5f));
                //mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        } catch (Exception excepcion) {
            Toast.makeText(this, "No se cargo ubicación correctamente.", Toast.LENGTH_SHORT).show();
        }
    }

    public void verDatosEvento(){

    }

    public void cargarEventosActuales() {
        try {
            JSONObject jsonParam = new JSONObject();
            String direccion = Singleton.getInstance().getControlador().getContext().getString(R.string.evento_actual)+".json";
            String tipo = "GET";
            String resultado = Singleton.getInstance().getControlador().getDaoApi().consultaApi(direccion, tipo, jsonParam);

            JSONArray marcas = new JSONArray(resultado);

            for(int i= 0;i<marcas.length();i++){
                String valorJson = marcas.getString(i);
                JSONObject marca = new JSONObject(valorJson);

                int id=marca.getInt("id");
                String nombre = marca.getString("nombre_evento");
                String categoria = marca.getString("categoria");
                String descrpcion = marca.getString("descripcion");
                float latitud = (float) marca.getDouble("posicion_x");
                float longitud = (float) marca.getDouble("posicion_y");
                boolean disponible = (boolean) marca.getBoolean("disponible");
                int reporte = marca.getInt("reportes");
                int verificacion = marca.getInt("verificacion");
                String fecha = marca.getString("created_at");

                Evento evento = new Evento();
                evento.setIdEvento(id);
                evento.setNombre(nombre);
                evento.setCategoria(categoria);
                evento.setDescripcion(descrpcion);
                evento.setPosicionX(Float.toString(latitud));
                evento.setGetPosicionY(Float.toString(longitud));
                evento.setReportes(reporte);
                evento.setVerificaciones(verificacion);
                evento.setTipo("ACTUAL");
                evento.setDisponible(disponible);
                evento.setFechaPublicacion(fecha);
                listaEventos.add(evento);

            }
        } catch (InterruptedException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } catch (ExecutionException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void cargarEventosFuturos() {
        try {
            JSONObject jsonParam = new JSONObject();
            String direccion = Singleton.getInstance().getControlador().getContext().getString(R.string.evento_futuro)+".json";
            String tipo = "GET";
            String resultado = Singleton.getInstance().getControlador().getDaoApi().consultaApi(direccion, tipo, jsonParam);

            JSONArray marcas = new JSONArray(resultado);

            for(int i= 0;i<marcas.length();i++){
                String valorJson = marcas.getString(i);
                JSONObject marca = new JSONObject(valorJson);

                int id=marca.getInt("id");
                String nombre = marca.getString("nombre_evento");
                String categoria = marca.getString("categoria");
                String descrpcion = marca.getString("descripcion");
                float latitud = (float) marca.getDouble("posicion_x");
                float longitud = (float) marca.getDouble("posicion_y");
                boolean disponible = (boolean) marca.getBoolean("disponible");
                int reporte = marca.getInt("reportes");
                int verificacion = marca.getInt("verificacion");
                String fecha = marca.getString("created_at");
                String fechaP = marca.getString("fecha");

                Evento evento = new Evento();
                evento.setIdEvento(id);
                evento.setNombre(nombre);
                evento.setCategoria(categoria);
                evento.setDescripcion(descrpcion);
                evento.setPosicionX(Float.toString(latitud));
                evento.setGetPosicionY(Float.toString(longitud));
                evento.setReportes(reporte);
                evento.setVerificaciones(verificacion);
                evento.setDisponible(disponible);
                evento.setTipo("FUTURO");
                evento.setFechaProgramada(fechaP);
                evento.setFechaPublicacion(fecha);
                listaEventos.add(evento);

            }
        } catch (InterruptedException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } catch (ExecutionException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Evento buscarEvento(String evento){
        for(int i=0;i<listaEventos.size();i++){
            Evento event = listaEventos.get(i);
            if(event.getNombre().equals(evento)){
                return event;
            }
        }
        return  null;
    }

}
