package com.commonman.manishankar.mob_gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView=(GridView)findViewById(R.id.gridView);
        ImageAdapter imageAdapter=new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);
        final ArrayList<File> mypic=findpic(Environment.getExternalStorageDirectory());
        //String ExternalStoragePath= Environment.getExternalStorageDirectory().getAbsolutePath();
        //String targetPath=ExternalStoragePath+"/test";
        //Toast.makeText(getApplicationContext(),targetPath,Toast.LENGTH_SHORT);
        for(File file:mypic){
            imageAdapter.add(file.getAbsolutePath());
        }

    }
    public ArrayList<File> findpic(File root){
        ArrayList<File> pic=new ArrayList<File>();
        File[] files=root.listFiles();
        for(File file :files){
            if(file.isDirectory()){
                pic.addAll(findpic(file));
            }
            else{
                if(file.getName().endsWith(".jpeg")||file.getName().endsWith(".jpg")||file.getName().endsWith(".png")){

                    pic.add(file);
                }

            }

        }
        return pic;
    }

    public class ImageAdapter extends BaseAdapter{

        private Context mContext;
        ArrayList<String>itemList=new ArrayList<String>();
        public ImageAdapter(Context c){
            mContext=c;
        }
        void add(String path){
            itemList.add(path);
        }
        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if(convertView==null){
                imageView=new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(220,220));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8,8,8,8);
            }else{
                imageView=(ImageView)convertView;
            }
            Bitmap bitmap=decodeSamplebitmapfromuri(itemList.get(position),220,220);
            imageView.setImageBitmap(bitmap);
            return imageView;
        }

        public Bitmap decodeSamplebitmapfromuri(String path,int reqwid,int reqheight){
            Bitmap bm;
            final BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeFile(path,options);
            options.inSampleSize=CalculateSampleSize(options,reqwid,reqheight);
            options.inJustDecodeBounds=false;
            bm=BitmapFactory.decodeFile(path,options);
            return bm;
        }
        public int CalculateSampleSize(BitmapFactory.Options options,int reqwid,int reqheight){

            final int height=options.outHeight;
            final int width=options.outWidth;
            int sampleSize=1;

                if(height>reqheight||width>reqwid){
                    if(width>height){
                        sampleSize=Math.round((float)height/(float)reqheight);
                    }else {
                        sampleSize=Math.round((float)width/(float)reqwid);
                    }
                }
            return  sampleSize;
        }
    }

}
