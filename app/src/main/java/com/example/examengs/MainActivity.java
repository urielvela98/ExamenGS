package com.example.examengs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    ArrayList items = new ArrayList();
    ArrayList items_especies = new ArrayList();
    ArrayAdapter<String> adaptador,adaptador_spinner;
    Spinner especies;
    EditText nombreP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        especies = findViewById(R.id.especies);
        lv.setOnItemClickListener(this);
        especies.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String especieT = especies.getSelectedItem().toString();
                adaptador.getFilter().filter(especieT);
                return false;
            }
        });
        nombreP= findViewById(R.id.nombreP);
        nombreP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = nombreP.getText().toString().toLowerCase(Locale.getDefault());
                adaptador.getFilter().filter(text);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Tarea t = new Tarea();
        t.execute("https://akabab.github.io/starwars-api/api/all.json");

        LlenarSpinner e = new LlenarSpinner();
        e.execute("https://akabab.github.io/starwars-api/api/all.json");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String IDE=String.valueOf(lv.getItemAtPosition(position));
        String[] parts = IDE.split(", ");
        String part1 = parts[1];
        Bundle bolsa = new Bundle();
        bolsa.putString("id", part1);


        Intent int1 = new Intent(this, Personajes.class);
        int1.putExtras(bolsa);
        startActivity(int1);
    }

    class Tarea extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String salida = ConexionWeb(strings[0]);
            return salida;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray arreglo = new JSONArray(s);
                for (int i = 0; i < arreglo.length(); i++)
                {
                    JSONObject renglon = arreglo.getJSONObject(i);
                    String id = renglon.getString("id");
                    String nombreP = renglon.getString("name");
                    String nombreR = renglon.getString("species");
                    // AGREGAMOS LOS ELEMENTOS A UN ARRAY (ES LO QUE SE VA A MOSTRAR EN LOS ITEMS DEL LISTVIEW)
                    items.add(nombreP+" , "+id+ ", "+ nombreR);
                    Context context = MainActivity.this;
                    // LE PASAMOS AL ADAPTADOR EL VALOR ARRAY PARA MOSTRARLO EN EL LISTVIEW
                    adaptador = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items);
                    lv.setAdapter(adaptador);
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error: "+e, Toast.LENGTH_LONG).show();
            }
        }
    }

    class LlenarSpinner extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String salida = ConexionWeb(strings[0]);
            return salida;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray arreglo = new JSONArray(s);
                for (int i = 0; i < arreglo.length(); i++)
                {
                    JSONObject renglon = arreglo.getJSONObject(i);
                    String id = renglon.getString("species");
                    // AGREGAMOS LOS ELEMENTOS A UN ARRAY (ES LO QUE SE VA A MOSTRAR EN LOS ITEMS DEL LISTVIEW)
                    if (!items_especies.contains(id)){
                        items_especies.add(id);
                    }

                    Context context = MainActivity.this;
                    // LE PASAMOS AL ADAPTADOR EL VALOR ARRAY PARA MOSTRARLO EN EL LISTVIEW
                    adaptador_spinner = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items_especies);
                    especies.setAdapter(adaptador_spinner);
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error: "+e, Toast.LENGTH_LONG).show();
            }
        }
    }
    String ConexionWeb(String direccion) {
        String pagina = "";
        try {
            URL url = new URL(direccion);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    pagina += linea + "\n";
                    linea = reader.readLine();
                }
                reader.close();
            } else {
                pagina += "ERROR: " + conexion.getResponseMessage() + "\n";
            }
            conexion.disconnect();
        } catch (Exception e) {
            pagina += e.getMessage();
        }
        return pagina;
    }
}