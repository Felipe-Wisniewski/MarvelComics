package com.felipewisniewski.marvelcomics.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.felipewisniewski.marvelcomics.Business.Character;
import com.felipewisniewski.marvelcomics.Model.HttpHandler;
import com.felipewisniewski.marvelcomics.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MarvelsActivity extends Activity {

    private String TAG = MarvelsActivity.class.getSimpleName();

    private ProgressDialog progressDialog;

    private RecyclerView recyclerView;
    private MarvelsAdapter marvelsAdapter;
    List<Character> charactersList;

    private static String url = "http://gateway.marvel.com/v1/public/characters";
    private static String publicKey = "c5c18968ee42b81da321e004e05a5205";
    private static String privateKey = "58d89f57c259281e1a61d6d484825354a2cecd2b";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marvels);

        recyclerView = findViewById(R.id.recyclerview_listmarvels_id);

        charactersList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


    }

    private class GetCharacters extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MarvelsActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String urlGet = geraUrl();
            String jsonStr = sh.makeServiceCall(urlGet);

            Log.e(TAG, "Response from url: " + jsonStr);

            if(jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    //GET JSON com Array de Personagens
                    JSONArray characters = jsonObject.getJSONArray("results");

                    //Preeche lista de Personagens
                    for(int i=0; i < characters.length(); i++) {
                        JSONObject c = characters.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String description = c.getString("description");

                        JSONObject img = c.getJSONObject("thumbnail");
                        String thumbnail = img.getString("path");

                        Character cha = new Character();
                        cha.id = id;
                        cha.name = name;
                        cha.description = description;
                        cha.thumbnail = thumbnail;

                        charactersList.add(cha);
                    }
                } catch (final JSONException e) {

                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }

        public String geraUrl() {
            String urlGerada;
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            String hash = ts + publicKey + privateKey;
            String md5 = geraMd5(hash);

            urlGerada = url + "?ts=" + ts + "&apikey=" + publicKey + "&hash=" + md5;
            return urlGerada;
        }

        private String geraMd5(String hash) {
            try {
                // Create MD5 Hash
                MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
                digest.update(hash.getBytes());
                byte messageDigest[] = digest.digest();

                // Create Hex String
                StringBuffer hexString = new StringBuffer();
                for (int i=0; i<messageDigest.length; i++)
                    hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

                return hexString.toString();
            }catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "";
        }
    }


}
