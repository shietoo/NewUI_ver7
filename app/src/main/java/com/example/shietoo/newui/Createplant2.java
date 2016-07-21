package com.example.shietoo.newui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

public class Createplant2 extends AppCompatActivity {
    private Button CreatPlant2_edit,CreatPlant2_save;
    private TextView CreateName,CreateBirthday,CreateNote;
    private ImageView img;
    private SQLiteDatabase db;
    private MyDBHelper myDBHelper;
    private MyApp myApp;
    private DbBitmapUtility dbu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createplant2);

        CreateName = (TextView) findViewById(R.id.tv_CreateName);
        CreateBirthday = (TextView) findViewById(R.id.tv_CreateBirthday);
        CreateNote = (TextView) findViewById(R.id.tv_CreateNote);
        img = (ImageView) findViewById(R.id.img);

        myApp =(MyApp) getApplication();
        myDBHelper = new MyDBHelper(this, "plantData", null,2);
        db = myDBHelper.getReadableDatabase();


        //傳直傳圖回去上一頁
        CreatPlant2_edit = (Button) findViewById(R.id.CreatPlant2_edit);
        CreatPlant2_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Createplant2.this,Createplant1.class);
                startActivity(intent);
                finish();       //讓此頁死亡
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
//        case 5:
//            getPlantData("5");
//            break;
//        case 6:
//            getPlantData("6");
//            break;

    }

}

    public void createSave(View view){
        if(!(CreateName.getText().toString().trim().length()>0)) {
            Toast.makeText(Createplant2.this, "請給您的植物命名～", Toast.LENGTH_SHORT).show();
            Log.i("aaaa", "setEnable???");
            CreateName.setEnabled(false);
        }else {

            Log.i("aaa", "createSave(View view)");

//            if (myApp.createbmp !=null) {
//
//                byte[] buf = dbu.getJPEGBytes(myApp.createbmp);
//                ContentValues cv = new ContentValues();
//                //ContentValues的結構跟map非常類似
//                cv.put("name", CreateName.getText().toString());
//                cv.put("birthday", CreateBirthday.getText().toString());
//                cv.put("note", CreateNote.getText().toString());
//                cv.put("image", buf);
//
//                db.update("plant1", cv, "id = \"" + myApp.plantID + "\"", null);
//                //第二欄不需要處理
//                Log.i("aaa", myApp.plantID + "");
//
//            }else{
//                Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Pictures/img/plant.jpg");
//                byte[] buf = dbu.getJPEGBytes(bitmap);
//                ContentValues cv = new ContentValues();
//                //ContentValues的結構跟map非常類似
//                cv.put("name", CreateName.getText().toString());
//                cv.put("birthday", CreateBirthday.getText().toString());
//                cv.put("note", CreateNote.getText().toString());
//                cv.put("image", buf);
//
//                db.update("plant1", cv, "id = \"" + myApp.plantID + "\"", null);
//            }
            new AlertDialog.Builder(Createplant2.this).setTitle("Congratulation!!!")
                    .setMessage("創建成功~>.^~y")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(Createplant2.this, PlantMainActivity.class);
                            startActivity(intent);

                            finish();
                        }
                    }).show();

        }
    }
    private void  getPlantData(String position) {
        Log.i("aaaa", "getPlatData");

//        Cursor c = db.query("plant1",null,"id=?",new String[]{position},null,null,null);
        Cursor c = db.query("plant1", null, "id=?", new String[]{position}, null, null, null);

        while (c.moveToNext()) {
            CreateName.setText(c.getString(c.getColumnIndex("name")));
            CreateBirthday.setText(c.getString(c.getColumnIndex("birthday")));
            CreateNote.setText(c.getString(c.getColumnIndex("note")));
//            Bitmap bitmap = dbu.getImage(etBlob(c.gc.getColumnIndex("image")));
            img.setImageURI(Uri.parse(getString(c.getColumnIndex("image"))));
            Log.i("aaaa", "getPlantData OK");
            c.close();
        }
    }
}
