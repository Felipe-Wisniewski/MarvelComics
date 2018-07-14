package com.felipewisniewski.marvelcomics.Presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.felipewisniewski.marvelcomics.Model.HttpHandler;
import com.felipewisniewski.marvelcomics.View.MarvelsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetAllCharacters extends AsyncTask<Void, Void, Void> {
    private Context context;
    private RecyclerView recycleV;
    private MarvelsAdapter marvelsAdapter;
    private List<Character> charactersList = new ArrayList<>();

    private ProgressDialog progressDialog;


    public GetAllCharacters(Context marvelContext, RecyclerView r) {
        this.context = marvelContext;
        this.recycleV = r;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler sh = new HttpHandler();
        String urlGet = geraUrl();
        String jsonStr = sh.makeServiceCall(urlGet);

        String TAG = "GetAllCharacters.class";
        Log.e(TAG, "Response from url: " + jsonStr);

        if(jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);

                //GET JSON com Array de Personagens
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray characters = data.getJSONArray("results");

                //Preeche lista de Personagens
                for(int i=0; i < characters.length(); i++) {
                    JSONObject c = characters.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("name");
                    String description = c.getString("description");

                    JSONObject img = c.getJSONObject("thumbnail");
                    String path = img.getString("path");
                    String ext = img.getString("extension");

                    Character cha = new Character();
                    cha.id = id;
                    cha.name = name;
                    cha.description = description;
                    cha.thumbnail = path + "." + ext;

                    charactersList.add(cha);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());

                Toast.makeText(context, "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e(TAG, "Error from server");
            Toast.makeText(context, "Error from Server", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if(progressDialog.isShowing())
            progressDialog.dismiss();

        marvelsAdapter = new MarvelsAdapter(charactersList, context);
        recycleV.setAdapter(marvelsAdapter);
    }

    public String geraUrl() {
        String urlGerada;
        String url = "http://gateway.marvel.com/v1/public/characters";
        String publicKey = "c5c18968ee42b81da321e004e05a5205";
        String privateKey = "58d89f57c259281e1a61d6d484825354a2cecd2b";

        String ts = new SimpleDateFormat("yyyyMMddHHmmssZ").format(new Date());
        String hash = ts + privateKey + publicKey;
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
