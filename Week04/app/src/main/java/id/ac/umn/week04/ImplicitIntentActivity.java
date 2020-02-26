package id.ac.umn.week04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ImplicitIntentActivity extends AppCompatActivity {

    EditText etWebURL, etPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_intent);

        etWebURL = findViewById(R.id.etWebURL);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
    }

    public void openWebUMN(View view) {
        Uri uri = Uri.parse("http://www.umn.ac.id");
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    }

    public void callUMN(View view) {
        Uri uri = Uri.parse("tel:02154220808");
        Intent i = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(i);
    }

    public void browseWebURL(View view) {
        String urlText = etWebURL.getText().toString();
        if(urlText.isEmpty()) {
            Toast.makeText(view.getContext(), "Please Enter Web URL", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(urlText));
            if(i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        }
    }

    public void callPhoneNumber(View view) {
        String phoneNumber = etPhoneNumber.getText().toString();
        if(phoneNumber.isEmpty()) {
            Toast.makeText(view.getContext(), "Please Enter Phone Number", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:" + phoneNumber));
            if(i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        }
    }
}
