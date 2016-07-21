package com.example.shietoo.newui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Plantprofile2 extends AppCompatActivity {
    private ImageView img;
    private EditText profileNameET,profileNoteET;
    private Button PlantFile2_back;
    private SQLiteDatabase db;
    private MyDBHelper myDBHelper;
    private DbBitmapUtility dbu;
    private MyApp myApp;
    private TextView profikeBirthET;
    int myyear,mymonth,myday;
    Calendar cal=Calendar.getInstance();
    DatePickerDialog dpd;
    private String datedown;
    Bitmap bitmapcamera;
    private Button PlantFile2_camera;
    static final int CAMERA=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantprofile2);
        profileNameET = (EditText) findViewById(R.id.PlantFile2_name);
        profikeBirthET = (TextView) findViewById(R.id.PlantFile2_birthday);
        profileNoteET = (EditText) findViewById(R.id.PlantFile2_note);
        PlantFile2_camera = (Button) findViewById(R.id.PlantFile2_camera);
        img = (ImageView) findViewById(R.id.img);

        myDBHelper = new MyDBHelper(this, "plantData", null,2);
        db = myDBHelper.getReadableDatabase();
        myApp = (MyApp)getApplication();

        myyear = cal.get(Calendar.YEAR);
        mymonth = cal.get(Calendar.MONTH);
        myday = cal.get(Calendar.DAY_OF_MONTH);


        PlantFile2_back = (Button) findViewById(R.id.PlantFile2_back);
        PlantFile2_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Plantprofile2.this,Plantprofile1.class);
                startActivity(it);
                finish();       //讓此頁死亡
            }
        });
        PlantFile2_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //開起照相機
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);
            }
        });

        profikeBirthET.setText("Set Date");
        profikeBirthET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dodatepicker();
            }
        });

        init();
    }
    private void init(){
        switch (myApp.plantID){
            case 1:
                getPlantData("1");
                Log.i("aaaa","plantprofile if1:");
                break;
            case 2:
                getPlantData("2");
                break;
            case 3:
                getPlantData("3");
                break;
            case 4:
                getPlantData("4");
                break;
//            case 5:
//                getPlantData("5");
//                break;
//            case 6:
//                getPlantData("6");
//                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode ==CAMERA) {
//            String sdStatus = Environment.getExternalStorageState();
//            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
//                Log.v("TestFile",
//                        "SD card is not avaiable/writeable right now.");
//                return;
//            }
            Uri uricreate = data.getData();
            myApp.uricreate = uricreate.toString();
//            Bundle bundle = data.getExtras();
//            Bitmap bitmapcamera = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
//            FileOutputStream b = null;
//            File file = new File("/sdcard/Pictures/img/");
//            file.mkdirs();// 创建文件夹
//            String fileName = "/sdcard/Pictures/img/plant.jpg";
//            try {
//                b = new FileOutputStream(fileName);
//                bitmapcamera.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    b.flush();
//                    b.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }else {
            myApp.uricreate ="";
        }
            ((ImageView) findViewById(R.id.img)).setImageURI(Uri.parse(myApp.uricreate));// 将图片显示在ImageView里
        }


    public void profilesave(View view) {

        if (profileNameET.getText().toString().trim().equals("")) {
            profileNameET.setError("請給您的植物命名～");
            profileNameET.setHint("請給您的植物命名～");
        } else {

            if (myApp.uricreate != null) {
                ContentValues cv = new ContentValues();
                //ContentValues的結構跟map非常類似
                cv.put("name", profileNameET.getText().toString());
                cv.put("birthday", profikeBirthET.getText().toString());
                cv.put("note", profileNoteET.getText().toString());
                cv.put("image", myApp.uricreate);
                //update last id
                db.update("plant1", cv, "id = \"" + myApp.plantID + "\"", null);

            } else {
                ContentValues cv = new ContentValues();
                //ContentValues的結構跟map非常類似
                cv.put("name", profileNameET.getText().toString());
                cv.put("birthday", profikeBirthET.getText().toString());
                cv.put("note", profileNoteET.getText().toString());
                db.update("plant1", cv, "id = \"" + myApp.plantID + "\"", null);
            }
            new AlertDialog.Builder(Plantprofile2.this).setTitle("Congratulation!!!")
                    .setMessage("修改成功~>.^~y")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(Plantprofile2.this, PlantMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();


        }
    }
    private void  getPlantData(String position){
        Log.i("aaaa","getPlatData");

//        Cursor c = db.query("plant1",null,"id=?",new String[]{position},null,null,null);
        Cursor c = db.query("plant1",null,"id=?",new String[]{position},null,null,null);

        while (c.moveToNext()){
            profileNameET.setText(c.getString(c.getColumnIndex("name")));
            profikeBirthET.setText(c.getString(c.getColumnIndex("birthday")));
            profileNoteET.setText(c.getString(c.getColumnIndex("note")));
            img.setImageURI(Uri.parse(c.getString(c.getColumnIndex("image"))));
            Log.i("aaaa","getPlantData OK");
            c.close();

        }

    }
    private void dodatepicker(){

        dpd = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        //Do something with the date chosen by the user
                        datedown =year+"-"+(month+1)+"-"+day;
                        profikeBirthET.setText(datedown);     }
                },myyear,mymonth,myday);
        dpd.show();
    }
}
