package id.ac.umn.week04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

public class FragmentViaProgrammaticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_via_programmatic);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment blueFragment = new HahaFragment();
        ft.add(R.id.frame1, blueFragment);

        Fragment redFragment = new RedFragment();
        ft.replace(R.id.frame2, redFragment);
        ft.remove(redFragment);

        ft.commit();
    }
}
