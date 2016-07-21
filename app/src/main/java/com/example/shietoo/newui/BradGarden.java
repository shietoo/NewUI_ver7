package com.example.shietoo.newui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BradGarden extends AppCompatActivity {

    private ImageView GardenIV1, GardenIV2, GardenIV3, GardenIV4, GardenIV5, GardenIV6;
    private SQLiteDatabase db;
    private MyDBHelper myDBHelper;
    private DbBitmapUtility dbu;
    private int position;
    private ImageView[] GardenIV = {GardenIV1, GardenIV2, GardenIV3, GardenIV4, GardenIV5, GardenIV6};
    private MyApp myApp;
    private TextView plantNameTV1, plantNameTV2, plantNameTV3, plantNameTV4, plantNameTV5, plantNameTV6;
    private String plantName;

    //static final int PIC_DROP =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brad_garden);

        myApp = (MyApp) getApplication();

//        GardenIV[0] = (ImageView) findViewById(R.id.garden1_iv);
//        GardenIV[1] = (ImageView) findViewById(R.id.garden2_iv);
//        GardenIV[2] = (ImageView) findViewById(R.id.garden3_iv);
//        GardenIV[3] = (ImageView) findViewById(R.id.garden4_iv);
//        GardenIV[4] = (ImageView) findViewById(R.id.garden5_iv);
//        GardenIV[5] = (ImageView) findViewById(R.id.garden6_iv);

        myDBHelper = new MyDBHelper(this, "plantData", null, 2);
        db = myDBHelper.getReadableDatabase();
        dbu = new DbBitmapUtility();

        //六筆資料的照片
//        Cursor c1 = db.query("plant1", null, "id=?", new String[]{"1"}, null, null, null);
//        while (c1.moveToNext()) {
//            String uri1 = c1.getString(c1.getColumnIndex("image"));
//            Log.i("aaaa",uri1);
//            if (uri1.trim().length()>0) {
//                GardenIV[0].setImageURI(Uri.parse(uri1));
//                c1.close();
//            }else {
//                GardenIV[0].setImageResource(R.drawable.wood);
//            }
//        }
//        Cursor c2 = db.query("plant1", null, "id=?", new String[]{"2"}, null, null, null);
//        while (c2.moveToNext()) {
//            String uri2 = c2.getString(c2.getColumnIndex("image"));
//            if (uri2.trim().length()>0) {
//                GardenIV[1].setImageURI(Uri.parse(uri2));
//
//                c2.close();
//            }else {
//                GardenIV[1].setImageResource(R.drawable.wood);
//            }
//        }
//        Cursor c3 = db.query("plant1", null, "id=?", new String[]{"3"}, null, null, null);
//        while (c3.moveToNext()) {
//            String uri3 = c3.getString(c3.getColumnIndex("image"));
//            if (uri3 .trim().length()>0){
//            GardenIV[2].setImageURI(Uri.parse(uri3));
//            c3.close();
//        }else {
//                GardenIV[2].setImageResource(R.drawable.wood);
//            }
//        }
//        Cursor c4 = db.query("plant1", null, "id=?", new String[]{"4"}, null, null, null);
//        while (c4.moveToNext()) {
//            String uri4 = c4.getString(c4.getColumnIndex("image"));
//            if(uri4.trim().length()>0) {
//                GardenIV[3].setImageURI(Uri.parse(uri4));
//                c4.close();
//            }else{
//                GardenIV[3].setImageResource(R.drawable.wood);
//            }
//        }
//        Cursor c5 = db.query("plant1", null, "id=?", new String[]{"5"}, null, null, null);
//        while (c5.moveToNext()) {
//            Bitmap bitmap5 = dbu.getImage(c5.getBlob(c5.getColumnIndex("image")));
//            GardenIV[4].setImageBitmap(bitmap5);
//            c5.close();
//        }
//        Cursor c6 = db.query("plant1", null, "id=?", new String[]{"6"}, null, null, null);
//        while (c6.moveToNext()) {
//            Bitmap bitmap6 = dbu.getImage(c6.getBlob(c6.getColumnIndex("image")));
//            GardenIV[5].setImageBitmap(bitmap6);
//            c6.close();
//        }
        //六筆資料

//        for (int j =0 ;j<6;j++) {
//            Cursor c[] = {db.query("plant1", null, "id=?", new String[]{"\"" + j + "\""}, null, null, null)};
//            while (c[j].moveToNext()) {
//                Bitmap bitmap[] = {dbu.getImage(c[j].getBlob(c[j].getColumnIndex("image")))};
//                GardenIV[j].setImageBitmap(bitmap[j]);
//
//
//            }
//        }

        plantNameTV1 = (TextView) findViewById(R.id.tv_plantname1);
        plantNameTV2 = (TextView) findViewById(R.id.tv_plantname2);
        plantNameTV3 = (TextView) findViewById(R.id.tv_plantname3);
        plantNameTV4 = (TextView) findViewById(R.id.tv_plantname4);
//        plantNameTV5 = (TextView) findViewById(R.id.tv_plantname5);
//        plantNameTV6 = (TextView) findViewById(R.id.tv_plantname6);
        plantNameTV1.setText(getPlantName("1"));
        plantNameTV2.setText(getPlantName("2"));
        plantNameTV3.setText(getPlantName("3"));
        plantNameTV4.setText(getPlantName("4"));
//        plantNameTV5.setText(getPlantName("5"));
//        plantNameTV6.setText(getPlantName("6"));
        plantNameTV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myApp.plantID = 1;
                if (plantNameTV1.getText().toString().trim().length() > 0) {
                    Intent intent = new Intent(BradGarden.this, PlantMainActivity.class);
                    startActivity(intent);
                //    finish();
                    Log.i("aaaa", "bradgarden:" + myApp.plantID);
                } else {
                    Intent intent = new Intent(BradGarden.this, Createplant1.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        plantNameTV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApp.plantID = 2;
                if (plantNameTV2.getText().toString().trim().length() > 0) {
                    Intent intent = new Intent(BradGarden.this, PlantMainActivity.class);
                    startActivity(intent);
               //     finish();
                } else {
                    Intent intent = new Intent(BradGarden.this, Createplant1.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
        plantNameTV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myApp.plantID = 3;
                if (plantNameTV3.getText().toString().trim().length() > 0) {
                    Intent intent = new Intent(BradGarden.this, PlantMainActivity.class);
                    startActivity(intent);
                //    finish();
                } else {
                    Intent intent = new Intent(BradGarden.this, Createplant1.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        plantNameTV4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myApp.plantID = 4;
                if (plantNameTV4.getText().toString().trim().length() > 0) {
                    Intent intent = new Intent(BradGarden.this, PlantMainActivity.class);
                    startActivity(intent);
               //     finish();
                } else {
                    Intent intent = new Intent(BradGarden.this, Createplant1.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
//        GardenIV[4].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                myApp.plantID = 5;
//                if (plantNameTV5.getText().toString().trim().length() > 0) {
//
//                    Intent intent = new Intent(BradGarden.this, PlantMainActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                } else {
//                    Intent intent = new Intent(BradGarden.this, Createplant1.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });
//        GardenIV[5].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                myApp.plantID = 6;
//                if (plantNameTV6.getText().toString().trim().length() > 0) {
//                    Intent intent = new Intent(BradGarden.this, PlantMainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Intent intent = new Intent(BradGarden.this, Createplant1.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });



        if (plantNameTV1.getText().toString().trim().length() > 0) {
            plantNameTV1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override

                public boolean onLongClick(View v) {
                    new android.support.v7.app.AlertDialog.Builder(BradGarden.this).setTitle("Warning!!!")
                            .setMessage("Are you sure to delete this plant?Q_Q")
                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    up2Zero(plantNameTV1, "1");
                                }
                            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    return true;
                }
            });
        }
        if (plantNameTV2.getText().toString().trim().length() > 0) {
            plantNameTV2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override

                public boolean onLongClick(View v) {
                    new android.support.v7.app.AlertDialog.Builder(BradGarden.this).setTitle("Warning!!!")
                            .setMessage("Are you sure to delete this plant?Q_Q")
                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    up2Zero(plantNameTV2, "2");
                                }
                            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    return true;
                }
            });
        }
        if (plantNameTV3.getText().toString().trim().length() > 0) {
            plantNameTV3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override

                public boolean onLongClick(View v) {
                    new android.support.v7.app.AlertDialog.Builder(BradGarden.this).setTitle("Warning!!!")
                            .setMessage("Are you sure to delete this plant?Q_Q")
                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    up2Zero(plantNameTV3, "3");
                                }
                            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    return true;
                }
            });
        }
        if (plantNameTV4.getText().toString().trim().length() > 0) {
            plantNameTV4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override

                public boolean onLongClick(View v) {
                    new android.support.v7.app.AlertDialog.Builder(BradGarden.this).setTitle("Warning!!!")
                            .setMessage("Are you sure to delete this plant?Q_Q")
                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    up2Zero(plantNameTV4, "4");
                                }
                            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    return true;
                }
            });
        }
//        if (plantNameTV5.getText().toString().trim().length() > 0) {
//            GardenIV[4].setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//
//                public boolean onLongClick(View v) {
//                    new android.support.v7.app.AlertDialog.Builder(BradGarden.this).setTitle("Warning!!!")
//                            .setMessage("Are you sure to delete this plant?Q_Q")
//                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    up2Zero(plantNameTV5, "5");
//                                }
//                            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//                    return true;
//                }
//            });
//        }
//        if (plantNameTV6.getText().toString().trim().length() > 0) {
//            GardenIV[5].setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//
//                public boolean onLongClick(View v) {
//                    new android.support.v7.app.AlertDialog.Builder(BradGarden.this).setTitle("Warning!!!")
//                            .setMessage("Are you sure to delete this plant?Q_Q")
//                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    up2Zero(plantNameTV6, "6");
//                                }
//                            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//                    return true;
//                }
//            });
//        }


    }


    public void test1(View view) {
        for (int i=0;i<4;i++){
        ContentValues cv = new ContentValues();
        cv.put("name", "");
        cv.put("birthday", "");
        cv.put("note", "");
        cv.put("image", "");
        //  db.update("plant1",cv,null,null);
        db.insert("plant1", null, cv);
        TextView tv = (TextView) findViewById(R.id.tv);
        Cursor c = db.query("plant1", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int indexName = c.getColumnIndex("name");
            int indexID = c.getColumnIndex("id");
            //tv.setText(c.getString(indexID)+c.getString(indexName));
            tv.append(c.getString(indexID) + c.getString(indexName));
            Log.i("aaa", "OK3");
        }        }

    }

    private String getPlantName(String position) {
        Log.i("aaaa", "getPlantName");

        Cursor c = db.query("plant1", null, "id=?", new String[]{position}, null, null, null);
        while (c.moveToNext()) {
            plantName = c.getString(c.getColumnIndex("name"));
            Log.i("aaaa", plantName);
        }
        return plantName;
    }

    public void test2(View view) {
//        ContentValues cv = new ContentValues();
//        cv.put("name", "");
//        cv.put("birthday", "");
//        cv.put("note", "");


        // Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Pictures/img/plant.png");
        //   byte[] buf = dbu.getPNGBytes(bitmap);


      //  cv.put("image", "");
      //  db.update("plant1", cv, null, null);
        TextView tv = (TextView) findViewById(R.id.tv);
        Cursor c = db.query("plant1", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int indexName = c.getColumnIndex("name");
            int indexID = c.getColumnIndex("id");
            //tv.setText(c.getString(indexID)+c.getString(indexName));
            tv.append(c.getString(indexID) + c.getString(indexName));
            Log.i("aaa", "OK3");
        }
    }

    private void up2Zero(TextView tv, String position) {
        ContentValues cv = new ContentValues();
        cv.put("name", "");
        cv.put("birthday", "");
        cv.put("note", "");
        //Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Pictures/img/plant.png");

       // byte[] buf = dbu.getPNGBytes(bitmap);
        cv.put("image", "");
        db.update("plant1", cv, "id=?", new String[]{position});
        Cursor c = db.query("plant1", null, "id=?", new String[]{position}, null, null, null);
        while (c.moveToNext()) {
            int indexName = c.getColumnIndex("name");
           // int indexImage = c.getColumnIndex("image");
            tv.setText(c.getString(indexName));
           // GardenIV[Integer.valueOf(position) - 1].setImageResource(R.drawable.wood);
        }

    }

}

//    private void performCrop(Bitmap data){
//        Intent cropIntent = new Intent("com.android.camera.action.CROP");
//        //indicate image type and Uri
//        cropIntent.setType("image/*");
//        //set crop properties
//        cropIntent.putExtra("data", data);
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
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PIC_DROP){
//            Bundle extras = data.getExtras();
////get the cropped bitmap
//            Bitmap bitmap = extras.getParcelable("data");
//        }
//
//
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }resultCode
