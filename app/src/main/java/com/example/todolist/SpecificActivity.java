package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SpecificActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);

        //画面上のパーツ宣言
        Button completeBtn = findViewById(R.id.complete);
        TextView contentView = findViewById(R.id.contentView);
        ImageView imageView = findViewById(R.id.imageView2);
        FloatingActionButton editBtn = findViewById(R.id.editBtn);
        FloatingActionButton returnBtn = findViewById(R.id.returnBtn);

        //呼び元
        Intent fromToDoList = getIntent();
        String Title = fromToDoList.getStringExtra("title");
        String Content = fromToDoList.getStringExtra("content");
        Uri imageUri = null;
        if (fromToDoList.getStringExtra("imageUri") != "null"){
            String image = fromToDoList.getStringExtra("imageUri");
            imageUri = Uri.parse(image);
            imageView.setImageURI(imageUri);
        }

        setTitle(Title);
        contentView.setText(Content);

        //completeボタンを押した時削除する
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToDoView = new Intent(getApplicationContext(),ToDoList.class);
                returnToDoView.putExtra("check",30);

                startActivity(returnToDoView);
            }
        });
        //returnボタンを押して画面が閉じる
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //editボタンを押した時、内容を修正する画面に移動
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoNewToDo = new Intent(getApplicationContext(),NewToDo.class);
                GoNewToDo.putExtra("fromSpecific_check",2);
                GoNewToDo.putExtra("title",Title);
                GoNewToDo.putExtra("content",Content);

                startActivity(GoNewToDo);
            }
        });
    }
}