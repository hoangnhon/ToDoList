package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpecificActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);

        ListAdapter list = new ListAdapter();
//        SharedPreferences pref_image= getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor_image = pref_image.edit();

//        pref_image.edit().clear().commit();

//        Map<String, ?> map_image = pref_image.getAll();
//        ListAdapter.Image_List.addAll(map_image.keySet());
//        System.out.println("Image_List"+ListAdapter.Image_List);

        //画面上のパーツ宣言
        Button completeBtn = findViewById(R.id.complete);
        TextView contentView = findViewById(R.id.contentView);
        ImageView imageView = findViewById(R.id.imageView2);
        FloatingActionButton editBtn = findViewById(R.id.editBtn);
        FloatingActionButton returnBtn = findViewById(R.id.returnBtn);

        //遷移先
        Intent GoToDoList = new Intent(getApplicationContext(),ToDoList.class);
        Intent GoNewToDo = new Intent(getApplicationContext(),NewToDo.class);

        //呼び元
        Intent fromToDoList = getIntent();
        Intent fromNewToDo = getIntent();
        Uri imageUri = null;
        String Title = "";
        String Content = "";
        String image = "";

        String check_ToDoList =fromToDoList.getStringExtra("check_fromToDoList");
        String check_NewToDo = fromNewToDo.getStringExtra("check_fromNewToDo");
        if (Objects.equals(check_ToDoList ,"fromToDoList　詳細表示")){      //todolistから立ち上げた場合、値を受け取る処理
            Title = fromToDoList.getStringExtra("title");
            Content = ListAdapter.Content_List.get(ListAdapter.position);
            image = ListAdapter.Image_List.get(ListAdapter.position);
            setTitle(Title);
            contentView.setText(Content);
        }
        else if (Objects.equals(check_NewToDo, "fromNewToDo")){        //Newtodo から立ち上げた場合、値を受け取る処理
            Title = fromNewToDo.getStringExtra("title");
            Content = fromNewToDo.getStringExtra("content");
            image = fromNewToDo.getStringExtra("imageUri");
//            editor_image.putString(Title,fromNewToDo.getStringExtra("imageUri")).apply();
//            list.addImage(image);
            System.out.println("Image_List"+ListAdapter.Image_List);
            setTitle(Title);
            contentView.setText(Content);
        }

        if (image != "null"){
            imageUri = Uri.parse(image);
            imageView.setImageURI(imageUri);
        }

        //completeボタンを押した時削除する
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pref_image.edit().remove(ListAdapter.TitleList.get(ListAdapter.position)).apply();
//                ListAdapter.Image_List.remove(ListAdapter.position);
                GoToDoList.putExtra("check_clear","fromSpecific 完了したアイテムを削除");
                startActivity(GoToDoList);
            }
        });
        //returnボタンを押して画面が閉じる
        String finalTitle = Title;
        String finalContent = Content;
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToDoList.putExtra("check_add","fromSpecific　新しいアイテムを追加");
                GoToDoList.putExtra("title", finalTitle);
                startActivity(GoToDoList);
            }
        });

        //editボタンを押した時、内容を修正する画面に移動
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoNewToDo.putExtra("fromSpecific_check","fromSpecific_check");
                GoNewToDo.putExtra("title", finalTitle);
                GoNewToDo.putExtra("content", finalContent);
                startActivity(GoNewToDo);
            }
        });
    }
}