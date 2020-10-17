package com.example.callbackthread;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import androidx.annotation.NonNull;

public class CalculatorHandlerThread extends HandlerThread {
    private static final String TAG = "CalculatorHandlerThread";
    Handler mHandler;
    DataCallback mCallback;

    public static final int SUM_MSG = 1;
    public static final int SUB_MSG = 2;

    CalculatorService calculatorService = new CalculatorService();

    public CalculatorHandlerThread() {
        super("CalculatorHandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
    }

    /// Result Data Callback
    public void listenResultCallBack(DataCallback callback) {
        mCallback = callback;
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case SUM_MSG:

                        int resSum = calculatorService.calculateSum(msg.arg1, msg.arg2);

                        Log.i(TAG, "SUM_MSG : " + resSum);

                        notifyCallBack(mCallback, new Result(SUM_MSG, resSum));

                        break;
                    case SUB_MSG:

                        int resSub = calculatorService.calculateSub(msg.arg1, msg.arg2);

                        Log.i(TAG, "SUB_MSG : " + resSub);

                        notifyCallBack(mCallback, new Result(SUB_MSG, resSub));

                        break;
                }
            }
        };
    }

    /// Get Handler
    public Handler getHandler() {
        return mHandler;
    }

    /// Callback Notifier
    public void notifyCallBack(DataCallback callback, Result result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onComplete(result);
                } else {
                    Log.e(TAG, "Callback is not assigned!");
                }
            }
        });
    }
}
