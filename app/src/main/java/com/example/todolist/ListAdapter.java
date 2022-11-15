package com.example.todolist;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListAdapter  extends AppCompatActivity {

    public static ArrayList<String> TitleList = new ArrayList<>();
    public static ArrayList<String> Content_List = new ArrayList<>();
    public static ArrayList<String> Image_List = new ArrayList<>();
    public static int position;

    protected void addTitle(String title){
        TitleList.add(title);
    }
    protected void addContent(String content){
        Content_List.add(content);
    }
    protected void addImage(String imageStr){
        Image_List.add(imageStr);
    }

}
