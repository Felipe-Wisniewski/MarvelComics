package com.felipewisniewski.marvelcomics.marvels;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.felipewisniewski.marvelcomics.Business.Character;
import com.felipewisniewski.marvelcomics.Model.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetAllCharacters extends AsyncTask<Integer, Void, Void> {
    static  String TAG = "GetAllCharacters: ";
    private Context context;

    private RecyclerView recyclerView;
    private MarvelsAdapter marvelsAdapter;
    private Boolean adapterInstanciado;

    static List<Character> charactersList;

    private int total;
    static int offSet;

    private ProgressDialog progressDialog;

    public GetAllCharacters(MarvelsActivity m, RecyclerView r, MarvelsAdapter ma) {
        this.context = m;
        this.recyclerView = r;
        this.marvelsAdapter = ma;
        charactersList  = new ArrayList<>();
        adapterInstanciado = false;
    }

    public GetAllCharacters(MarvelsActivity m, MarvelsAdapter ma) {
        this.context = m;
        this.marvelsAdapter = ma;
        adapterInstanciado = true;
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
    protected Void doInBackground(Integer... os) {
        String jsonStr = null;

        while(jsonStr == null){
            HttpHandler sh = new HttpHandler();
            String url = getUrl(os[0]);
            jsonStr = sh.makeServiceCall(url);
        }

        Log.e(TAG, "Retorno JSON: " + jsonStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject data = jsonObject.getJSONObject("data");
            total = data.getInt("total");
            offSet = data.getInt("offset");
            JSONArray characters = data.getJSONArray("results");

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
                offSet++;
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(progressDialog.isShowing()) progressDialog.dismiss();

        if(!adapterInstanciado){
            marvelsAdapter.setListCharacter(charactersList);
            recyclerView.setAdapter(marvelsAdapter);
        }
        marvelsAdapter.notifyDataSetChanged();
    }

    public List<Character> getCharactersList() {
        return charactersList;
    }

    public int getTotal() {
        return total;
    }

    public int getOffSet() {
        return offSet;
    }

    private String getUrl(Integer offset) {
        String urlGerada;
        String url = "https://gateway.marvel.com/v1/public/characters?offset=" + offset.toString();
        String publicKey = "c5c18968ee42b81da321e004e05a5205";
        String privateKey = "58d89f57c259281e1a61d6d484825354a2cecd2b";

        String ts = new SimpleDateFormat("yyyyMMddHHmmssZ").format(new Date());
        String hash = ts + privateKey + publicKey;
        String md5 = getMd5(hash);

        urlGerada = url + "&ts=" + ts + "&apikey=" + publicKey + "&hash=" + md5;
        return urlGerada;
    }

    private String getMd5(String hash) {
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
