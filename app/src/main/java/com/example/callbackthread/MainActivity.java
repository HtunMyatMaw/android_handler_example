
package com.example.callbackthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.callbackthread.CalculatorHandlerThread.SUB_MSG;
import static com.example.callbackthread.CalculatorHandlerThread.SUM_MSG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtNumOne, edtNumTwo;
    Button btnSum, btnSub;

    CalculatorHandlerThread calculatorHandlerThread = new CalculatorHandlerThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNumOne = findViewById(R.id.num_one);
        edtNumTwo = findViewById(R.id.num_two);
        btnSum = findViewById(R.id.btn_sum);
        btnSub = findViewById(R.id.btn_sub);

        btnSum.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        calculatorHandlerThread.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sum:

                /// Initialize Callback and Get Callback Data
                calculatorHandlerThread.listenResultCallBack(data -> {
                    System.out.println("Action = " + data.mActionType + " , Result =  " + data.mResult);
                });
                Message msgSum = Message.obtain(calculatorHandlerThread.getHandler());
                msgSum.what = SUM_MSG;
                msgSum.arg1 = 200;
                msgSum.arg2 = 100;
                msgSum.sendToTarget();

                break;
            case R.id.btn_sub:

                /// Initialize Callback and Get Callback Data
                calculatorHandlerThread.listenResultCallBack(data -> {
                    System.out.println("Action = " + data.mActionType + " , Result =  " + data.mResult);
                });

                Message msgSub = Message.obtain(calculatorHandlerThread.getHandler());
                msgSub.what = SUB_MSG;
                msgSub.arg1 = 200;
                msgSub.arg2 = 100;
                msgSub.sendToTarget();

                break;
        }
    }
}