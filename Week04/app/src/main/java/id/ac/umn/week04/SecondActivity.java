package id.ac.umn.week04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView tvDataFromFA;
    EditText etSAData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvDataFromFA = findViewById(R.id.tvDataFromFA);
        etSAData = findViewById(R.id.etSecondActivityData);

        Intent getI = getIntent();
        String dataFromFA = getI.getStringExtra("DataFromFA");
        tvDataFromFA.setText(dataFromFA);
    }

    public void sendDataBackToFirstActivity(View view) {
        Log.d("DEBUG", "test");
        String saData = etSAData.getText().toString();

        Intent i = new Intent();

        i.putExtra("DataFromSA", saData);

        setResult(RESULT_OK, i);
        finish();
    }
}
