package com.alexwawo.w12androidphpmysql;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllProductsActivity extends ListActivity {

    // progress dialog
    private ProgressDialog pDialog;
    // creating JSON parser object
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;
    // url to get all products list
//    private static String url_all_products = "http://10.0.2.2:8000/read_all_products.php";
    private static String url_all_products = "https://android.mobdevumn.com/read_all_products.php";

    // JSON node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";

    //products JSONArray
    JSONArray products = null;

    // background AsyncTask to load all products by making HTTP request
    class LoadAllProducts extends AsyncTask<String, String, String>{
        // before starting background thread, show progress dialog
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Log.d("DEBUG", "onPreExecute");
            pDialog = new ProgressDialog(AllProductsActivity.this);
            pDialog.setMessage("Loading Products, Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        // getting all products from url
        @Override
        protected String doInBackground(String... params){
            Log.d("DEBUGX", "doInBackgroundX");
            // building parameters
            List<Pair<String, String>> args = new ArrayList<Pair<String, String>>();
            JSONObject jsonObject = null;

            try{
                Log.d("DEBUG", "TRY1");
                // getting JSON string from url
                jsonObject = jParser.makeHttpRequest(url_all_products, "GET", args);
            }catch(IOException e){
                Log.d("Networking", e.getLocalizedMessage());
                Log.d("DEBUGX", e.getLocalizedMessage());
            }

            // check your log cat for JSON response
            Log.d("All products: ", jsonObject.toString());

            try{
                // checking for SUCCESS TAG
                int success = jsonObject.getInt(TAG_SUCCESS);
                if(success == 1){
                    // product found
                    // getting array of products
                    products = jsonObject.getJSONArray(TAG_PRODUCTS);
                    // looping through all products
                    for(int i=0; i<products.length(); i++){
                        JSONObject c = products.getJSONObject(i);
                        // storing each json item in variable
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);
                        // creating new hashmap
                        HashMap<String, String> map = new HashMap<String, String>();
                        // adding each child node to hashmap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        // adding hashlist to arraylist
                        productsList.add(map);
                    }
                }else{
                    // no products found
                    // launch add new product activity
                    Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
                    // closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        // after completing background task, dismiss the progress dialog
        @Override
        protected void onPostExecute(String s){
            Log.d("DEBUG", "onPostExecute");
            // dismiss the dialog after getting all products
            pDialog.dismiss();

            // updating UI from background thread
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    // updating parsed JSON data into ListView
                    ListAdapter adapter = new SimpleAdapter(AllProductsActivity.this, productsList, R.layout.list_item, new String[]{TAG_PID, TAG_NAME}, new int[]{R.id.pid, R.id.pName});
                    // updating ListView
                    setListAdapter(adapter);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        Log.d("DEBUG", "TEST");

        // hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();
        //loading products in background thread
        new LoadAllProducts().execute();
        // get the ListView
        ListView listView = getListView();
        // on selecting single product
        // launch the edit product screen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();
                // starting new intent
                Intent i = new Intent(getApplicationContext(), EditProductActivity.class);
                // sending pid to the next activity
                i.putExtra(TAG_PID, pid);
                // starting new activity and expecting some response back
                startActivityForResult(i, 100);
            }
        });
    }

    // response from edit product activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if(resultCode == 100){
            // if resultCode 100 is received means user edited/deleted product
            // reloal this screen again
            Intent i = getIntent();
            finish();
            startActivity(i);
        }
    }
}
