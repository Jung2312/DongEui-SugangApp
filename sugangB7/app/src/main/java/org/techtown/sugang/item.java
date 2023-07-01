package org.techtown.sugang;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.List;

public class item {
    public String TypeText;
    public String LectureNoText;
    public String LectureTitleText;
    public String ProfessorText;
    public String TimeText;
    //public AppCompatButton deleteBtn;

    public item() {

    }


    public item(String typeText, String lectureNoText, String lectureTitleText, String professorText,
                String timeText) {

        TypeText = typeText;
        LectureNoText = lectureNoText;
        LectureTitleText = lectureTitleText;
        ProfessorText = professorText;
        TimeText = timeText;

    }

    @SuppressLint("RestrictedApi")
    public List<item> GetItems() {
        List<item> lstItems = new ArrayList<>();

        return lstItems;
    }

}