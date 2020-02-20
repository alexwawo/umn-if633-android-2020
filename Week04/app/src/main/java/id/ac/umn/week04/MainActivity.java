package id.ac.umn.week04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToLifecycleActivity(View view) {
        Intent i = new Intent(MainActivity.this, LifecycleActivity.class);
        startActivity(i);
    }

    public void goToImplicitIntentActivity(View view) {
        Intent i = new Intent(MainActivity.this, ImplicitIntentActivity.class);
        startActivity(i);
    }

    public void goToFirstActivity(View view) {
        Intent i = new Intent(MainActivity.this, FirstActivity.class);
        startActivity(i);
    }

    public void goToFragmentViaLayout(View view) {
        Intent i = new Intent(MainActivity.this, FragmentViaLayoutActivity.class);
        startActivity(i);
    }

    public void goToFragmentViaProgrammatic(View view) {
        Intent i = new Intent(MainActivity.this, FragmentViaProgrammaticActivity.class);
        startActivity(i);
    }

}
