package com.lbj.frameworkexercise;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class InformationFromSys {

    private static InformationFromSys information = new InformationFromSys();

    public static InformationFromSys getInstance(){
        return information;
    }

    public List<AppInfo> getAppInfoList(Context context){
        ArrayList<AppInfo> lists = new ArrayList<>(100);
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for(int i = 0; i < installedPackages.size(); i++){

            PackageInfo packageInfo = installedPackages.get(i);

            AppInfo appInfo = new AppInfo();

            appInfo.setLabel(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
            appInfo.setPackageName(packageInfo.packageName); //获取应用包名，可用于卸载和启动应用
            appInfo.setIcon(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));//获取应用图标
            appInfo.setSystem((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0);


            lists.add(appInfo);
        }
        return lists;
    }
}
