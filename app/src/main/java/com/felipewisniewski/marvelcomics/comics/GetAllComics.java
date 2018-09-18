package com.felipewisniewski.marvelcomics.comics;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.felipewisniewski.marvelcomics.Business.Comics;
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

public class GetAllComics extends AsyncTask<Integer, Void, Void> {
    private static String TAG = "GetAllComics: ";
    private Context context;
    private String charId;

    private RecyclerView recyclerView;
    private ComicsAdapter adapter;
    private Boolean adapterInstanciado;

    static List<Comics> comicsList;

    private int total;
    static int offSet;

    private ProgressDialog progressDialog;

    public GetAllComics(ComicsActivity v, RecyclerView r, ComicsAdapter coAdp, String id) {
        this.context = v;
        this.recyclerView = r;
        this.charId = id;
        this.adapter = coAdp;
        comicsList = new ArrayList<>();
        adapterInstanciado = false;
    }

    public GetAllComics(ComicsActivity v, ComicsAdapter coAdp, String id) {
        this.context = v;
        this.charId = id;
        this.adapter = coAdp;
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
            JSONArray comics = data.getJSONArray("results");

            for(int i=0; i < comics.length(); i++) {
                JSONObject c = comics.getJSONObject(i);
                String id = c.getString("id");
                String title = c.getString("title");
                String description = c.getString("description");

                Comics co = new Comics();
                co.setId(id);
                co.setTitle(title);
                co.setDescription(description);
                comicsList.add(co);
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
           adapter.setListComics(comicsList);
           recyclerView.setAdapter(adapter);
       }
       adapter.notifyDataSetChanged();
       Log.e(TAG, "onPostExecute()");
    }

    public List<Comics> getComicsList() { return comicsList; }

    public int getTotal() { return total; }

    public int getOffSet() { return offSet; }

    private String getUrl(Integer offset) {
        String urlGerada;
        String url = "https://gateway.marvel.com/v1/public/characters/" + charId + "/comics?offset=" + offset.toString();
        String publicKey = "c5c18968ee42b81da321e004e05a5205";
        String privateKey = "PRIVATE KEY";

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
