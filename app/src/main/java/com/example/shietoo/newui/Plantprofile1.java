package com.example.shietoo.newui;

import android.content.ContentProvider;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Plantprofile1 extends AppCompatActivity {
    private ImageView PlantFile1_img;
    private TextView PlantFile1_name,PlantFile1_birthday,PlantFile1_note;
    private Button PlantFile1_edit;
    private SQLiteDatabase db;
    private MyDBHelper myDBHelper;
    private DbBitmapUtility dbu;
    private MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantprofile1);

        PlantFile1_name = (TextView) findViewById(R.id.PlantFile1_name);
        PlantFile1_birthday = (TextView) findViewById(R.id.PlantFile1_birthday);
        PlantFile1_note = (TextView) findViewById(R.id.PlantFile1_note);
        PlantFile1_edit = (Button) findViewById(R.id.PlantFile1_edit);
        PlantFile1_img = (ImageView) findViewById(R.id.PlantFile1_img);

        myDBHelper = new MyDBHelper(this, "plantData", null, 2);
        db = myDBHelper.getReadableDatabase();
        myApp =(MyApp)getApplication();

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
//                case 5:
//                    getPlantData("5");
//                    break;
//                case 6:
//                    getPlantData("6");
//                    break;

            }

        PlantFile1_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Plantprofile1.this, Plantprofile2.class);
                startActivity(intent);
                finish();       //讓此頁死亡
            }
        });

    }
    private void  getPlantData(String position){
        Log.i("aaaa","getPlatData");

//        Cursor c = db.query("plant1",null,"id=?",new String[]{position},null,null,null);
         Cursor c = db.query("plant1",null,"id=?",new String[]{position},null,null,null);

        while (c.moveToNext()){
            PlantFile1_name.setText(c.getString(c.getColumnIndex("name")));
            PlantFile1_birthday.setText(c.getString(c.getColumnIndex("birthday")));
            PlantFile1_note.setText(c.getString(c.getColumnIndex("note")));
            PlantFile1_img.setImageURI(Uri.parse(c.getString(c.getColumnIndex("image"))));
            Log.i("aaaa","getPlantData OK");
            c.close();
        }

    }
}