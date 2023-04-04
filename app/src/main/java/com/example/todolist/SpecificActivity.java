package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SpecificActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);

        String title, content , imageStr;
        Uri imageUri;
        Message msg = new Message();

        //画面上のパーツ宣言
        Button completeBtn = findViewById(R.id.complete);
        TextView contentView = findViewById(R.id.contentView);
        ImageView imageView = findViewById(R.id.imageView2);
        FloatingActionButton editBtn = findViewById(R.id.editBtn);
        FloatingActionButton returnBtn = findViewById(R.id.returnBtn);

        //呼び元
        Intent fromToDoList = getIntent();
        //遷移先
        Intent GotoNewToDo = new Intent(getApplicationContext(),NewToDo.class);
        Intent GotoToDoList = new Intent(getApplicationContext(),ToDoList.class);

        title = fromToDoList.getStringExtra(msg.ttl_Str);
        content = fromToDoList.getStringExtra(msg.ctt_Str);
        imageStr = fromToDoList.getStringExtra(msg.img_Str);
        if (imageStr != "null") {
            imageUri = Uri.parse(imageStr);
            imageView.setImageURI(imageUri);
        }

        setTitle(title);
        contentView.setText(content);

        //completeボタンを押した時削除する
        completeBtn.setOnClickListener(v -> {
            GotoToDoList.putExtra(msg.str_Spe_ToDo, msg.Spe_ToDo);
            GotoToDoList.putExtra(msg.oldTtl, title);
            startActivity(GotoToDoList);
        });
        //returnボタンを押して画面が閉じる
        returnBtn.setOnClickListener(v -> finish());

        //editボタンを押した時、内容を修正する画面に移動
        editBtn.setOnClickListener(v -> {
            GotoNewToDo.putExtra(msg.str_Spe_New, msg.Spe_New);
            GotoNewToDo.putExtra(msg.ttl_Str, title);
            GotoNewToDo.putExtra(msg.ctt_Str, content);
            GotoNewToDo.putExtra(msg.img_Str, imageStr);
            startActivity(GotoNewToDo);
        });
    }
}