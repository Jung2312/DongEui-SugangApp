package org.techtown.sugang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ApplicationForClasses extends AppCompatActivity {

    public static ApplicationForClasses applicationForClasses;

    private RadioButton majorRB;
    private RadioButton doubleMajRB;
    private RadioButton minorRB;
    private RadioButton cultureRB;
    private RadioButton shoppingBasketRB;
    private RadioGroup rgp;
    private EditText searchEdit;
    private ListView lstStockItems;
    private Button searchbtn;
    private Button aRegisteredSubjectBtn;

    private int check = 0;
    private String lecture_no = "1";
    private String data;
    private CompletableFuture<ArrayList<HashMap<String, String>>> arr = new CompletableFuture<ArrayList<HashMap<String, String>>>();



    LectureSearch lecturesearch;
    ApplicationForClassesAdd applicationForClassesAdd;
    ARegisteredSubject aRegisteredSubject = (ARegisteredSubject)ARegisteredSubject.aRegisteredSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        applicationForClasses = ApplicationForClasses.this;
        if (aRegisteredSubject != null) {
            aRegisteredSubject.finish();
        }

        Intent getIntent = getIntent();
        String userID = getIntent.getStringExtra("userID");

        Log.d("ApplicationForClasses",userID);

        // LinearLayout을 최상위 뷰 그룹으로 사용하는 경우
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        // 첫 번째 XML 파일의 내용을 inflate하여 추가
        View firstView = getLayoutInflater().inflate(R.layout.lst_items, null);
        layout.addView(firstView);

        // 별도의 XML 파일을 inflate하여 해당 뷰를 가져옴
        View listItemView = getLayoutInflater().inflate(R.layout.lst_items, null);
        // 가져온 뷰에서 findViewById를 사용하여 원하는 뷰를 찾음
        Button applyBtn = listItemView.findViewById(R.id.applybtn);

        layout.addView(listItemView);

        setContentView(layout);

        setContentView(R.layout.message);
        lstStockItems = findViewById(R.id.lstStockItems);
        majorRB = findViewById(R.id.majorRB);
        minorRB = findViewById(R.id.minorRB);
        doubleMajRB = findViewById(R.id.doubleMajRB);
        cultureRB = findViewById(R.id.radioButton13);
        shoppingBasketRB = findViewById(R.id.radioButton14);


        rgp = findViewById(R.id.RadioGroup);
        searchEdit = findViewById(R.id.searchlecture_no);
        aRegisteredSubjectBtn = findViewById(R.id.lectureApplyBtn);
        searchbtn = findViewById(R.id.searchbtn);

        aRegisteredSubjectBtn = findViewById(R.id.aRegisteredSubjectBtn);
        aRegisteredSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the "신청완료과목" activity
                Intent intent = new Intent(ApplicationForClasses.this, ARegisteredSubject.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });


        lecturesearch = new LectureSearch(this, lecture_no);
        applicationForClassesAdd = new ApplicationForClassesAdd();

        //검색버튼 클릭 시
        searchbtn.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if (majorRB.isChecked()) {
                    check = 1;
                }
                if (minorRB.isChecked()) {
                    check = 2;
                }
                if (doubleMajRB.isChecked()) {
                    check = 3;
                }
                if (cultureRB.isChecked()) {
                    check = 4;
                }
                if (shoppingBasketRB.isChecked()) {
                    check = 5;
                }
                Log.d("check", Integer.toString(check));
                lecturesearch.radioBtnClick(rgp, check);
                lecture_no = searchEdit.getText().toString();
                if(lecture_no.isEmpty())
                    Log.d("에딧텍스트 비어있음",lecture_no);

                if (!lecture_no.isEmpty() || (check<=5 && check>=1)) {
                    arr = lecturesearch.searchBtnClick(lecture_no, userID, check);


                    FillList(arr);

                } else {
                    searchError();
                }
            }
        });

        lstStockItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object vo = (Object)adapterView.getAdapter().getItem(position);  //리스트뷰의 포지션 내용을 가져옴.
                data = vo.toString();

                String[] data_split = data.split("lecture_no=");
                System.out.println("강의 번호 + '}' : " + data_split[1]);

                String selected_lecture_no = data_split[1].substring(0,10); // 리스트에서 선택한 강의의 강의 번호
                System.out.println("강의 번호 : " + selected_lecture_no);

                applicationForClassesAdd.setContext(ApplicationForClasses.this);

                CompletableFuture<Boolean> timetable = new CompletableFuture<>();
                timetable = applicationForClassesAdd.duplicateTimetable(userID, selected_lecture_no);

                timetable.thenAccept(time -> {
                  System.out.println("ThenAccept : " + time);

                        if (time) {
                            CompletableFuture<Boolean> excessive = new CompletableFuture<>();
                        excessive = applicationForClassesAdd.excessiveStd(selected_lecture_no);

                        excessive.thenAccept(headcount -> {
                            System.out.println("ThenAccept : " + headcount);

                            if (headcount) {
                                CompletableFuture<Boolean> appCredit = new CompletableFuture<>();
                                appCredit = applicationForClassesAdd.appCredit(userID, selected_lecture_no);

                                appCredit.thenAccept(credit -> {
                                    System.out.println("ThenAccept : " + credit);

                                    if (credit) {
                                        applicationForClassesAdd.applicationBtnClick(userID, selected_lecture_no);
                                    }

                                    else {
                                        creditError();
                                    }
                                });
                            }

                            else {
                                headCountError();
                            }
                        });

                    }
                    else {
                        timeTableError();
                    }

                });
                System.out.println("Application에서의 timetable : " + timetable);

            }
        });
    }

    // 리스트에 데이터 불러옴
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void FillList(CompletableFuture<ArrayList<HashMap<String, String>>> list) {
        try {

            List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
            String[] from = {"type", "lecture_no", "lecture_title", "professor", "time", "apply"};
            int[] views = {R.id.txtType, R.id.txtLecture_No, R.id.txtLecture_Title,
                    R.id.txtProfessor, R.id.txtTime, R.id.applybtn};

            System.out.println("2"+list);
            list.thenAccept(filllist -> {
                for (HashMap<String, String> data : filllist) {
                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("type", data.get("type"));
                    datanum.put("lecture_no", data.get("lecture_no"));
                    datanum.put("lecture_title", data.get("lecture_title"));
                    datanum.put("professor", data.get("professor"));
                    datanum.put("time", data.get("time"));
                    datanum.put("apply", "신청");
                    prolist.add(datanum);
                }
                System.out.println("data"+prolist);
                final SimpleAdapter simpleAdapter = new SimpleAdapter(ApplicationForClasses.this,
                        prolist, R.layout.lst_items, from,
                        views);
                lstStockItems.setAdapter(simpleAdapter);
            });
        } catch (Exception ex) {
            Toast.makeText(ApplicationForClasses.this, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void timeTableError() {
        //오류메시지를 띄우는 등의 코드 작성
        Toast.makeText(ApplicationForClasses.this, "강의시간표가 중복됩니다.", Toast.LENGTH_SHORT).show();
    }

    public void headCountError() {
        //오류메시지를 띄우는 등의 코드 작성
        Toast.makeText(ApplicationForClasses.this, "수강 인원이 초과되었습니다.", Toast.LENGTH_SHORT).show();
    }

    public void creditError() {
        //오류메시지를 띄우는 등의 코드 작성
        Toast.makeText(ApplicationForClasses.this, "신청할 수 있는 최대 학점을 초과했습니다.", Toast.LENGTH_SHORT).show();
    }

    public void searchError() {
        //오류메시지를 띄우는 등의 코드 작성
        Toast.makeText(ApplicationForClasses.this, "강의번호와 타입을 모두 입력해주세요..", Toast.LENGTH_SHORT).show();
    }
    
}
