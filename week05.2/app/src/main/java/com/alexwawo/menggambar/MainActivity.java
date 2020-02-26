package com.alexwawo.menggambar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        Kanvasku kanvasKu;
        kanvasKu = new Kanvasku(this);
        kanvasKu.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        this.setContentView(kanvasKu);
    }
}
