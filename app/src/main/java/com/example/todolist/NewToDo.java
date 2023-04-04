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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewToDo extends AppCompatActivity {
    //クラス変数
    final int CAMERA_RESULT = 100;      //カメラ起動用のリクエストコードを宣言
    Uri imageUri;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_to_do);

        Message msg = new Message();

        //画面上のパーツ宣言
        EditText titleText = findViewById(R.id.titleText);
        Button titleClear = findViewById(R.id.clearBtn_1);
        EditText contentText = findViewById(R.id.contentText);
        Button contentClear = findViewById(R.id.clearBtn_2);
        FloatingActionButton addImageBtn = findViewById(R.id.addImageBtn);
        FloatingActionButton confirmBtn = findViewById(R.id.confirmBtn);
        FloatingActionButton cancelBtn = findViewById(R.id.cancelBtn);
        imageView = findViewById(R.id.imageView);

        //呼び元
        Intent fromToDoList = getIntent();
        Intent fromSpecific = getIntent();
        //遷移先
        Intent GotoToDoList = new Intent(getApplicationContext(),ToDoList.class);
        //呼び元判別用データを取得する
        int check_fromToDoList = fromToDoList.getIntExtra(msg.str_ToDo_New,0);
        int check_fromSpecific = fromSpecific.getIntExtra(msg.str_Spe_New,0);
        String title,content,imageStr;
        String oldTitle ="";
        //ToDoListにより画面が立ち上げた場合
        if (check_fromToDoList == msg.ToDo_New){
            //titleを変換する
            setTitle("新規作成");
        }
        //specificにより画面が立ち上げた場合
        else if (check_fromSpecific == msg.Spe_New){
            //titleを変換する
            setTitle("編集");
            title = fromSpecific.getStringExtra(msg.ttl_Str);
            content = fromSpecific.getStringExtra(msg.ctt_Str);
            imageStr = fromSpecific.getStringExtra(msg.img_Str);
            oldTitle = title;
            titleText.setText(title);
            contentText.setText(content);
            if (imageStr != "null"){
                imageUri = Uri.parse(imageStr);
                imageView.setImageURI(imageUri);
            }
        }
        //タイトルクリアボタンを押すとタイトルボックスの文字が消える
        titleClear.setOnClickListener(v -> titleText.setText(""));
        //内容クリアボタンを押すと内容ボックスのが消える
        contentClear.setOnClickListener(v -> contentText.setText(""));
        //画像追加ボタンを押すとカメラが起動される
        addImageBtn.setOnClickListener(v -> {
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
        });
        //addImageBtnを長押しで画像を削除する
        addImageBtn.setOnLongClickListener(v -> false);
        //確認ボタンを押すとtodolist画面に戻り、入力したタイトルがtodolist配列に表示する
        String finalOldTitle = oldTitle;
        confirmBtn.setOnClickListener(v -> {
            GotoToDoList.putExtra(msg.ttl_Str,titleText.getText().toString().isEmpty() ? "タイトル無し" : titleText.getText().toString());
            GotoToDoList.putExtra(msg.ctt_Str,contentText.getText().toString());
            GotoToDoList.putExtra(msg.img_Str,imageUri == null ? "null" : imageUri.toString());
            if (check_fromToDoList == msg.ToDo_New){
                GotoToDoList.putExtra(msg.str_New_ToDo, msg.New_ToDo);
            }
            else if (check_fromSpecific == msg.Spe_New){
                GotoToDoList.putExtra(msg.when_edited, msg.special);
                GotoToDoList.putExtra(msg.oldTtl, finalOldTitle);
            }
            startActivity(GotoToDoList);
        });
        //cancelボタンを押した時前の画面に戻る
        cancelBtn.setOnClickListener(v -> finish());
    }
    @Override
    //撮った写真が画面に表示する
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //imageView に画像データをセットする
        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK){
            imageView = findViewById(R.id.imageView);
            imageView.setImageURI(imageUri);
        }
    }
}