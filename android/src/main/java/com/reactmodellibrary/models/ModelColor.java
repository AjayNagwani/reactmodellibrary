package com.reactmodellibrary.models;

public class ModelColor {
    private float r;
    private float b;
    private float g;
    private float a;
    public ModelColor(float r,float b,float g,float a){
        this.r=r;
        this.b=b;
        this.g=g;
        this.a=a;
    }

    public float[] getColor(){
        return new float[]{r,b,g,a};
    }

    public float[] getColorWithOpacity(float a){
        return new float[]{r,b,g,a};
    }
}
