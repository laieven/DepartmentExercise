package com.lbj.ipcexercise;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IpcActivity extends AppCompatActivity {

    private TextView mDistanceTV;
    private Button mResetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        initView();

        Intent intent = new Intent(this, SportService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);

    }

    private void initView() {
        mResetBtn = findViewById(R.id.reset_btn);
        mDistanceTV = findViewById(R.id.distance_tv);
    }

    private ISportManager sportManager;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sportManager = ISportManager.Stub.asInterface(service);
            try {
                mDistanceTV.setText("" + sportManager.getSportDistance());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            onClickListener();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void onClickListener() {
        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sportManager.reset();
                    mDistanceTV.setText("" + sportManager.getSportDistance());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}