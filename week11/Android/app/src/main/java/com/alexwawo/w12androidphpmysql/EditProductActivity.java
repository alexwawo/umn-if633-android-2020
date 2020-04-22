package com.alexwawo.w12androidphpmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditProductActivity extends AppCompatActivity {

    EditText txtName, txtPrice, txtDesc;
    Button btnSave, btnDelete;
    String pid, name, price, desc;

    // progress dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
//    private static String url_product_details = "http://10.0.2.2:8000/get_product_details.php";
    private static String url_product_details = "https://android.mobdevumn.com/get_product_details.php";

    // url to update product
//    private static String url_update_product = "http://10.0.2.2:8000/update_product.php";
    private static String url_update_product = "https://android.mobdevumn.com/update_product.php";

    // url to delete product
//    private static String url_delete_product = "http://10.0.2.2:8000/delete_product.php";
    private static String url_delete_product = "https://android.mobdevumn.com/delete_product.php";

    // JSON node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        // lazy way to handle strict mode - networkOnMainThreadException
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // save and delete button
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // getting product details from intent
        Intent i = getIntent();

        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);

        // getting complete product details in background thread
        new GetProductDetails().execute();

        // save button click event
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // getting updated data from EditTexts
                name = txtName.getText().toString();
                price = txtPrice.getText().toString();
                desc = txtDesc.getText().toString();
                // starting background task to update product
                new SaveProductDetails().execute();
            }
        });

        // delete button click event
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // starting background task to delete product
                new DeleteProduct().execute();
            }
        });
    }

    // background AsyncTask to get complete product details
    class GetProductDetails extends AsyncTask<String, String, String> {
        // before starting background thread, show progress dialog
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Loading Product Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // getting product details using url
        @Override
        protected String doInBackground(String... params){
            Log.d("DEBUG", "EditProductActivity - doInBackground");
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    Log.d("DEBUG", "EditProductActivity - runOnUiThread");
                    // check for success tag
                    int success;
                    try{
                        // building parameters
                        List<Pair<String, String>> args = new ArrayList<Pair<String, String>>();
                        args.add(new Pair<>(TAG_PID, pid));
                        JSONObject jsonObject = null;
                        try{
                            // getting product details by making HTTP request
                            // note that product details url will use GET request
                            jsonObject = jsonParser.makeHttpRequest(url_product_details, "POST", args);
                        }catch(IOException e){
                            Log.d("Networking", e.getLocalizedMessage());
                        }

                        // check your logcat for JSON response
                        Log.d("Single product details", jsonObject.toString());

                        // json success tag
                        success = jsonObject.getInt(TAG_SUCCESS);
                        if(success == 1){
                            Log.d("DEBUG", "Product Found.");
                            // successfully received product details
                            JSONArray productObj = jsonObject.getJSONArray(TAG_PRODUCT);

                            // get first product object from JSON array
                            JSONObject product = productObj.getJSONObject(0);

                            // product with this pid found
                            // EditText
                            txtName = (EditText) findViewById(R.id.inputName);
                            txtPrice = (EditText) findViewById(R.id.inputPrice);
                            txtDesc = (EditText) findViewById(R.id.inputDesc);

                            // display product data in EditText
                            txtName.setText(product.getString(TAG_NAME));
                            txtPrice.setText(product.getString(TAG_PRICE));
                            txtDesc.setText(product.getString(TAG_DESCRIPTION));
                        }else{
                            // product with this pid not found
                            Log.d("DEBUG", "Product Not Found.");
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }

        // after completing background task, dismiss the progress dialog
        @Override
        protected void onPostExecute(String s){
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }

    // background AsyncTask to save product
    class SaveProductDetails extends AsyncTask<String, String, String>{
        // before starting background thread, show progress dialog
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Saving Product Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // saving product details using url
        @Override
        protected String doInBackground(String... params){
            Log.d("DEBUG", "SaveProduct - doInBackground");
            // building parameters
            List<Pair<String, String>> args = new ArrayList<Pair<String, String>>();
            args.add(new Pair<>(TAG_PID, pid));
            args.add(new Pair<>(TAG_NAME, name));
            args.add(new Pair<>(TAG_PRICE, price));
            args.add(new Pair<>(TAG_DESCRIPTION, desc));
            JSONObject jsonObject = null;
            try{
                // sending modified data through HTTP request
                //notice that update product url accepts POST method
                jsonObject = jsonParser.makeHttpRequest(url_update_product, "POST", args);
            }catch(IOException e){
                Log.d("Networking", e.getLocalizedMessage());
            }

            // check JSON success tag
            try{
                int success = jsonObject.getInt(TAG_SUCCESS);
                if(success == 1){
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                }else{
                    // failed to update product
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        // after completing background task, dismiss the progress dialog
        @Override
        protected void onPostExecute(String s){
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }

    // background AsyncTask to delete product
    class DeleteProduct extends AsyncTask<String, String, String>{
        // before starting background thread, show progress dialog
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Deleting Product. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // deleting product using url
        @Override
        protected String doInBackground(String... params){
            Log.d("DEBUG", "DeleteProduct - doInBackground");
            // builing parameters
            List<Pair<String, String>> args = new ArrayList<Pair<String, String>>();
            args.add(new Pair<>(TAG_PID, pid));
            JSONObject jsonObject = null;
            try{
                // getting product details through HTTP request
                // notice that delete product url accepts POST method
                jsonObject = jsonParser.makeHttpRequest(url_delete_product, "POST", args);
                Log.d("DEBUG", "url_delete_product");
            }catch(IOException e){
                Log.d("Networking", e.getLocalizedMessage());
            }

            // check your logcat for JSON response
            Log.d("Delete product", jsonObject.toString());
            Log.d("DEBUG", jsonObject.toString());
            // check JSON success tag
            try{
                int success = jsonObject.getInt(TAG_SUCCESS);
                if(success == 1){
                    Log.d("DEBUG", "Delete success.");
                    // successfully deleted
                    // notify previous activity by sending code 100
                    Intent i = getIntent();
                    // send result code 100 to notify about product delete
                    setResult(100, i);
                    finish();
                }else{
                    // failed to delete product
                    Log.d("DEBUG", "Delete failed.");
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        // after completing background task, dismiss the progress dialog
        @Override
        protected void onPostExecute(String s){
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }

}