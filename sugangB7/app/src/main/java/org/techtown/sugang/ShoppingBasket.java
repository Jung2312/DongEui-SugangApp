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
public class ShoppingBasket extends AppCompatActivity {

    public static ShoppingBasket shoppingBasket;

    ContainLecture containLecture = (ContainLecture) ContainLecture.containLecture;

    private ListView lstStockItems;
    private RadioButton rbtn1;
    private RadioButton rbtn2;
    private RadioButton rbtn3;
    private RadioButton rbtn4;
    private RadioGroup rgp;
    private EditText et;
    private Button btn;
    private Button searchbtn;



    LectureSearch lecturesearch;
    ShoppingBasketAdd shoppingBasketAdd;
    private int check = 0;
    private String lecture_no = "1";
    private String data;

    private CompletableFuture<ArrayList<HashMap<String, String>>> arr = new CompletableFuture<ArrayList<HashMap<String, String>>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        shoppingBasket = ShoppingBasket.this;
        if (containLecture != null) {
            containLecture.finish();
        }

        Intent getIntent = getIntent();
        String userID = getIntent.getStringExtra("userID");

        Log.d("ShoppingBasket",userID);

        // LinearLayout을 최상위 뷰 그룹으로 사용하는 경우
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        // 첫 번째 XML 파일의 내용을 inflate하여 추가
        View firstView = getLayoutInflater().inflate(R.layout.list_item, null);
        layout.addView(firstView);

        // 별도의 XML 파일을 inflate하여 해당 뷰를 가져옴
        View listItemView = getLayoutInflater().inflate(R.layout.list_item, null);
        // 가져온 뷰에서 findViewById를 사용하여 원하는 뷰를 찾음
        Button applicationBtn = listItemView.findViewById(R.id.applicationBtn);

        layout.addView(listItemView);

        setContentView(layout);

        setContentView(R.layout.activity_basket);
        lstStockItems = findViewById(R.id.lstStockItems);
        rbtn1 = findViewById(R.id.majorRB);
        rbtn2 = findViewById(R.id.minorRB);
        rbtn3 = findViewById(R.id.doubleMajRB);
        rbtn4 = findViewById(R.id.radioButton13);
        rgp = findViewById(R.id.RadioGroup);
        et = findViewById(R.id.searchlecture_no);
        btn = findViewById(R.id.shoppingBasketBtn);
        searchbtn = findViewById(R.id.searchbtn);


        btn = findViewById(R.id.containLectureBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the "담은과목" activity
                Intent intent = new Intent(ShoppingBasket.this, ContainLecture.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        // ApplicationForClassesAdd 토스트 메시지 출력
        applicationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lectureNo = "123"; // 선택된 강의 번호를 가져와야 함.

                ShoppingBasketAdd shoppingBasketAdd = new ShoppingBasketAdd();
                shoppingBasketAdd.setContext(ShoppingBasket.this);
                shoppingBasketAdd.applicationBtnClick(userID, lectureNo);
            }
        });

        lecturesearch = new LectureSearch(this, lecture_no);
        shoppingBasketAdd = new ShoppingBasketAdd();

        //검색버튼 클릭 시
        searchbtn.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if (rbtn1.isChecked()) {
                    check = 1;
                }
                if (rbtn2.isChecked()) {
                    check = 2;
                }
                if (rbtn3.isChecked()) {
                    check = 3;
                }
                if (rbtn4.isChecked()) {
                    check = 4;
                }

                Log.d("check", Integer.toString(check));
                lecturesearch.radioBtnClick(rgp, check);
                lecture_no = et.getText().toString();
                if(lecture_no.isEmpty())
                    Log.d("에딧텍스트 비어있음",lecture_no);

                if (!lecture_no.isEmpty() || (check<=4 && check>=1)) {
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

                shoppingBasketAdd.setContext(ShoppingBasket.this);
                shoppingBasketAdd.applicationBtnClick(userID, selected_lecture_no);

            }
        });
    }

    // 리스트에 데이터 불러옴
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void FillList(CompletableFuture<ArrayList<HashMap<String, String>>> list) {
        try {

            List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
            String[] from = {"type", "lecture_no", "lecture_title", "professor", "time", "application"};
            int[] views = {R.id.txtType, R.id.txtLecture_No, R.id.txtLecture_Title,
                    R.id.txtProfessor, R.id.txtTime, R.id.applicationBtn};

            System.out.println("2"+list);
            list.thenAccept(filllist -> {
                for (HashMap<String, String> data : filllist) {
                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("type", data.get("type"));
                    datanum.put("lecture_no", data.get("lecture_no"));
                    datanum.put("lecture_title", data.get("lecture_title"));
                    datanum.put("professor", data.get("professor"));
                    datanum.put("time", data.get("time"));
                    datanum.put("application", "신청");
                    prolist.add(datanum);
                }
                System.out.println("data"+prolist);
                final SimpleAdapter simpleAdapter = new SimpleAdapter(ShoppingBasket.this,
                        prolist, R.layout.list_item, from,
                        views);
                lstStockItems.setAdapter(simpleAdapter);
            });
        } catch (Exception ex) {
            Toast.makeText(ShoppingBasket.this, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void searchError() {
        //오류메시지를 띄우는 등의 코드 작성
        Toast.makeText(ShoppingBasket.this, "강의번호와 타입을 모두 입력해주세요..", Toast.LENGTH_SHORT).show();
    }

}