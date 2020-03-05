package com.alexwawo.demokelas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        Drawable shape = res.getDrawable(R.drawable.my_shape);
        TextView tv = findViewById(R.id.textView);
        tv.setBackground(shape);
    }
}
