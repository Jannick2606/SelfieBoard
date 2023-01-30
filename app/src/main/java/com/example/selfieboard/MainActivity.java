package com.example.selfieboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

/**
 * This activity is the first one that gets loaded in the application
 * @author Jannick
 */
public class MainActivity extends AppCompatActivity {
Button takePictureButton;
Button deleteAllButton;
RelativeLayout rlMain;
StorageManager sm;
ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
float x, y;
float dx, dy;

ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {

            //After a photo has been taken this method is called
            //so you can get the photo that was captured with the camera
            //It calls the createImageView and saveToExternalStorage methods
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                Bitmap photo = (Bitmap) intent.getExtras().get("data");
                createImageView(photo);
                sm.saveToExternalStorage(photo);
            }
        }
);

    /**
     * This method is called on startup
     * It sets the values for the neccesary attributes
     * This is also where the buttons get their onClickListeners
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePictureButton = findViewById(R.id.newSelfieButton);
        deleteAllButton = findViewById(R.id.removeAllImgButton);
        rlMain = findViewById(R.id.rlMain);
        sm = new StorageManager(this);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
        //listens for clicks on the takePictureButton
        //When clicked it opens the camera
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityLauncher.launch(cameraIntent);
            }
        });
        //Listens for clicks on the deleteAllButton
        //When clicked it removes the imageviews
        //and calls the deleteFromExternalStorage method
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ImageView view : imageViews){
                    rlMain.removeView(view);
                }
                imageViews.clear();
                sm.deleteFromExternalStorage();
            }
        });
    }

    /**
     * This method is used to create an ImageView after a photo is taken
     * @param photo
     */
    private void createImageView(Bitmap photo) {

        ImageView newImageView = new ImageView(MainActivity.this);
        newImageView.setImageBitmap(photo);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                500,
                500
        );
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        newImageView.setLayoutParams(params);
        rlMain.addView(newImageView);
        imageViews.add(newImageView);
        newImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    x = motionEvent.getRawX();
                    y = motionEvent.getRawY();
                }
                if (motionEvent.getAction() == motionEvent.ACTION_MOVE) {
                    dx = motionEvent.getRawX() - x;
                    dy = motionEvent.getRawY() - y;
                    view.setX(view.getX() + dx);
                    view.setY(view.getY() + dy);
                    x = motionEvent.getRawX();
                    y = motionEvent.getRawY();
                }
                return true;
            }
        });
    }
}