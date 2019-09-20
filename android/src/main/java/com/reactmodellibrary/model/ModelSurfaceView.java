package com.reactmodellibrary.model;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.reactmodellibrary.controller.TouchController;
import com.reactmodellibrary.loader.SceneLoader;
import com.reactmodellibrary.loader.TakedaModel;
import com.reactmodellibrary.models.DataPoint;

import org.andresoviedo.util.android.AndroidURLStreamHandlerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.os.Looper.getMainLooper;

/**
 * This is the actual opengl view. From here we can detect touch gestures for example
 * 
 * @author andresoviedo
 *
 */
public class ModelSurfaceView extends GLSurfaceView {

	static {
		System.setProperty("java.protocol.handler.pkgs", "org.andresoviedo.util.android");
		URL.setURLStreamHandlerFactory(new AndroidURLStreamHandlerFactory());
	}

	private ModelRenderer mRenderer;
	private TouchController touchHandler;
	public Activity mActivity;

	private TakedaModel scene;
	public Boolean injectionMode = true;

	private List<DataPoint> dataPoints = new ArrayList<>();
	private Handler handler;

	private float[] backgroundColor = new float[]{0.98f, 0.98f, 0.98f, 1.0f};

	public ModelSurfaceView(Activity activity) throws IllegalAccessException, IOException {
		super(activity);

		// parent component
		this.mActivity = activity;

		handler = new Handler(getMainLooper());
		Log.e("Reached","Takeda Model");
		scene = new TakedaModel(this);
		scene.init();

		// Create an OpenGL ES 2.0 context.
		setEGLContextClientVersion(2);

		// This is the actual renderer of the 3D space
		mRenderer = new ModelRenderer(this);
		setRenderer(mRenderer);

		// Render the view only when there is a change in the drawing data
		// TODO: enable this?
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		//setAlpha(0.80f);

		touchHandler = new TouchController(this, mRenderer);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return touchHandler.onTouchEvent(event);
	}

	public Activity getActivity() {
		return mActivity;
	}

	public ModelRenderer getModelRenderer(){
		return mRenderer;
	}

	public float[] getBackgroundColor() {
		return backgroundColor;
	}

	public void modelLoaded(){
		WritableMap event = Arguments.createMap();
		event.putBoolean("modelLoaded", true);
		ReactContext reactContext = (ReactContext)getContext();
		reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
				getId(),
				"modelLoaded",
				event);

	}

//	public void resetModel(Boolean reset){
//		scene.getCamera().resetCamera();
//	}
//
	public void reloadModel(String reload) {
		if(!reload.equals("firstLoad")){
			scene.reloadModel();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		this.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		this.onResume();
	}
//
//	public void setXPos(float xPos) {
//
//		scene.getCamera().updateCameraInitial(xPos,0,0,0,0,0,0,0,0);
//	}
//
//	public void setYPos(float yPos) {
//		scene.getCamera().updateCameraInitial(0,yPos,0,0,0,0,0,0,0);
//	}
//
//	public void setZPos( float zPos) {
//		scene.getCamera().updateCameraInitial(0,0,zPos,0,0,0,0,0,0);
//	}
//
//	public void setXView( float xView) {
//
//		scene.getCamera().updateCameraInitial(0,0,0,xView,0,0,0,0,0);
//	}
//
//	public void setYView( float yView) {
//		scene.getCamera().updateCameraInitial(0,0,0,0,yView,0,0,0,0);
//	}
//
//	public void setZView( float zView) {
//		scene.getCamera().updateCameraInitial(0,0,0,0,0,zView,0,0,0);
//	}
//
//	public void setXUp(float xUp) {
//
//		scene.getCamera().updateCameraInitial(0,0,0,0,0,0,xUp,0,0);
//	}
//
//	public void setYUp( float yUp) {
//		scene.getCamera().updateCameraInitial(0,0,0,0,0,0,0,yUp,0);
//	}
//
//	public void setZUp( float zUp) {
//		scene.getCamera().updateCameraInitial(0,0,0,0,0,0,0,0,zUp);
//	}
//
//	public void setInjectionMode(Boolean injectionMode) {
//		this.injectionMode = injectionMode;
//	}
//
//	@RequiresApi(api = Build.VERSION_CODES.N)
//	public void setDataPoints( ReadableArray dataPoints) {
//		List<DataPoint> mappedPoints = new ArrayList<>();
//		for (Integer i=0;i<dataPoints.size();i++){
//			ReadableMap dataPoint = dataPoints.getMap(i);
//			DataPoint point = new DataPoint(
//					dataPoint.getString("location"),
//					dataPoint.getString("color")
//			);
//			mappedPoints.add(point);
//		}
//
//		this.dataPoints = mappedPoints;
//
//		//scene.loadDataPoints(this.dataPoints);
//
//	}

	public SceneLoader getScene() { return  scene; }
	public TakedaModel getModelScene(){return  scene;}
	public List<DataPoint> getDataPoints(){ return dataPoints;}


}