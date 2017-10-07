package com.example.noman.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import android.widget.TextView;

//import com.google.android.gms.vision.Frame;
//import com.google.android.gms.vision.face.Face;
//import com.google.android.gms.vision.face.FaceDetector;
//import com.google.android.gms.vision.face.Landmark;


public class MainActivity extends Activity {
    private FaceOverlayView mFaceOverlayView;
    public static final int REQUEST_CAPTURE = 1;
    public static final int REQUEST_RECOGNIZE=2;
    Button save;
    Button recog;
    //ImageView result_photo;
    public TextView text;
    public int first=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View v=null;
        mFaceOverlayView = (FaceOverlayView) findViewById(R.id.face_overlay);
        save=(Button)findViewById(R.id.button3);
        recog=(Button)findViewById(R.id.button4);
    }
    public boolean hascamera()
    {
        return  getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    public void  LaunchCameraforSave(View v)
    {

        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,REQUEST_CAPTURE);

    }
    public void  LaunchCameraforRecognize(View v)
    {

        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,REQUEST_RECOGNIZE);

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==REQUEST_CAPTURE&&resultCode==Activity.RESULT_OK)
        {
            Bundle extras=data.getExtras();
            Bitmap photo=(Bitmap)extras.get("data");
            mFaceOverlayView.setBitmap(photo);
            //Log.d("distance", "Saving called");
        }
        else if(requestCode==REQUEST_RECOGNIZE&&resultCode==Activity.RESULT_OK){
            Bundle extras=data.getExtras();
            Bitmap photo=(Bitmap)extras.get("data");
            mFaceOverlayView.recognize(photo);
            //Log.d("distance", "recognize called");
        }

    }
}
