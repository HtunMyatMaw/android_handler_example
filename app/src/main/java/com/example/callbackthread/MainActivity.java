
package com.example.callbackthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.callbackthread.CalculatorHandlerThread.SUB_MSG;
import static com.example.callbackthread.CalculatorHandlerThread.SUM_MSG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    EditText edtNumOne, edtNumTwo;
    Button btnSum, btnSub;
    TextView sumResult, subResult;
    int num1 = 0, num2 = 0;

    /// Priority Sum
    CalculatorHandlerThread calculatorHandlerThreadForSum = new CalculatorHandlerThread();
    /// Priority Sub
    CalculatorHandlerThread calculatorHandlerThreadForSub = new CalculatorHandlerThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNumOne = findViewById(R.id.num_one);
        edtNumTwo = findViewById(R.id.num_two);

        sumResult = findViewById(R.id.sum_result);
        subResult = findViewById(R.id.sub_result);

        btnSum = findViewById(R.id.btn_sum);
        btnSub = findViewById(R.id.btn_sub);

        btnSum.setOnClickListener(this);
        btnSub.setOnClickListener(this);

        // use it init state
        calculatorHandlerThreadForSum.start();
        calculatorHandlerThreadForSub.start();
    }

    @Override
    public void onClick(View view) {
        if (edtNumOne.getText() != null && !edtNumOne.getText().toString().equals("")) {
            num1 = Integer.parseInt(edtNumOne.getText().toString());
        }
        if (edtNumTwo.getText() != null && !edtNumTwo.getText().toString().equals("")) {
            num2 = Integer.parseInt(edtNumTwo.getText().toString());
        }

        switch (view.getId()) {
            case R.id.btn_sum:

                /// Initialize Callback and Get Callback Data
                calculatorHandlerThreadForSum.listenResultCallBack(data -> {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "run: sum " + data.mResult);
                            int result = data.mResult;
                            sumResult.setText("SUM VALUE : " + result);
                        }
                    });
                });
                Message msgSum = Message.obtain(calculatorHandlerThreadForSum.getHandler());
                msgSum.what = SUM_MSG;
                msgSum.arg1 = num1;
                msgSum.arg2 = num2;
                msgSum.sendToTarget();

                break;
            case R.id.btn_sub:

                /// Initialize Callback and Get Callback Data
                calculatorHandlerThreadForSub.listenResultCallBack(data -> {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "run: sub " + data.mResult);
                            int result = data.mResult;
                            subResult.setText("SUB VALUE : " + result);
                        }
                    });
                });

                Message msgSub = Message.obtain(calculatorHandlerThreadForSub.getHandler());
                msgSub.what = SUB_MSG;
                msgSub.arg1 = num1;
                msgSub.arg2 = num2;
                msgSub.sendToTarget();

                break;
        }
    }
}