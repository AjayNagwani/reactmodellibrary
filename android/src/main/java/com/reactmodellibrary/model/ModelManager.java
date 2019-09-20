package com.reactmodellibrary.model;

import android.app.Activity;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.io.IOException;
import java.util.Map;

public class ModelManager extends SimpleViewManager<ModelSurfaceView> {

    private Activity activity = null;

    public ModelManager(Activity mActivity) {
        this.activity = mActivity;
    }

    @Override
    public String getName() {
        return "Model";
    }

    @Override
    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put("modelLoaded", MapBuilder.of(
                                "phasedRegistrationNames",
                                MapBuilder.of("bubbled", "onLoad"))).build();
    }

//    @ReactProp(name="reset")
//    public void resetModel(ModelTextureView view, Boolean reset) {
//        view.resetModel(reset);
//    }
//
    @ReactProp(name="reload")
    public void reloadModel(ModelSurfaceView view, String reload) {
        view.reloadModel(reload);

    }

//
//    @ReactProp(name="xPos")
//    public void setXPos(ModelTextureView view, float xPos) {
//        view.setXPos(xPos);
//
//    }
//
//    @ReactProp(name="yPos")
//    public void setYPos(ModelTextureView view, float yPos) {
//        view.setYPos(yPos);
//    }
//
//    @ReactProp(name="zPos")
//    public void setZPos(ModelTextureView view, float zPos) {
//        view.setZPos(zPos);
//    }
//
//    @ReactProp(name="xView")
//    public void setXView(ModelTextureView view, float xView) {
//
//        view.setXView(xView);
//    }
//
//    @ReactProp(name="yView")
//    public void setYView(ModelTextureView view, float yView) {
//        view.setYView(yView);
//    }
//
//    @ReactProp(name="zView")
//    public void setZView(ModelTextureView view, float zView) {
//        view.setZView(zView);
//    }
//
//    @ReactProp(name="xUp")
//    public void setXUp(ModelTextureView view, float xUp) {
//
//        view.setXUp(xUp);
//    }
//
//    @ReactProp(name="yUp")
//    public void setYUp(ModelTextureView view, float yUp) {
//        view.setXPos(yUp);
//    }
//
//    @ReactProp(name="zUp")
//    public void setZUp(ModelTextureView view, float zUp) {
//        view.setZUp(zUp);
//    }
//
//    @ReactProp(name = "injectionMode")
//    public void setInjectionMode(ModelTextureView view, Boolean injectionMode) {
//        view.setInjectionMode(injectionMode);
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @ReactProp(name = "dataPoints")
//    public void setDataPoints(ModelTextureView view, ReadableArray dataPoints) {
//        view.setDataPoints(dataPoints);
//
//    }

    @Override
    protected ModelSurfaceView createViewInstance(ThemedReactContext reactContext) {

        try {
            return new ModelSurfaceView(activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





}
