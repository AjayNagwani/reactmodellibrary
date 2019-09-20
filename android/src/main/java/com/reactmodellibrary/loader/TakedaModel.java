package com.reactmodellibrary.loader;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.reactmodellibrary.model.ModelRenderer;
import com.reactmodellibrary.model.ModelSurfaceView;
import com.reactmodellibrary.models.DataPoint;
import com.reactmodellibrary.models.ModelColor;


import org.andresoviedo.android_3d_model_engine.model.Object3DData;
import org.andresoviedo.android_3d_model_engine.services.Object3DBuilder;
import org.andresoviedo.util.android.ContentUtils;
import org.andresoviedo.util.io.IOUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class loads a 3D scene as an example of what can be done with the app
 *
 * @author andresoviedo
 */


class InjectionPoint {

    private ModelColor bgColor;
    private ModelColor white = new ModelColor(1f, 1f, 1f, 1f);
    private ModelColor gray = new ModelColor(0.51f, 0.53f, 0.55f, 1f);

    public Object3DData innerPoint = null;
    public Object3DData outerPoint = null;

    public InjectionPoint(){
        this.bgColor = gray;
        createInjectionPoint();
    }

    public InjectionPoint(ModelColor bgColor){
        this.bgColor=bgColor;
        createInjectionPoint();
    }

    public void setBgColor(ModelColor bgColor){
        this.bgColor=bgColor;
        outerPoint.setColor(bgColor.getColorWithOpacity(0.7f));
    }

    public void createInjectionPoint(){
        innerPoint = Object3DBuilder.modelPoint();
        innerPoint.centerAndScale(0.067f);
        innerPoint.setColor(white.getColor());
        outerPoint = Object3DBuilder.modelPoint();
        outerPoint.centerAndScale(0.177f);
        outerPoint.setColor(bgColor.getColorWithOpacity(0.7f));

    }

    public void setPosition(float x, float y, float z){
        innerPoint.setPosition(new float[]{x,y,z});
        outerPoint.setPosition(new float[]{x,y,z-0.01f});
    }

    public synchronized List<Object3DData> getPoints() {
        List<Object3DData> points = new ArrayList<>();
        points.add(innerPoint);
        points.add(outerPoint);
        return points;
    }
}


public class TakedaModel extends SceneLoader {

    private CountDownTimer timer;

    public TakedaModel(ModelSurfaceView view) {
        super(view);
    }

    public ModelColor white = new ModelColor(1f, 1f, 1f, 1f);
    public ModelColor gray = new ModelColor(0.51f, 0.53f, 0.55f, 1f);
    public ModelColor primary = new ModelColor(0.00f, 0.36f, 0.26f, 1f);
    public ModelColor secondaryDark = new ModelColor(0.46f, 0.68f, 0.60f, 1f);
    public Object3DData model = null;

    // TODO: fix this warning
    @SuppressLint("StaticFieldLeak")
    public void init() {
        super.init();
        new AsyncTask<Void, Void, Void>() {

            List<Exception> errors = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    // Set up ContentUtils so referenced materials and/or textures could be find
                    ContentUtils.setThreadActivity(parent.getActivity());
                    ContentUtils.provideAssets(parent.getActivity());

                    try {
                        // this has heterogeneous faces
                        Log.e("Reached","1");
                        InputStream open = ContentUtils.getInputStream(Uri.parse("assets://assets/models/human.png"));
                        Log.e("Reached","2");
                        model = Object3DBuilder.loadV5(parent.getActivity(), Uri.parse("assets://assets/models/human.obj"));
                        Log.e("Reached","3");
                        model.setTextureData(IOUtils.read(open));
                        model.setPosition(new float[] { 0f, 0f, 0f });
                        model.setId("Human");
                        model.setScale(new float[]{5, 5, 5});
                        addObject(model);


                    } catch (Exception ex) {
                        errors.add(ex);
                    }

                } catch (Exception ex) {
                    errors.add(ex);
                } finally {
                    ContentUtils.setThreadActivity(null);
                    ContentUtils.clearDocumentsProvided();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                ModelRenderer mr = parent.getModelRenderer();
                processTouch(mr.getWidth()/2,mr.getHeight()/2,false);
                timer = new CountDownTimer(2000,
                        2000) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {
                        parent.modelLoaded();
                    }
                };
                timer.start();

                if (!errors.isEmpty()) {
                    StringBuilder msg = new StringBuilder("There was a problem loading the data");
                    for (Exception error : errors) {
                        Log.e("Example", error.getMessage(), error);
                        msg.append("\n" + error.getMessage());
                    }
                    Toast.makeText(parent.getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void reloadModel(){
       /* new AsyncTask<Void, Void, Void>() {

            List<Exception> errors = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    // Set up ContentUtils so referenced materials and/or textures could be find
                    ContentUtils.setThreadActivity(parent.getActivity());
                    ContentUtils.provideAssets(parent.getActivity());

                    try {
                        // this has heterogeneous faces
                        Log.e("Reloaded","model");
                        Log.e("Re - objects",getObjects().toString());
                        removeObject("Human");
                        Log.e("Re - objects 1 ",getObjects().toString());
                        InputStream open = ContentUtils.getInputStream(Uri.parse("assets://assets/models/human.png"));
                        model = Object3DBuilder.loadV5(parent.getActivity(), Uri.parse("assets://assets/models/human.obj"));
                        model.setTextureData(IOUtils.read(open));
                        model.setPosition(new float[] { 0f, 0f, 0f });
                        Log.e("Re - objects2",getObjects().toString());
                        model.setId("Human");
                        model.setScale(new float[]{5, 5, 5});
                        addObject(model);
                        Log.e("Re - objects3",getObjects().toString());
                    } catch (Exception ex) {
                        errors.add(ex);
                    }

                } catch (Exception ex) {
                    errors.add(ex);
                } finally {
                    ContentUtils.setThreadActivity(null);
                    ContentUtils.clearDocumentsProvided();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                Log.e("Reloaded","success");
                ModelRenderer mr = parent.getModelRenderer();
                processTouch(mr.getWidth()/2,mr.getHeight()/2,false);
                timer = new CountDownTimer(2000,
                        2000) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {
                        parent.modelLoaded();
                    }
                };
                timer.start();

                if (!errors.isEmpty()) {
                    StringBuilder msg = new StringBuilder("There was a problem loading the data");
                    for (Exception error : errors) {
                        Log.e("Example", error.getMessage(), error);
                        msg.append("\n" + error.getMessage());
                    }
                    Toast.makeText(parent.getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute();*/
      /*  Log.e("Reloaded","model");
        Log.e("Re - objects",getObjects().toString());
        removeObject("Human");
        Log.e("Re - objects 1 ",getObjects().toString());
        init();*/
    }

    @SuppressLint("StaticFieldLeak")
    public void loadDataPoints(final List<DataPoint> dataPoints){
        new AsyncTask<Void, Void, Void>() {

            List<Exception> errors = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    try {
                        //removeObject("ModelPoint");

                        dataPoints.forEach(new Consumer<DataPoint>() {
                            @Override
                            public void accept(DataPoint dataPoint) {
                                float[] location = getCordFrmLoc(dataPoint.getLocation());
                                ModelColor bgColor = getPointBg(dataPoint.getColor());
                                addInjection(location[0], location[1], location[2], bgColor);
                            }
                        });
                    } catch (Exception ex) {
                        errors.add(ex);
                    }

                } catch (Exception ex) {
                    errors.add(ex);
                } finally {

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                if (!errors.isEmpty()) {
                    StringBuilder msg = new StringBuilder("There was a problem loading the data");
                    for (Exception error : errors) {
                        Log.e("Example", error.getMessage(), error);
                        msg.append("\n" + error.getMessage());
                    }
                    Toast.makeText(parent.getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public float [] getCordFrmLoc(String location){
        float[] cord = new float[]{0,0,0};
        switch (location){
            case "leftArm":
                cord = new float[]{1.137f,1.161f,-0.041f};
                break;
            case "rightArm":
                cord = new float[]{-1.137f,1.213f,-0.05f};
                break;
            case "leftLeg":
                cord = new float[]{0.425f,-1.049f,0.395f};
                break;
            case "rightLeg":
                cord = new float[]{-0.407f,-0.405f,0.407f};
                break;
        }
        return cord;
    }

    public ModelColor getPointBg(String color){
        ModelColor modelColor = gray;
        if(color !=null){
            switch (color){
                case "secondaryDark":
                    modelColor = secondaryDark;
                    break;
                case "primary":
                    modelColor = primary;
                    break;
            }
        }
        return modelColor;
    }

    public float [] formatCoord(float x,float y ,float z){
//        float baseX=0.010f;
//        float baseY=1.369f;
//        float baseZ=0.876f;

        float baseX=0.002f;
        float baseY=0.8391f;
        float baseZ=0.115f;

        float scaleX=6.46f;
        float scaleY=6.437f;
        float scaleZ=6.617f;

        float X = baseX-((baseX -x)*scaleX);
        float Y = baseY-((baseY -y)*scaleY);
        float Z = baseZ-((baseZ -z)*scaleZ);

        return new float[]{(X+0.008f),(Y+0.5299f),(Z+0.761f)};
    }

    public float [] calibrateCoordAfterRotate(float x,float z ,float angle){

        // convert degrees to radians
        double radians = Math.toRadians(angle);

        double sinValue = Math.sin(radians);
        double cosValue = Math.cos(radians);

        float X = (float) ((x*cosValue) - (z*sinValue));
        float Y = (float) ((x*sinValue) + (z*cosValue));

        float calibration = 0.01f;
        if(Math.abs(angle) > 180) {
            calibration = -0.0055f;
        }

        return new float[]{(X),(Y+calibration)};
    }

    public float calibrateRotation(float x,float y,float z){

        float angle = (float) (-105.90261*z+89.77068);

        if(x<0.010){
            angle=-angle;
        }
        if(x<0.010&&y<-0.7401056){
            if(x>-0.80042004){
                angle=-angle;
            }
        }
        if(x>0.010&&y<-0.7401056){
            if(x<0.68798004){
                angle=-angle;
            }
        }
        if(x>1.22138&&x<1.70588&&y>1.026742){
            angle=-angle;
        }
        if(x<-1.1497001&&x>-1.8148199&&y>1.026742){
            angle=-angle;
        }
        return angle;
    }


    @SuppressLint("StaticFieldLeak")
    public  void addInjection(final float x, final float y, final float z, final ModelColor bgColor){
        new AsyncTask<Void, Void, Void>() {

            List<Exception> errors = new ArrayList<>();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    ContentUtils.setThreadActivity(parent.getActivity());
                    ContentUtils.provideAssets(parent.getActivity());

                    try {
                        InjectionPoint injectionPoint = new InjectionPoint();
                        if(bgColor!=null){
                            injectionPoint.setBgColor(bgColor);
                        }
                        injectionPoint.setPosition(x,y,z);
                        List<Object3DData> points = injectionPoint.getPoints();
                        for (int i=0; i<points.size(); i++) {
                            Object3DData pointData = points.get(i);
                            addObject(pointData);
                        }

                    } catch (Exception ex) {
                        errors.add(ex);
                    }


                } catch (Exception ex) {
                    errors.add(ex);
                } finally {
                    ContentUtils.setThreadActivity(null);
                    ContentUtils.clearDocumentsProvided();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                if (!errors.isEmpty()) {
                    StringBuilder msg = new StringBuilder("There was a problem loading injection points");
                    for (Exception error : errors) {
                        Log.e("Example", error.getMessage(), error);
                        msg.append("\n" + error.getMessage());
                    }
                    Toast.makeText(parent.getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public  void addInjection(float[] point){
        addInjection( point[0],point[1],point[2]+0.07f,secondaryDark);
    }
//    @SuppressLint("StaticFieldLeak")
//    public  void addAttack(float x, float y,final float z){
//        new AsyncTask<Void, Void, Void>() {
//
//            ProgressDialog dialog = new ProgressDialog(parent.getActivity());
//            List<Exception> errors = new ArrayList<>();
//            Boolean showAlert =false;
//            Object3DData selectedObj = null;
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
////                dialog.setCancelable(false);
////                dialog.setMessage("Loading demo...");
////                dialog.show();
//            }
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//
//                    ContentUtils.setThreadActivity(parent.getActivity());
//                    ContentUtils.provideAssets(parent.getActivity());
//
//
//                    try {
//                        Object3DData obj10 = Object3DBuilder.buildAttacker(1.1f,1f);
//                        float [] color = new float[]{0.18f, 0.65f, 0.75f, 1f};
//                        obj10.setColor(color);
//                        float Z = z;
//
//                        if(z>0){
//                            Z=z+0.06f;
//                        }else {
//                            Z=z-0.04f;
//                        }
//                        float [] tmp = new float[]{x, y,Z};
//                        obj10.setPosition(tmp);
//                        float rAngle = calibrateRotation(tmp[0],tmp[1],tmp[2]);
//                        if(y>2.57&&Z>0){
//                            rAngle=0f;
//                            Z=Z+0.07f;
//                            tmp[2]=Z;
//                        }
//                        if(y>2.57&&Z<0){
//                            rAngle=0f;
//                            Z=Z-0.07f;
//                            tmp[2]=Z;
//                        }
//                        if((x>1.1&&y<2.5&&z>-0.3)||(x<-1.1&&y<2.5&&z>-0.3)){
//                            rAngle=0f;
//                            Z=Z+0.10f;
//                            tmp[2]=Z;
//                        }
//
//                        obj10.setRotation(new float[]{0f ,rAngle,0f});
//                        float[] rCord = calibrateCoordAfterRotate(tmp[0],tmp[2],rAngle);
//                        obj10.setPosition(new float[]{rCord[0],tmp[1],rCord[1]});
//
//
//                        Object3DData obj11 = Object3DBuilder.buildAttacker(1.15f,1f);
//                        obj11.centerAndScale(0.35f);
//
//                        obj11.setColor(color);
//                        obj11.setPosition(tmp);
//                        obj11.setRotation(new float[]{0f ,rAngle,0f});
//                        obj11.setPosition(new float[]{rCord[0],tmp[1],rCord[1]});
//
//                        Object3DData obj12 = Object3DBuilder.buildAttacker(1.25f,1f);
//                        obj12.centerAndScale(0.2f);
//
//                        obj12.setColor(color);
//                        obj12.setPosition(tmp);
//                        obj12.setRotation(new float[]{0f ,rAngle,0f});
//                        obj12.setPosition(new float[]{rCord[0],tmp[1],rCord[1]});
//
//                        //Object3DData conflictObj = parent.checkConfilctingPoint(obj10.getPosition());
//                        Object3DData conflictObj=null;
//                        if(conflictObj==null){
//                            addObject(obj10);
//                            addObject(obj11);
//                            addObject(obj12);
//                        }else{
//                            showAlert = true;
//                            selectedObj = conflictObj;
//                        }
//
//
//                    } catch (Exception ex) {
//                        errors.add(ex);
//                    }
//
//
//                } catch (Exception ex) {
//                    errors.add(ex);
//                } finally {
//                    ContentUtils.setThreadActivity(null);
//                    ContentUtils.clearDocumentsProvided();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void result) {
//                super.onPostExecute(result);
//                if(showAlert){
//                    //parent.handleSelectedObj(false,selectedObj);
//                }
//
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//                if (!errors.isEmpty()) {
//                    StringBuilder msg = new StringBuilder("There was a problem loading the data");
//                    for (Exception error : errors) {
//                        Log.e("Example", error.getMessage(), error);
//                        msg.append("\n" + error.getMessage());
//                    }
//                    Toast.makeText(parent.getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//                }
//            }
//        }.execute();
//    }
}
