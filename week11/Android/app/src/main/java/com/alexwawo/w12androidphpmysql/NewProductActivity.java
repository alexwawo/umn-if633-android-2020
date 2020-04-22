package com.alexwawo.w12androidphpmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewProductActivity extends AppCompatActivity {

    // progress dialog
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    EditText inputName, inputPrice, inputDesc;
    Button btnCreateProduct;
    String name, price, desc;

    // url to create new product
    //private static String url_create_products = "http://10.0.2.2/www/android/w12/create_product.php";
    private static String url_create_products = "https://android.mobdevumn.com/create_product.php";

    // JSON node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        // get the input
        inputName = (EditText) findViewById(R.id.inputName);
        inputPrice = (EditText) findViewById(R.id.inputPrice);
        inputDesc = (EditText) findViewById(R.id.inputDesc);
        btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

        btnCreateProduct.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = inputName.getText().toString();
                price = inputPrice.getText().toString();
                desc = inputDesc.getText().toString();

                Log.d("DEBUG", "onCLICK");
                // creating new product in background thread
                new CreateNewProduct().execute();
            }
        });

    }

    // background AsyncTask to create new product
    class CreateNewProduct extends AsyncTask<String, String, String> {
        // before starting background thread, show progress dialog
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Log.d("DEBUG", "onPreExecute");
            pDialog = new ProgressDialog(NewProductActivity.this);
            pDialog.setMessage("Creating Product...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // creating product using url
        @Override
        protected String doInBackground(String... params){
            Log.d("DEBUG", "doInBackground");
            // build Pair
            List<Pair<String, String>> args = new ArrayList<Pair<String, String>>();
            args.add(new Pair<>("name", name));
            args.add(new Pair<>("price", price));
            args.add(new Pair<>("description", desc));
            JSONObject jsonObject = null;

            try{
                // getting JSON object
                // note that create product url acceps POST method
                jsonObject = jsonParser.makeHttpRequest(url_create_products, "POST", args);
            }catch (IOException e){
                Log.d("Networking", e.getLocalizedMessage());
            }

            // check log cat for response
            Log.d("Create response", jsonObject.toString());
            Log.d("DEBUG", jsonObject.toString());

            // check for success tag
            try{
                int success = jsonObject.getInt(TAG_SUCCESS);
                if(success == 1){
                    // successfully create product
                    Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                    startActivity(i);
                    // closing this screen
                    finish();
                }else{
                    // failed to create product
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
