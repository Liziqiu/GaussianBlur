package com.gdut.gaussianblur;

import com.gdut.graphics.AsyncGraphicsTask;
import com.gdut.graphics.GaussianBlur;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity{

 private GaussianBlur blur;
 private ImageView pic;
 private TextView tv;
 private SeekBar seekbar;
 private AsyncGraphicsTask task;
 private float radius=0f;
 private Bitmap handleView,handledondView;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);
 tv = (TextView) this.findViewById(R.id.tv);
 pic= (ImageView) this.findViewById(R.id.iv);
 seekbar = (SeekBar) this.findViewById(R.id.seekBar1);
 blur = new GaussianBlur(radius,0.8f,this);
 task = new AsyncGraphicsTask(){

 @Override
 public void done() {
 Log.d("zhiqiang", "done");
 if(radius == 1){
 pic.setImageBitmap(handleView);
 }else{
 pic.setImageBitmap(handledondView);
 }
 }

 @Override
 public boolean IsChanged() {
 if(blur.getRadius() == radius){
 Log.d("zhiqiang", "IsChanged:false");
 return false;
 }
 blur.setRadius(radius);
 Log.d("zhiqiang", "IsChanged:true");
 return true;
 }

 @Override
 public void process() {
 Log.d("zhiqiang", "process");
 handledondView = blur.androidblur(handleView);
 }

 };
 seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

 @Override
 public void onProgressChanged(SeekBar s, int position, boolean forUser) {
 //Log.d("zhiqiang", "onProgressChanged:"+position);
 radius = 1+position/10;
 /*blur.setRadius(radius);
 handledondView = blur.androidblur(handleView);
 if(radius == 1){
 pic.setImageBitmap(handleView);
 }else{
 pic.setImageBitmap(handledondView);
 }*/
 }

 @Override
 public void onStartTrackingTouch(SeekBar s) {
 Log.d("zhiqiang", "onStartTrackingTouch");
 task.SetTaskStart(true);
 }

 @Override
 public void onStopTrackingTouch(SeekBar s) {
 Log.d("zhiqiang", "onStopTrackingTouch");
 task.SetTaskStart(false);
 }

 });
 }

 private Bitmap getBitmapByView(ImageView view) {
 view.setDrawingCacheEnabled(true);
 Bitmap result = Bitmap.createBitmap(view.getDrawingCache());
 Log.d("zhiqiang", "result.getWidth:"+result.getWidth());
 Log.d("zhiqiang", "result.getHeight:"+result.getHeight());
 view.setDrawingCacheEnabled(false);
 return result;
 }

 @Override
 protected void onResume() {
 super.onResume();
 Log.d("zhiqiang", "onResume");
 }

 @Override
 protected void onPause() {
 // TODO Auto-generated method stub
 super.onPause();
 Log.d("zhiqiang", "onPause");
 }

 @Override
 protected void onStop() {
 super.onStop();
 }

 @Override
 public void onWindowFocusChanged(boolean hasFocus) {
 // TODO Auto-generated method stub
 super.onWindowFocusChanged(hasFocus);
 if(handleView == null){
 handleView = getBitmapByView(pic);
 }
 Log.d("zhiqiang", "onWindowFocusChanged");
 }

}
