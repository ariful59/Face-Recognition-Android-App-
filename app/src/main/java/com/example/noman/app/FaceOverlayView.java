package com.example.noman.app;

//import android.content.Intent;
//import android.content.pm.PackageManager;
import android.graphics.Bitmap;

//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.v7.app.AppCompatActivity;


import android.content.Context;
//import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;

//import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;
public class FaceOverlayView extends View {
    // variable needed for recognition
    TextView show;
    Person person=null;
    double distanceOfEye;
    double distanceOfChin;
    double distanceOfMouth;
    double differenceOfEye;
    double differenceOfChin;
    double differenceOfMouth;
    double distanceOfNoseToEye1;
    double distanceOfNoseToEye2;
    //variable needed for recognition

    //point declaration
    double[] xValues=new double[8];
    double firstX;
    double firstY;
    double secX;
    double secY;
    double thirdX;
    double thirdY;
    double fourthX;
    double fourthY;
    double fifthX;
    double fifthY;
    double sixthX;
    double sixthY;
    double seventhX;
    double seventhY;
    double eightX;
    double eightY;

    double eyeRatio;
    double chinRatio;
    double mouthRatio;
    //point declaration

    public String emo;//nothing";
    public Bitmap mBitmap;

    private SparseArray<Face> mFaces;
    private Context applicationContext;

    public FaceOverlayView(Context context) {
        this(context, null);
    }

    public FaceOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBitmap(Bitmap bitmap) {
        show=(TextView)findViewById(R.id.textView);
        int k=0;
        mBitmap = bitmap;
        FaceDetector detector = new FaceDetector.Builder( getContext() )
                .setTrackingEnabled(true)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.ACCURATE_MODE)
                .build();

        if (!detector.isOperational()) {
            //Handle contingency
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            mFaces = detector.detect(frame);
            Log.d("rumi","Faces Found "+mFaces.size());
            for (int i = 0; i < mFaces.size(); ++i) {
                Face face = mFaces.valueAt(i);
                for (Landmark landmark : face.getLandmarks()) {
                    int cx = (int) (landmark.getPosition().x * 10);
                    int cy = (int) (landmark.getPosition().y * 10);
                    Log.d("rumi", "rouf cx= " + cx + " cy= " + cy +"\n");
                    xValues[k]=cx;
                    k++;
                }
            }
            for (int i=0;i<8;i++){
                Log.d("rumi",""+xValues[i]);
            }
            distanceOfEye=xValues[1]-xValues[0];
            distanceOfChin=xValues[3]-xValues[4];
            distanceOfMouth=xValues[5]-xValues[6];
            Log.d("distance","Distance of eye "+distanceOfEye);
            Log.d("distance", "Distance of chin " + distanceOfChin);
            Log.d("distance", "Distance of mouth " + distanceOfMouth);

            person=new Person("You are the right person ",distanceOfEye,distanceOfChin,distanceOfMouth);
            detector.release();
        }
        logFaceData();
        invalidate();
    }

    public void recognize(Bitmap bitmap) {
        //show=(TextView)findViewById(R.id.textView);
        int k=0;
        mBitmap = bitmap;
        FaceDetector detector = new FaceDetector.Builder( getContext() )
                .setTrackingEnabled(true)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.ACCURATE_MODE)
                .build();

        if (!detector.isOperational()) {
            //Handle contingency
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            mFaces = detector.detect(frame);
            Log.d("rumi","Faces Found "+mFaces.size());
            for (int i = 0; i < mFaces.size(); ++i) {
                Face face = mFaces.valueAt(i);
                for (Landmark landmark : face.getLandmarks()) {
                    int cx = (int) (landmark.getPosition().x * 10);
                    int cy = (int) (landmark.getPosition().y * 10);
                    Log.d("rumi", "rouf cx= " + cx + " cy= " + cy +"\n");
                    xValues[k]=cx;
                    k++;
                }
            }
            distanceOfEye=xValues[1]-xValues[0];
            distanceOfChin=xValues[3]-xValues[4];
            distanceOfMouth=xValues[5]-xValues[6];
            Log.d("distance","Distance of eye "+distanceOfEye);
            Log.d("distance", "Distance of chin " + distanceOfChin);
            Log.d("distance", "Distance of mouth " + distanceOfMouth);

            differenceOfEye=Math.abs(distanceOfEye-person.getDistanceOfEye());
            differenceOfChin=Math.abs(distanceOfChin-person.getDistanceOfChin());
            differenceOfMouth=Math.abs(distanceOfMouth-person.getDistanceOfMout());

            Log.d("distance","Person eye difference "+differenceOfEye);
            Log.d("distance","Person chin difference"+differenceOfChin);
            Log.d("distance","Person mouth difference"+differenceOfMouth);

            eyeRatio=person.getDistanceOfEye()/distanceOfEye;
            chinRatio=person.getDistanceOfChin()/distanceOfChin;
            mouthRatio=person.getDistanceOfMout()/distanceOfMouth;

            Log.d("distance","eye ratio "+eyeRatio);
            Log.d("distance","chin ratio "+eyeRatio);
            Log.d("distance","mouth ratio "+mouthRatio);

            if(Math.abs(distanceOfEye-person.getDistanceOfEye())<10&&Math.abs(distanceOfChin-person.getDistanceOfChin())<10&&Math.abs(distanceOfMouth-person.getDistanceOfMout())<10){
                Log.d("distance", "You are the right person");
                //show.setText(person.getNameOfPerson());
            }
            else {
                Log.d("distance","You are FUGITIVE");
                //show.setText("You are FUGUTIVE");
            }
            detector.release();
        }
        logFaceData();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if ((mBitmap != null) && (mFaces != null)) {
            double scale = drawBitmap(canvas);
            drawFaceLandmarks(canvas, scale);
        }
    }

    private double drawBitmap(Canvas canvas) {
        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);

        Rect destBounds = new Rect(0, 0, (int)(imageWidth * scale), (int)(imageHeight * scale));
        canvas.drawBitmap(mBitmap, null, destBounds, null);
        return scale;
    }

    private void drawFaceBox(Canvas canvas, double scale) {
        //This should be defined as a member variable rather than
        //being created on each onDraw request, but left here for
        //emphasis.
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        float left = 0;
        float top = 0;
        float right = 0;
        float bottom = 0;

        for( int i = 0; i < mFaces.size(); i++ ) {
            Face face = mFaces.valueAt(i);

            left = (float) ( face.getPosition().x * scale );
            top = (float) ( face.getPosition().y * scale );
            right = (float) scale * ( face.getPosition().x + face.getWidth() );
            bottom = (float) scale * ( face.getPosition().y + face.getHeight() );

            canvas.drawRect( left, top, right, bottom, paint );
        }
    }

    private void drawFaceLandmarks( Canvas canvas, double scale ) {
        Paint paint = new Paint();
        paint.setColor( Color.GREEN );
        paint.setStyle( Paint.Style.STROKE );
        paint.setStrokeWidth( 5 );

        for( int i = 0; i < mFaces.size(); i++ ) {
            Face face = mFaces.valueAt(i);

            for ( Landmark landmark : face.getLandmarks() ) {
                int cx = (int) ( landmark.getPosition().x * scale );
                int cy = (int) ( landmark.getPosition().y * scale );
                canvas.drawCircle( cx, cy, 10, paint );
            }

        }
    }

    private void logFaceData() {
        float smilingProbability=2;
        float leftEyeOpenProbability=2;
        float rightEyeOpenProbability=2;
        float eulerY;
        float eulerZ;
        for( int i = 0; i < mFaces.size(); i++ ) {
            Face face = mFaces.valueAt(i);

            if(face.getIsSmilingProbability()>=0)smilingProbability = face.getIsSmilingProbability();
            if(face.getIsLeftEyeOpenProbability()>=0)leftEyeOpenProbability = face.getIsLeftEyeOpenProbability();
            if(face.getIsRightEyeOpenProbability()>=0)rightEyeOpenProbability = face.getIsRightEyeOpenProbability();
            eulerY = face.getEulerY();
            eulerZ = face.getEulerZ();


            Log.e( "noman", "Smiling: " + smilingProbability );
            Log.e( "noman", "Left eye open: " + leftEyeOpenProbability );
            Log.e( "noman", "Right eye open: " + rightEyeOpenProbability );
            Log.e( "noman", "Euler Y: " + eulerY );
            Log.e( "noman", "Euler Z: " + eulerZ );
        }
        // if(leftEyeOpenProbability<=0.2&&rightEyeOpenProbability<=0.2)emo="sleepy";
        // else if(leftEyeOpenProbability>=0.2||rightEyeOpenProbability>=0.2)emo= "wink";
        if(smilingProbability>=.5&&smilingProbability<=1)emo="happy";
        else if(smilingProbability<0.5&&smilingProbability>=0.0001)emo="fake smile";
        else if(smilingProbability<0.0001)
        {
            emo="sad";
            emo+=String.format("%f", smilingProbability);
            emo+=String.format("%f", leftEyeOpenProbability);

        }
        else {
            emo = "nothing";
            emo += String.format("%f", smilingProbability);
            emo += String.format("%f", leftEyeOpenProbability);
        }

    }

}

