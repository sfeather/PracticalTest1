package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private Random random = new Random();

    private double geometricMean;
    private double arithmeticMean;

    public ProcessingThread(Context context, int value1, int value2) {
        this.context = context;

        this.arithmeticMean = (double) (value1 + value2) / 2;
        this.geometricMean = (double) Math.sqrt(value1 * value2);
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }

        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        Random rand = new Random();
        intent.setAction(Constants.actionTypes[rand.nextInt(Constants.actionTypes.length)]);
        intent.putExtra("ro.pub.cs.systems.eim.practicaltest01.message",
                new Date(System.currentTimeMillis()) + " " + arithmeticMean + " " + geometricMean);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}