package com.example.shietoo.newui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

public class CreateDiary extends AppCompatActivity {
    private SQLiteDatabase db;
    private MyDBHelper myDBHelper;
    private EditText titleET,descET;
    private Uri cameraUri;
    private MyApp myApp;
    private ImageView diaryImage;
    static final int DBVERSION =2;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diary);

        myApp =(MyApp)getApplication();
        myDBHelper = new MyDBHelper(this, "plantData", null, DBVERSION);
        db = myDBHelper.getReadableDatabase();

        titleET = (EditText)findViewById(R.id.diaryTitle_et);
        descET = (EditText)findViewById(R.id.diaryDesc_et);
        diaryImage = (ImageView)findViewById(R.id.diaryimage_iv);



    }
    public void save(View view){
        try {
            ContentValues cv = new ContentValues();
            cv.put("date",setTime());
            cv.put("title",titleET.getText().toString());
            cv.put("desc",descET.getText().toString());
            cv.put("image",String.valueOf(cameraUri));
            db.insert("diary",null,cv);
            Log.i("zzz","insert ok");
            cursor = db.query("diary", new String[]{"rowid _id", "title",
                    "date","desc", "image"}, null, null, null, null, "id DESC");

            myApp.cursorAdapter.swapCursor(cursor);

        }catch (Exception e){

        }
        finish();
    }
    public void camera1(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            cameraUri = data.getData();
            diaryImage.setImageURI(cameraUri);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String setTime(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String nowTime = year+"-"+(month+1)+"-"+day;
        return nowTime;
    }
}
