// ISportManager.aidl
package com.lbj.ipcexercise;



interface ISportManager {

    //获取当日运动距离
    double getSportDistance();

    //重置
    void reset();

}