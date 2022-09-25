package com.lbj.frameworkexercise;

import android.graphics.drawable.Drawable;

public class AppInfo {

    public String label;
    public String packageName;
    public Drawable icon;
    public boolean isSystem;

    public AppInfo() {
    }

    public AppInfo(String label, String packageName, Drawable icon, boolean isSystem) {
        this.label = label;
        this.packageName = packageName;
        this.icon = icon;
        this.isSystem = isSystem;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }
}
