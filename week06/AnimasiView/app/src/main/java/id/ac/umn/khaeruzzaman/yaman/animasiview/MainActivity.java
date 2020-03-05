package id.ac.umn.khaeruzzaman.yaman.animasiview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn;
    Animation animasiKu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button2);
        animasiKu = AnimationUtils.loadAnimation(this,R.anim.animasiku);
        btn.startAnimation(animasiKu);
    }
    public void jalankanAnimasi(View view){
        btn.startAnimation(animasiKu);
    }
}
