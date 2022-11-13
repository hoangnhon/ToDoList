package com.example.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewToDo extends AppCompatActivity {
    //クラス変数
    final int CAMERA_RESULT = 100;      //カメラ起動用のリクエストコードを宣言
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_to_do);

        //画面上のパーツ宣言
        EditText titleText = findViewById(R.id.titleText);
        Button titleClear = findViewById(R.id.clearBtn_1);
        EditText contentText = findViewById(R.id.contentText);
        Button contentClear = findViewById(R.id.clearBtn_2);
        FloatingActionButton addImageBtn = findViewById(R.id.addImageBtn);
        FloatingActionButton confirmBtn = findViewById(R.id.confirmBtn);
        FloatingActionButton cancelBtn = findViewById(R.id.cancelBtn);

        //ToDoListにより画面が立ち上げた場合
        Intent fromToDoList = getIntent();
        int check1 = fromToDoList.getIntExtra("fromToDoList_check",0);
        if (check1 != 0) {
            //titleを変換する
            setTitle("新規作成");
        }
        //specificにより画面が立ち上げた場合
        Intent fromSpecific = getIntent();
        int check2 = fromSpecific.getIntExtra("fromSpecific_check",0);
        if (check2 != 0){
            //titleを変換する
            setTitle("編集");
            String Title = fromSpecific.getStringExtra("title");
            String content = fromSpecific.getStringExtra("content");
            titleText.setText(Title);
            contentText.setText(content);
        }
        //タイトルクリアボタンを押すとタイトルボックスの文字が消える
        titleClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleText.setText("");
            }
        });
        //内容クリアボタンを押すと内容ボックスのが消える
        contentClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentText.setText("");
            }
        });
        //画像追加ボタンを押すとカメラが起動される
        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //ユニックな画像ファイル名を生成する
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String filename = "Image" + timestamp + "_.jpg";
                //画像ファイル設定に必要なパラメータを設定する
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE,filename);
                values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");
                //保存画像情報のURIを生成
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                //カメラ起動のintent にパラメータをセットする
                GoCamera.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

                startActivityForResult(GoCamera,CAMERA_RESULT);
            }
        });
        //確認ボタンを押すとtodolist画面に戻り、入力したタイトルがtodolist配列に表示する
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_TodoView = new Intent(getApplicationContext(),ToDoList.class);
                intent_TodoView.putExtra("check",10);
                intent_TodoView.putExtra("title",titleText.getText().toString());
                intent_TodoView.putExtra("content",contentText.getText().toString() == null ? "null": contentText.getText().toString());
                if (imageUri != null){
                    intent_TodoView.putExtra("imageUri",imageUri.toString());
                }else {
                    intent_TodoView.putExtra("imageUri","null");
                }

                startActivity(intent_TodoView);
            }
        });
        //cancelボタンを押した時前の画面に戻る
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    //撮った写真が画面に表示する
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //imageView に画像データをセットする
        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK){
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageURI(imageUri);
        }
    }
}