package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Map;

public class ToDoList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        ArrayList<String> TitleList = new ArrayList<>();
        ArrayList<String> Content_List = new ArrayList<>();
        ArrayList<String> Image_List = new ArrayList<>();

        //データ保存用配列を生成する
        String title, content, imageStr;
        Message msg = new Message();

        ArrayAdapter<String> adapter =
                new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, TitleList);
        //画面上のパーツ宣言
        ListView listView = findViewById(R.id.todoView);
        FloatingActionButton addTodoBtn = findViewById(R.id.addTodoBtn);
        //設定ファイルオブジェクト生成
        SharedPreferences title_content_image = getPreferences(MODE_PRIVATE);
        //データの取得を行う
        SharedPreferences.Editor editor = title_content_image.edit();
        //呼び先
        Intent fromNewToDo = getIntent();
        Intent fromSpecific = getIntent();
        //遷移先
        Intent GotoSpecific = new Intent(getApplicationContext(),SpecificActivity.class);
        Intent GotoNewToDo = new Intent(getApplicationContext(),NewToDo.class);
        //呼び元判別用データを取得する
        int check_fromNewToDo = fromNewToDo.getIntExtra(msg.str_New_ToDo,0);
        int check_fromSpecific = fromSpecific.getIntExtra(msg.str_Spe_ToDo,0);
        int check_special = fromNewToDo.getIntExtra(msg.when_edited, 0);
        //NewToDoから立ち上げた場合(追加)
        if (check_fromNewToDo == msg.New_ToDo){
            //データをゲット
            title = fromNewToDo.getStringExtra(msg.ttl_Str);
            content = fromNewToDo.getStringExtra(msg.ctt_Str);
            imageStr = fromNewToDo.getStringExtra(msg.img_Str);
            //ファイルに書き込み
            editor.putString(title+msg.ttl_Str, title).apply();
            editor.putString(title+msg.ctt_Str, content).apply();
            editor.putString(title+msg.img_Str, imageStr).apply();
            //Listをリセットする
            for (String pref : title_content_image.getAll().keySet()){
                if (pref.contains(msg.ttl_Str)){
                    TitleList.add(title_content_image.getString(pref,""));
                }
            }
            //確認用
            System.out.println(msg.ttl_Str+ TitleList);
        }
        //Specific から立ち上げた場合(削除)
        else if (check_fromSpecific == msg.Spe_ToDo){
            title = fromSpecific.getStringExtra(msg.oldTtl);
            editor.remove(title+msg.ttl_Str).apply();
            editor.remove(title+msg.ctt_Str).apply();
            editor.remove(title+msg.img_Str).apply();
            //Listをリセットする
            for (String pref : title_content_image.getAll().keySet()){
                if (pref.contains(msg.ttl_Str)){
                    TitleList.add(title_content_image.getString(pref,""));
                }
            }
            //確認用
            System.out.println(msg.ttl_Str+ TitleList);
        }
        //内容を修正された場合
        else if (check_special == msg.special){
            //旧内容を削除する
            title = fromNewToDo.getStringExtra(msg.oldTtl);
            editor.remove(title+msg.ttl_Str).apply();
            editor.remove(title+msg.ctt_Str).apply();
            editor.remove(title+msg.img_Str).apply();
            //新内容を書き込み
            //データをゲット
            title = fromNewToDo.getStringExtra(msg.ttl_Str);
            content = fromNewToDo.getStringExtra(msg.ctt_Str);
            imageStr = fromNewToDo.getStringExtra(msg.img_Str);
            //ファイルに書き込み
            editor.putString(title+ msg.ttl_Str,title).apply();
            editor.putString(title+ msg.ctt_Str,content).apply();
            editor.putString(title+ msg.img_Str,imageStr).apply();
            //Listをリセットする
            for (String pref : title_content_image.getAll().keySet()){
                if (pref.contains(msg.ttl_Str)){
                    TitleList.add(title_content_image.getString(pref,""));
                }
            }
        }
        //起動した場合
        else{
            //title_content_image.edit().clear().apply();               //ファイルをclear
            //Listをリセットする
            for (String pref : title_content_image.getAll().keySet()){
                if (pref.contains(msg.ttl_Str)){
                    TitleList.add(title_content_image.getString(pref,""));
                }
            }
            for (int index = 0; index<TitleList.size(); index++){
                for (String pref : title_content_image.getAll().keySet()){
                    if (pref.contains(msg.ttl_Str)){
                    }else if (pref.contains(msg.ctt_Str)){
                        if (pref.contains(TitleList.get(index))){
                            Content_List.add(title_content_image.getString(pref,""));
                        }
                    }
                    else if (pref.contains(msg.img_Str)){
                        if (pref.contains(TitleList.get(index))){
                            Image_List.add(title_content_image.getString(pref,""));
                        }
                    }
                }
            }
            //確認用
            System.out.println(msg.ttl_Str+ TitleList);
            System.out.println(msg.ctt_Str+ Content_List);
            System.out.println(msg.img_Str+ Image_List);
        }
        //ListView のアダプターに配列用アダプターをセットする
        listView.setAdapter(adapter);
        //addTodoBtnを押すと新しいto-doを追加する画面に遷移する
        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoNewToDo.putExtra(msg.str_ToDo_New,msg.ToDo_New);
                startActivity(GotoNewToDo);
            }
        });
        //タイトルにクリックすると詳細画面に遷移する
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ListAdapterのListをリセットする
                for (int index = 0; index<TitleList.size(); index++){
                    for (String pref : title_content_image.getAll().keySet()){
                        if (pref.contains(msg.ttl_Str)){
                        }else if (pref.contains(msg.ctt_Str)){
                            if (pref.contains(TitleList.get(index))){
                                Content_List.add(title_content_image.getString(pref,""));
                            }
                        }
                        else if (pref.contains(msg.img_Str)){
                            if (pref.contains(TitleList.get(index))){
                                Image_List.add(title_content_image.getString(pref,""));
                            }
                        }
                    }
                }
                System.out.println(msg.ctt_Str+ Content_List);
                System.out.println(msg.img_Str+ Image_List);

                GotoSpecific.putExtra(msg.ttl_Str, TitleList.get(position));
                GotoSpecific.putExtra(msg.ctt_Str,Content_List.get(position));
                GotoSpecific.putExtra(msg.img_Str,Image_List.get(position));

                startActivity(GotoSpecific);
            }
        });
    }
}