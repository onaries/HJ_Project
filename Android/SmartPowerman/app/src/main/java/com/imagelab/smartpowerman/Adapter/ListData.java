package com.imagelab.smartpowerman.Adapter;

/**
 * Created by ksw89 on 2016-11-29.
 */

public class ListData{

    private int custom_img;
    private int custom_txt;
    private int custom_switch;
    private int custom_img2;

    public ListData(int custom_img, int custom_txt) {
        this.custom_img = custom_img;
        this.custom_txt = custom_txt;
    }

    public ListData(int custom_img, int custom_txt, int custom_switch) {
        this.custom_img = custom_img;
        this.custom_txt = custom_txt;
        this.custom_switch = custom_switch;
    }

    public ListData(int custom_img, int custom_txt, int custom_switch, int custom_img2) {
        this.custom_img = custom_img;
        this.custom_txt = custom_txt;
        this.custom_switch = custom_switch;
        this.custom_img2 = custom_img2;
    }

    public int getCustom_img() {
        return custom_img;
    }

    public int getCustom_txt() {
        return custom_txt;
    }

    public int getCustom_switch() {
        return custom_switch;
    }

    public void setCustom_switch(int custom_switch) {
        this.custom_switch = custom_switch;
    }

    public void setCustom_img(int custom_img) {
        this.custom_img = custom_img;
    }

    public void setCustom_txt(int custom_txt) {
        this.custom_txt = custom_txt;
    }

    public int getCustom_img2() {
        return custom_img2;
    }

    public void setCustom_img2(int custom_img2) {
        this.custom_img2 = custom_img2;
    }
}
