package com.example.todolist;

import java.util.ArrayList;

public class ListAdapter {

    public static ArrayList<String> TitleList = new ArrayList<>();
    public static ArrayList<String> Content_List = new ArrayList<>();
    public static ArrayList<String> Image_List = new ArrayList<>();
    public static int position;

    protected void addData(String title, String content, String imageUri){
        TitleList.add(title);
        Content_List.add(content);
        Image_List.add(imageUri);
    }
//    protected void saveData(String title, String content, String imageUri){
//        //設定ファイルのオブジェクト生成
//        SharedPreferences pref_title = getSharedPreferences(title,Context.MODE_PRIVATE);
//        SharedPreferences pref_content = getSharedPreferences(title,Context.MODE_PRIVATE);
//        SharedPreferences pref_image = getSharedPreferences(title,Context.MODE_PRIVATE);
//        //設定ファイルのオブジェクト生成後にデータの取得を行う
//        SharedPreferences.Editor editor_title = pref_title.edit();
//        SharedPreferences.Editor editor_content = pref_content.edit();
//        SharedPreferences.Editor editor_image = pref_image.edit();
//
//        //プレファレンスへの書き込み処理を記述する
//        editor_title.putString(title,title);
//        editor_title.apply();
//        editor_content.putString(title,content);
//        editor_content.apply();
//        editor_image.putString(title,imageUri);
//        editor_image.apply();
//
//    }

}
