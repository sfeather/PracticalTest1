package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private Button pressMeButton1;
    private Button pressMeButton2;
    private Button navigateToSecondaryActivityButton;

    private EditText pressMeText1;
    private EditText pressMeText2;

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[MessageBroadcastReceiver]", intent.getStringExtra("ro.pub.cs.systems.eim.practicaltest01.message"));
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

        pressMeButton1 = (Button)findViewById(R.id.press_me_button1);
        pressMeButton2 = (Button)findViewById(R.id.press_me_button2);

        navigateToSecondaryActivityButton = (Button)findViewById(R.id.navigate_to_secondary_activity_button);

        pressMeText1 = (EditText)findViewById(R.id.press_me_text1);
        pressMeText2 = (EditText)findViewById(R.id.press_me_text2);

        pressMeButton1.setOnClickListener(it -> {
            int number = Integer.parseInt(pressMeText1.getText().toString());
            pressMeText1.setText(Integer.toString(number + 1));
            startServicePracticalTest1();
        });

        pressMeButton2.setOnClickListener(it -> {
            int number = Integer.parseInt(pressMeText2.getText().toString());
            pressMeText2.setText(Integer.toString(number + 1));
            startServicePracticalTest1();
        });

        navigateToSecondaryActivityButton.setOnClickListener(it -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
            intent.putExtra("press_me_text1", pressMeText1.getText().toString());
            intent.putExtra("press_me_text2", pressMeText2.getText().toString());
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("press_me_text1", pressMeText1.getText().toString());
        savedInstanceState.putString("press_me_text2", pressMeText2.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey("press_me_text1")) {
            pressMeText1.setText(savedInstanceState.getString("press_me_text1"));
        }

        if (savedInstanceState.containsKey("press_me_text2")) {
            pressMeText2.setText(savedInstanceState.getString("press_me_text2"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK)
                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            else if (resultCode == RESULT_CANCELED)
                Toast.makeText(this, "CANCEL", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "UNKNOWN", Toast.LENGTH_LONG).show();
        }
    }

    private void startServicePracticalTest1() {
        int value1 = Integer.parseInt(pressMeText1.getText().toString());
        int value2 = Integer.parseInt(pressMeText2.getText().toString());

        int sum = value1 + value2;

        if (sum > Constants.THRESHOLD) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
            intent.putExtra("text1", value1);
            intent.putExtra("text2", value2);

            getApplicationContext().startService(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        System.out.println("DESTROY");
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
        getApplicationContext().stopService(intent);
    }
}