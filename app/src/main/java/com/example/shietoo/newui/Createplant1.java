package com.example.shietoo.newui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Createplant1 extends AppCompatActivity {
    private Button CreatPlant1_next, CreatPlant1_back;
    private EditText CreatPlant1_name, CreatPlant1_note;
    private ImageView  CreatPlant1_img;
    private TextView dltimg2, CreatPlant1_birthday;
    private Bitmap bitmapCamera;
    private DbBitmapUtility dbu;
    private MyApp myApp;
    private SQLiteDatabase db;
    private MyDBHelper myDBHelper;
    private Button camera;

    int myyear, mymonth, myday;
    Calendar cal = Calendar.getInstance();
    DatePickerDialog dpd;
    private String datedown;

    static final int CAMERA = 0;
    static final int PHOTO = 1;
    static final int PIC_DROP = 2;

    //private Uri picUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createplant1);

        myyear = cal.get(Calendar.YEAR);
        mymonth = cal.get(Calendar.MONTH);
        myday = cal.get(Calendar.DAY_OF_MONTH);

        myApp = (MyApp) getApplication();
        myDBHelper = new MyDBHelper(this, "plantData", null, 2);
        db = myDBHelper.getReadableDatabase();

        CreatPlant1_next = (Button) findViewById(R.id.CreatPlant1_next);
        CreatPlant1_back = (Button) findViewById(R.id.CreatPlant1_back);
        CreatPlant1_name = (EditText) findViewById(R.id.CreatPlant1_name);
        CreatPlant1_birthday = (TextView) findViewById(R.id.CreatPlant1_birthday);
        CreatPlant1_note = (EditText) findViewById(R.id.CreatPlant1_note);
        camera = (Button) findViewById(R.id.camera);
        CreatPlant1_img = (ImageView) findViewById(R.id.CreatPlant1_img);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //開起照相機
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);
            }
        });


        CreatPlant1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CreatPlant1_name.getText().toString().trim().equals("")) {
                    CreatPlant1_name.setError("請給您的植物命名～");
                    CreatPlant1_name.setHint("請給您的植物命名～");
                } else {

                    if (myApp.uri != null) {
                        //byte[] buf = dbu.getJPEGBytes(myApp.createbmp);
                        ContentValues cv = new ContentValues();
                        //ContentValues的結構跟map非常類似
                        cv.put("name", CreatPlant1_name.getText().toString());
                        cv.put("birthday", CreatPlant1_birthday.getText().toString());
                        cv.put("note", CreatPlant1_note.getText().toString());
                        cv.put("image", myApp.uri);
                        db.update("plant1", cv, "id = \"" + myApp.plantID + "\"", null);
                        //第二欄不需要處理
                        Log.i("aaaa", myApp.plantID + "");
                    } else {
//                        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Pictures/img/plant.png");
//                        byte[] buf = dbu.getPNGBytes(bitmap);
                        ContentValues cv = new ContentValues();
                        //ContentValues的結構跟map非常類似
                        cv.put("name", CreatPlant1_name.getText().toString());
                        cv.put("birthday", CreatPlant1_birthday.getText().toString());
                        cv.put("note", CreatPlant1_note.getText().toString());
                        cv.put("image", "");

                        db.update("plant1", cv, "id = \"" + myApp.plantID + "\"", null);
                    }

                    new android.support.v7.app.AlertDialog.Builder(Createplant1.this).setTitle("Congratulation!!!")
                            .setMessage("創建成功~>.^~y")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(Createplant1.this, BradGarden.class);
                                    startActivity(intent);
                                    myApp.uri = null;
                                    finish();
                                }
                            }).show();

                }

            }
        });

        CreatPlant1_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Createplant1.this, BradGarden.class);
                startActivity(intent);
            }
        });


        CreatPlant1_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dodatepicker();
            }
        });
        init();
    }

    private void init() {

        switch (myApp.plantID) {
            case 1:
                getPlantData("1");
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

        if (resultCode == RESULT_OK && requestCode == CAMERA) {
//            String sdStatus = Environment.getExternalStorageState();
//            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
//                Log.v("TestFile",
//                        "SD card is not avaiable/writeable right now.");
//                return;
//            }

            //裁切成1:1
//        if (requestCode == CAMERA){
//            picUri = data.getData();
//            performCrop();
//        }else if (requestCode == PIC_DROP){
//            Bundle extras = data.getExtras();
////get the cropped bitmap
//            bitmapCamera = extras.getParcelable("data");
//            if (bitmapCamera != null){
//            CreatPlant1_img.setImageBitmap(bitmapCamera);
//        }
//        }

            Uri uri = data.getData();
            myApp.uri = uri.toString();
//            Bundle bundle = data.getExtras();
//            bitmapCamera = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
//            myApp.createbmp = bitmapCamera;
            CreatPlant1_img.setImageURI(uri);
//            FileOutputStream b = null;
//            File file = new File("/sdcard/Pictures/img/");
//            file.mkdirs();// 创建文件夹
//            String fileName = "/sdcard/Pictures/img/plant.jpg";
//            try {
//                b = new FileOutputStream(fileName);
//                bitmapCamera.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    b.flush();
//                    b.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }else {
            myApp.uri ="";
        }
            super.onActivityResult(requestCode, resultCode, data);

    }

    private void dodatepicker() {

        dpd = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        //Do something with the date chosen by the user
                        datedown = year + "-" + (month + 1) + "-" + day;
                        CreatPlant1_birthday.setText(datedown);
                    }
                }, myyear, mymonth, myday);
        dpd.show();
    }

    private void getPlantData(String position) {
        Log.i("aaaa", "getPlatData");

//        Cursor c = db.query("plant1",null,"id=?",new String[]{position},null,null,null);
        Cursor c = db.query("plant1", null, "id=?", new String[]{position}, null, null, null);

        while (c.moveToNext()) {
            CreatPlant1_name.setText(c.getString(c.getColumnIndex("name")));
            CreatPlant1_birthday.setText(c.getString(c.getColumnIndex("birthday")));
            CreatPlant1_note.setText(c.getString(c.getColumnIndex("note")));
            CreatPlant1_img.setImageURI(Uri.parse((c.getString(c.getColumnIndex("image")))));
            Log.i("aaaa", "getPlantData OK");
            c.close();
        }
    }

//    private void dialog1(){
//        final String[] dinner = {"","雞蛋糕","沙威瑪","澳美客","麵線","麵疙瘩"};
//
//        AlertDialog.Builder dialog_list = new AlertDialog.Builder(MainActivity.this);
//        dialog_list.setTitle("利用List呈現");
//        dialog_list.setItems(dinner, new DialogInterface.OnClickListener(){
//            @Override
//
//            //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
//            public void onClick(DialogInterface dialog, int which) {
//                switch (dinner[which]){
//                    case
//                }
//
//                // TODO Auto-generated method stub
//                Toast.makeText(BradGarden.this, "你選的是" + dinner[which], Toast.LENGTH_SHORT).show();
//            }
//        });
//        dialog_list.show();
//    }
//    private void performCrop(){
//        Intent cropIntent = new Intent("com.android.camera.action.CROP");
//        //indicate image type and Uri
//        cropIntent.setDataAndType(picUri, "image/*");
//        //set crop properties
//        cropIntent.putExtra("crop", "true");
//        //indicate aspect of desired crop
//        cropIntent.putExtra("aspectX", 1);
//        cropIntent.putExtra("aspectY", 1);
//        //indicate output X and Y
//        cropIntent.putExtra("outputX", 256);
//        cropIntent.putExtra("outputY", 256);
//        //retrieve data on return
//        cropIntent.putExtra("return-data", true);
//        //start the activity - we handle returning in onActivityResult
//        startActivityForResult(cropIntent, PIC_DROP);
//
//    }
//

}
