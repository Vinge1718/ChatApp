package com.google.firebase.udacity.friendlychat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MemeActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST = 1;
    private static final int RESULT_LOAD_IMAGE = 2;

    Button load, save, share, go;
    TextView textView1, textView2;
    EditText editText1, editText2;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);

        if(ContextCompat.checkSelfPermission(MemeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MemeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(MemeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else{
                ActivityCompat.requestPermissions(MemeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        }else {
            //do nothing
        }

        imageView = (ImageView) findViewById(R.id.imageView);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

        load = (Button) findViewById(R.id.load);
        save = (Button) findViewById(R.id.save);
        share = (Button) findViewById(R.id.share);
        go = (Button) findViewById(R.id.go);

        save.setEnabled(false);
        share.setEnabled(false);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView1.setText(editText1.getText().toString());
                editText1.setText("");
                textView2.setText(editText2.getText().toString());
                editText2.setText("");
            }
        });
    }
//added a method with the re
//quest parameters to the content provider and the cursor which is supposed to parse through the received data"
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int collumnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(collumnIndex);
            cursor.close();
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(MemeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        //do nothing
                    }
                }else {
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }
}
