package com.example.examengs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Personajes extends AppCompatActivity {

    TextView tv;
    ImageView img;
    String extraerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajes);
        // SE RECIBE
        Bundle bolsaReceptora = getIntent().getExtras();
        // SE EXTRAE EL ID Y SE GUARDA EN UNA VARIABLE ID
        String id = bolsaReceptora.getString("id");
        tv=(TextView)findViewById(R.id.textView2);
        extraerID = id;
        img=(ImageView)findViewById(R.id.imageView);

        Tarea2 t = new Tarea2();

        // AL ENLACE DE LA BASE DE DATOS SE LE CONCATENA EL NUEVO ID OBTENIDO CON EL MÉTODO SUBSTRING
        t.execute("https://akabab.github.io/starwars-api/api/id/"+extraerID+".json");



    }


    class Tarea2 extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String salida=ConexionWeb(strings[0]);
            return salida;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try
            {

                String cadenaJson=s.toString();
                JSONObject objetoJson = new JSONObject(cadenaJson);

                String id = objetoJson.getString("name");
                String nombreP = objetoJson.getString("gender");
                String nombreR = objetoJson.getString("height");
                String descripcion = objetoJson.getString("mass");
                String especie = objetoJson.getString("species");
                String planeta = objetoJson.getString("homeworld");
                String afiliaciones = objetoJson.getString("affiliations");


                String foto = objetoJson.getString("image");

                NPersona persona = new NPersona(id,nombreP,nombreR,descripcion,foto, especie, planeta, afiliaciones );
                tv.append(""+id+"\n"+nombreP+"\n"+nombreR+"\n"+descripcion+"\n" +especie+"\n"+planeta+"\n"+afiliaciones+"\n");
                Picasso.get().load(foto).into(img);

            }
            catch(Exception e)
            {
                tv.setText(e.getMessage());
                Toast.makeText(Personajes.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }



    String ConexionWeb (String direccion) {

        String pagina="";
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
        }
        catch (Exception e){
            pagina+=e.getMessage();
        }
        return pagina;
    }



}
