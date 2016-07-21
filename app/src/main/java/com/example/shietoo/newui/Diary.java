package com.example.shietoo.newui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Diary extends AppCompatActivity {

    private ListView lv;
    final String[] form = {"date","title", "desc", "image"};
    int[] to = {R.id.item_id,R.id.item_title, R.id.item_desc, R.id.item_iv};

    private TextView tv;
    private SQLiteDatabase db;
    private MyDBHelper myDBHelper;
    private ImageView iv;
    private MyApp myApp;
    SimpleCursorAdapter listAdapter;
    Cursor cursor;
    static final int DBVERSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        myApp =(MyApp)getApplication();
        tv = (TextView) findViewById(R.id.tv);
        lv = (ListView) findViewById(R.id.lv);
        iv = (ImageView) findViewById(R.id.item_iv);

        myDBHelper = new MyDBHelper(this, "plantData", null, DBVERSION);
        db = myDBHelper.getReadableDatabase();


        SimpleCursorAdapter.ViewBinder viewBinder = new SimpleCursorAdapter.ViewBinder() {
            /**
             * Binds the Cursor column defined by the specified index to the specified view.
             * <p>
             * When binding is handled by this ViewBinder, this method must return true.
             * If this method returns false, SimpleCursorAdapter will attempts to handle
             * the binding on its own.
             *
             * @param view        the view to bind the data to
             * @param cursor      the cursor to get the data from
             * @param columnIndex the column at which the data can be found in the cursor
             * @return true if the data was bound to the view, false otherwise
             */
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                iv = (ImageView)view;
                String uri = cursor.getString(columnIndex);
                iv.setImageURI(Uri.parse(uri));
                return true;

            }
        };

        restListView();
    }

    private void restListView() {

        try {

            cursor = db.query("diary", new String[]{"rowid _id", "title","date","desc", "image"}, null, null, null, null, "id DESC");
            listAdapter = new SimpleCursorAdapter(this, R.layout.diary_item,
                    cursor, new String[]{"date","title", "desc", "image"}, new int[]{R.id.item_id,R.id.item_title, R.id.item_desc, R.id.item_iv});
            myApp.cursorAdapter = listAdapter;
            listAdapter.getViewBinder();
            lv.setAdapter(listAdapter);

            listAdapter.notifyDataSetChanged();
            Log.i("zzz", "ok");
        } catch (Exception e) {
            Log.i("zzz", e.toString());
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myApp.page = id;
                Intent intent = new Intent(Diary.this,DiaryShow.class);
                startActivity(intent);

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                new android.support.v7.app.AlertDialog.Builder(Diary.this).setTitle("Warning!!!")
                        .setMessage("Are you sure to delete this diary?Q_Q")
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.delete("diary","id=?",new String[]{String.valueOf(id)});
                                Log.i("zzz",id+"");

                                cursor = db.query("diary", new String[]{"rowid _id", "title",
                                        "date","desc", "image"}, null, null, null, null, "id DESC");

                                listAdapter.swapCursor(cursor);

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
    public void adddiary(View view){
        Intent intent = new Intent(this,CreateDiary.class);
        startActivity(intent);

    }
}
