package com.lbj.frameworkexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class FrameworkActivity extends AppCompatActivity {

    private static final String TAG = "FrameworkActivity";
    private RecyclerView mRecyclerView;
    private List<AppInfo> mAllAppInfo;
    private AppInfoAdapter mAppInfoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framework);
        initView();

        mAllAppInfo = InformationFromSys.getInstance().getAppInfoList(FrameworkActivity.this);
        mAppInfoAdapter = new AppInfoAdapter(mAllAppInfo, this);
        mRecyclerView.setAdapter(mAppInfoAdapter);

    }

    private void initView() {
        mRecyclerView = findViewById(R.id.date_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }
}