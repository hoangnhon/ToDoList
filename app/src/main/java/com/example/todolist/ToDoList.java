package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Map;

public class ToDoList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        ListAdapter list = new ListAdapter();
        //データ保存用配列を生成する
        String title, content,imageUri;
        ArrayAdapter<String> adapter =
                new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,ListAdapter.TitleList);
        //画面上のパーツ宣言
        ListView listView = findViewById(R.id.todoView);
        FloatingActionButton addTodoBtn = findViewById(R.id.addTodoBtn);

        //設定ファイルのオブジェクト生成
        SharedPreferences pref_title= getPreferences(Context.MODE_PRIVATE);
        SharedPreferences pref_content= getPreferences(Context.MODE_PRIVATE);
        SharedPreferences pref_image= getPreferences(Context.MODE_PRIVATE);
        //設定ファイルのオブジェクト生成後にデータの取得を行う
        SharedPreferences.Editor editor_title = pref_title.edit();
        SharedPreferences.Editor editor_content = pref_content.edit();
        SharedPreferences.Editor editor_image = pref_image.edit();

        //遷移先の情報を受け取り為の宣言
        Intent fromNewTodo = getIntent();     //newTodoが追加された後、受け取る用インテント
        Intent fromSpecific = getIntent();
        int fromNewToDo_check = fromNewTodo.getIntExtra("check",0);
        int fromSpecific_check = fromSpecific.getIntExtra("check",0);
        if (fromNewToDo_check == 10){       //NewToDoより立ち上げた場合
            title = fromNewTodo.getStringExtra("title");
            Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
            content = fromNewTodo.getStringExtra("content");
            Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
            imageUri = fromNewTodo.getStringExtra("imageUri");
            Toast.makeText(getApplicationContext(), imageUri, Toast.LENGTH_SHORT).show();

            //NewToDoからのデータを受け取り、配列に追加する
            list.addData(title,content,imageUri);

            //プレファレンスへの書き込み処理を記述する
            editor_title.putString(title,title);
            editor_title.apply();
            editor_content.putString(title,content);
            editor_content.apply();
            editor_image.putString(title,imageUri);
            editor_image.apply();

            System.out.println(ListAdapter.TitleList);
            System.out.println(ListAdapter.Content_List);
            System.out.println(ListAdapter.Image_List);
        }
        else if (fromSpecific_check == 30){
            pref_title.edit().remove(ListAdapter.TitleList.get(ListAdapter.position)).apply();
            pref_content.edit().remove(ListAdapter.TitleList.get(ListAdapter.position)).apply();
            pref_image.edit().remove(ListAdapter.TitleList.get(ListAdapter.position)).apply();

            ListAdapter.TitleList.remove(ListAdapter.position);
            ListAdapter.Content_List.remove(ListAdapter.position);
            ListAdapter.Image_List.remove(ListAdapter.position);
        }
        else{
//            pref_title.edit().clear().commit();
//            pref_content.edit().clear().commit();
//            pref_image.edit().clear().commit();
            Map<String, ?> map_title = pref_title.getAll();
            Map<String, ?> map_content = pref_content.getAll();
            Map<String, ?> map_image = pref_image.getAll();

            ListAdapter.TitleList.addAll(map_title.keySet());
            System.out.println(ListAdapter.TitleList);

            ListAdapter.Content_List.addAll(map_content.keySet());
            System.out.println(ListAdapter.Content_List);

            ListAdapter.Image_List.addAll(map_image.keySet());
            System.out.println(ListAdapter.Image_List);
        }
        //ListView のアダプターに配列用アダプターをセットする
        listView.setAdapter(adapter);

        //addTodoBtnを押すと新しいto-doを追加する画面に遷移する
        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //遷移先を設定する
                Intent GoNewToDo = new Intent(getApplicationContext(),NewToDo.class);
                GoNewToDo.putExtra("fromToDoList_check",1);
                //Activityをスタート
                startActivity(GoNewToDo);
            }
        });
        //タイトルにクリックすると詳細画面に遷移する
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toSpecific = new Intent(getApplicationContext(),SpecificActivity.class);
                toSpecific.putExtra("title", ListAdapter.TitleList.get(position));
                toSpecific.putExtra("content",ListAdapter.Content_List.get(position));
                toSpecific.putExtra("imageUri",ListAdapter.Image_List.get(position));
                ListAdapter.position = position;

                startActivity(toSpecific);
            }
        });
    }
}