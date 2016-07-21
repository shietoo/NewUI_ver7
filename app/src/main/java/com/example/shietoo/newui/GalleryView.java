package com.example.shietoo.newui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GalleryView extends AppCompatActivity {
    int[]imageIDs={R.drawable.day,R.drawable.happy,R.drawable.night,R.drawable.thirsty};
    private List<String> thumbs;
    private List<String> imagePaths;
    ImageView imageView;
    private MyApp myApp;

    private DisplayMetrics mPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gallery);

        imageView =(ImageView)findViewById(R.id.image1);
        myApp = (MyApp)getApplication();

//        mPhone = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(mPhone);


        //imageView.setImageURI(Uri.parse(myApp.uri_plantmain));

       try {
           Log.i("zzz", myApp.uri_plantmain);
           if (myApp.uri_plantmain != null) {
               String s = myApp.uri_plantmain;
               imageView.setImageURI(Uri.parse(s));

               Log.i("zzz", myApp.uri_plantmain+"00");
           }
       }catch (Exception e){}




        ContentResolver cr = getContentResolver();
        String[]pro = {MediaStore.Images.Media._ID,MediaStore.Images.Media.DATA};
        Cursor c = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,pro,null,null,null);
        thumbs = new ArrayList<>();
        imagePaths = new ArrayList<>();




        for (int i = 0;i<c.getCount();i++){
            c.moveToPosition(i);
            String id = c.getString(c.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            thumbs.add(id+"");
            String filePath = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
            imagePaths.add(filePath);

        }
        c.close();

        Gallery gallery = (Gallery)findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(),"pic" + (position + 1) + " selected",
//                        Toast.LENGTH_SHORT).show();

                // imageView.setImageResource(imageIDs[position]);


                BitmapFactory.Options moptions = new BitmapFactory.Options();
                moptions.inSampleSize=6;
                Bitmap bitmap = BitmapFactory.decodeFile(imagePaths.get(position),moptions);
                imageView.setImageBitmap(bitmap);
//                if (bitmap.getWidth() > bitmap.getHeight()) {
//                    ScalePic(bitmap,300);
//                } else {
//                    ScalePic(bitmap,400);
//                }
                // imageView.setImageURI(Uri.parse(imagePaths.get(position)));
            }
        });
    }

    public class ImageAdapter extends BaseAdapter{
        private Context context;
        private int itemBG;
        public ImageAdapter(Context c){
            context =c;
            TypedArray a = obtainStyledAttributes(R.styleable.MyGallery);
            itemBG =a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground,0);
            a.recycle();
        }
        public int getCount(){
            return imagePaths.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        public Object getItem(int position) {
            return position;
        }

        public long getItemID(int position){
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent){


            ImageView imageView = new ImageView(context);
//            BitmapFactory.Options moptions = new BitmapFactory.Options();
//            moptions.inSampleSize=100;
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePaths.get(position),moptions);
            Bitmap bitmap = BitmapFactory.decodeFile(thumbs.get(position));
            imageView.setImageBitmap(bitmap);
            imageView.setLayoutParams(new Gallery.LayoutParams(200,200));
            imageView.setBackgroundResource(itemBG);
            return imageView;
        }
    }

    private void ScalePic(Bitmap bitmap, int phone) {
        float mScal = 1;
        if (bitmap.getWidth() > phone) {
            mScal = (float) phone / bitmap.getWidth();
            Matrix matrix = new Matrix();
            matrix.setScale(mScal, mScal);
            Bitmap mScalBitmap = Bitmap.createBitmap(
                    bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
            imageView.setImageBitmap(mScalBitmap);
        } else {
            imageView.setImageBitmap(bitmap);

        }

    }
}


