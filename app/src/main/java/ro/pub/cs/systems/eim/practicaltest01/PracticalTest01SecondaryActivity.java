package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    private TextView sumTextView;

    private Button okButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        sumTextView = (TextView)findViewById(R.id.sum_text_view);

        okButton = (Button)findViewById(R.id.ok_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int text1 = Integer.parseInt(extras.getString("press_me_text1"));
            int text2 = Integer.parseInt(extras.getString("press_me_text2"));

            int sum = text1 + text2;

            sumTextView.setText(Integer.toString(sum));
        }

        okButton.setOnClickListener(it -> {
            setResult(RESULT_OK, null);
            finish();
        });

        cancelButton.setOnClickListener(it -> {
            setResult(RESULT_CANCELED, null);
            finish();
        });
    }
}