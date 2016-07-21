package com.example.shietoo.newui;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView welcomeView;
    private SQLiteDatabase db;
    private MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHelper = new MyDBHelper(this, "plantData", null, 2);
        db = myDBHelper.getReadableDatabase();

        Cursor cursor =db.query("plant1",null,null,null,null,null,null);
        if (cursor.getCount()>0){}else {
            for (int i = 0; i < 4; i++) {
                ContentValues cv = new ContentValues();
                cv.put("name", "");
                cv.put("birthday", "");
                cv.put("note", "");
                cv.put("image", "");
                //  db.update("plant1",cv,null,null);
                db.insert("plant1", null, cv);
            }
        }

        welcomeView = (ImageView)findViewById(R.id.welcome_iv);
        welcomeView.setImageResource(R.drawable.welcome);
        welcomeView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent1 = new Intent(MainActivity.this,BradGarden.class);
            startActivity(intent1);

            Intent it = new Intent(MainActivity.this,MyBootService.class);
            it.putExtra("what",1);
            startService(it);
            finish();
//            Intent intent = new Intent(MainActivity.this,MyService.class);
//            intent.putExtra(MyService.EXTRA_MESSAGE,"我快渴死惹QQ");
//            startService(intent);
        }
    });
    }
//    public void welcome(View view){
//        Intent intent = new Intent(this,PlantMainActivity.class);
//        startActivity(intent);
//
//    }
//    public void startservice(View v){
//        Intent it = new Intent(this,MyBootService.class);
//        it.putExtra("what",1);
//        startService(it);
//
//        Intent intent = new Intent(this,MyService.class);
//        intent.putExtra(MyService.EXTRA_MESSAGE,"我快渴死惹QQ");
//        startService(intent);
//    }
}
