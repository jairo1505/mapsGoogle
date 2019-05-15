package br.org.catolicasc.mapsgoogle;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DAL dal;
    private Cursor cursor;
    private static final String TAG = "MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        dal = new DAL(this);
        cursor = dal.loadAll();


        if(cursor.getCount()==0) {
            DataDownload dataDownload = new DataDownload();
            String jsonString;
            try {
                jsonString = dataDownload.execute("http://www.mocky.io/v2/5cdb4544300000640068cc7b").get();
                JSONTokener jsonTokener = new JSONTokener(jsonString);
                JSONObject jsonObject = new JSONObject(jsonTokener);
                JSONArray jsonArray = jsonObject.getJSONArray("pessoas");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject contatos = jsonArray.getJSONObject(i);
                    dal.insert(
                            contatos.getString("nome"),
                            contatos.getString("email"),
                            Double.valueOf(contatos.getString("latitude")),
                            Double.valueOf(contatos.getString("longitude"))
                    );
                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++) {

            LatLng ponto = new LatLng(cursor.getDouble(cursor.getColumnIndex(CreateDatabase.LATITUDE)), cursor.getDouble(cursor.getColumnIndex(CreateDatabase.LONGITUDE)));
            mMap.addMarker(new MarkerOptions().position(ponto).title("Nome: "+cursor.getString(cursor.getColumnIndex(CreateDatabase.NOME))+" / E-mail: "+cursor.getString(cursor.getColumnIndex(CreateDatabase.EMAIL))));
            cursor.moveToNext();
        }
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(catolica, 15));
    }
}
