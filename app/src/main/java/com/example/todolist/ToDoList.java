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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Map;
import java.util.Objects;

public class ToDoList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

//        ListAdapter list = new ListAdapter();
        SaveTitle saveTitle = new SaveTitle();
        SaveContent saveContent = new SaveContent();
        SaveImage saveImage = new SaveImage();

        //データ保存用配列を生成する
        String title;
        ArrayAdapter<String> adapter =
                new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,ListAdapter.TitleList);

        //画面上のパーツ宣言
        ListView listView = findViewById(R.id.todoView);
        FloatingActionButton addTodoBtn = findViewById(R.id.addTodoBtn);

//        //設定ファイルのオブジェクト生成
//        SharedPreferences pref_title = getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor_title = pref_title.edit();

        //遷移先
        Intent GoNewToDo = new Intent(getApplicationContext(),NewToDo.class);

        //呼び元
        Intent fromSpecific = getIntent();
        String check_Specific1 = fromSpecific.getStringExtra("check_add");
        String check_Specific2 = fromSpecific.getStringExtra("check_clear");
        if (Objects.equals(check_Specific1 ,"fromSpecific　新しいアイテムを追加")){
            title = fromSpecific.getStringExtra("title") ;
//            if (!Objects.equals(ListAdapter.TitleList.get(ListAdapter.TitleList.size()-1), title)){
//                saveTitle.save(title);
////                editor_title.putString(title,title).apply();
////                list.addTitle(title);
//            }
            System.out.println("TitleList"+ListAdapter.TitleList);
        }
        else if (Objects.equals(check_Specific2, "fromSpecific 完了したアイテムを削除")){
//            pref_title.edit().remove(ListAdapter.TitleList.get(ListAdapter.position)).apply();
            ListAdapter.TitleList.remove(ListAdapter.position);
            System.out.println("TitleList"+ListAdapter.TitleList);
        }
        else {  //起動した時
//            saveTitle.reset();
//            saveContent.reset();
//            saveImage.reset();

//            saveTitle.now_list();
//            saveContent.now_list();
//            saveImage.now_list();
////            pref_title.edit().clear().commit();
//
//            Map<String, ?> map_title = pref_title.getAll();
//            ListAdapter.TitleList.addAll(map_title.keySet());
//            System.out.println("TitleList"+ListAdapter.TitleList);
        }
        //ListView のアダプターに配列用アダプターをセットする
        listView.setAdapter(adapter);

        //addTodoBtnを押すと新しいto-doを追加する画面に遷移する
        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoNewToDo.putExtra("fromToDoList_check","fromToDoList_check");
                startActivity(GoNewToDo);
            }
        });
        //タイトルにクリックすると詳細画面に遷移する
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent GoSpecific = new Intent(getApplicationContext(),SpecificActivity.class);
                GoSpecific.putExtra("check_fromToDoList","fromToDoList　詳細表示");
                GoSpecific.putExtra("title", ListAdapter.TitleList.get(position));
                ListAdapter.position = position;

                startActivity(GoSpecific);
            }
        });
    }
}