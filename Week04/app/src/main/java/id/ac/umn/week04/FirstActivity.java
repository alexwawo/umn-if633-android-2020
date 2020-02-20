package id.ac.umn.week04;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    EditText etFAData;
    TextView tvDataFromSA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        etFAData = findViewById(R.id.etFirstActivityData);
        tvDataFromSA = findViewById(R.id.tvDataFromSA);
    }

    public void sendDataToSecondActivity(View view) {
        Intent i = new Intent(FirstActivity.this, SecondActivity.class);
        String faData = etFAData.getText().toString();
        i.putExtra("DataFromFA", faData);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String dataFromSA = data.getStringExtra("DataFromSA");
                tvDataFromSA.setText(dataFromSA);
            }
        }
    }
}
