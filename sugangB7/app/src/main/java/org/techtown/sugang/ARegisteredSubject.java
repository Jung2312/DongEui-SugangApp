package org.techtown.sugang;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@RequiresApi(api = Build.VERSION_CODES.N)
public class ARegisteredSubject extends AppCompatActivity {

    private ListView lstLecture;
    private AppCompatButton aRegisteredSubjectBtn;
    private AppCompatButton applicationForClassesBtn;
    private ApplicationForClassesDel applicationForClassesDel;
    private String userID;
    private String data;
    private String delLectureNo;
    private CompletableFuture<ArrayList<HashMap<String, String>>> arr2 = new CompletableFuture<ArrayList<HashMap<String, String>>>();

    ARegisteredSearch aregisteredsearch;

    public static ARegisteredSubject aRegisteredSubject;
    ApplicationForClasses applicationForClasses = (ApplicationForClasses)ApplicationForClasses.applicationForClasses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aregisteredsbj);

        aRegisteredSubject = ARegisteredSubject.this;
        if (applicationForClasses != null) {
            applicationForClasses.finish();
        }

        Intent getIntent = getIntent();
        userID = getIntent.getStringExtra("userID");

        Log.d("ARegisteredSubject",userID);

        lstLecture = findViewById(R.id.lstLecture);

        aRegisteredSubjectBtn = findViewById(R.id.aRegisteredSubjectBtn);

        applicationForClassesBtn = findViewById(R.id.applicationForClassesBtn);


        applicationForClassesDel = new ApplicationForClassesDel();
        // 리스트뷰 아이템 클릭 이벤트
        lstLecture.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id){
                Object vo = (Object)adapterView.getAdapter().getItem(position);
                data = vo.toString();

                String[] data_split = data.split("lecture_no=");
                System.out.println("강의 번호 + '}' : " + data_split[1]);

                delLectureNo = data_split[1].substring(0,10);
                System.out.println("강의 번호 : " + delLectureNo);

                applicationForClassesDel.setContext(ARegisteredSubject.this);
                Log.v("CLICK", "OnClickListener" + position);

                AlertDialog.Builder builder = new AlertDialog.Builder(ARegisteredSubject.this);

                builder.setTitle(delLectureNo).setMessage("삭제 하시겠습니까?");

                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        applicationForClassesDel.deleteBtnClick(delLectureNo, userID);
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "삭제했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getApplicationContext(), "취소했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // 수강 신청 버튼 화면 전환
        applicationForClassesBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), ApplicationForClasses.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        aregisteredsearch = new ARegisteredSearch(this, userID);
        arr2 = aregisteredsearch.aRegisteredSubjectbtnClick(userID);
        Log.d("check", String.valueOf(arr2));
        // 리스트뷰 데이터 출력 이벤트
        FillList(arr2);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void FillList(CompletableFuture<ArrayList<HashMap<String, String>>> list) {
        try {

            List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
            String[] from = {"type", "lecture_no", "lecture_title", "professor", "time", "del"};
            int[] views = {R.id.typeText, R.id.lectureNoText, R.id.lectureTitleText,
                    R.id.professorText, R.id.timeText, R.id.delbtn};

            System.out.println("2" + list);
            list.thenAccept(filllist -> {
                for (HashMap<String, String> data : filllist) {
                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("type", data.get("type"));
                    datanum.put("lecture_no", data.get("lecture_no"));
                    datanum.put("lecture_title", data.get("lecture_title"));
                    datanum.put("professor", data.get("professor"));
                    datanum.put("time", data.get("time"));
                    datanum.put("del", "삭제");
                    prolist.add(datanum);
                }
                System.out.println("data" + prolist);
                final SimpleAdapter simpleAdapter = new SimpleAdapter(ARegisteredSubject.this,
                        prolist, R.layout.lecture_items, from,
                        views);
                lstLecture.setAdapter(simpleAdapter);
            });
        } catch (Exception ex) {
            Toast.makeText(ARegisteredSubject.this, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
}