package com.lbj.ipcexercise;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.util.Random;

public class SportService extends Service {

    private double distance = 0.0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        distance = new Random().nextDouble() * 10;
        return stub;
    }

    private final ISportManager.Stub stub = new ISportManager.Stub() {
        @Override
        public double getSportDistance() throws RemoteException {
            return distance;
        }

        @Override
        public void reset() throws RemoteException {
            distance = 0;
        }
    };
}
