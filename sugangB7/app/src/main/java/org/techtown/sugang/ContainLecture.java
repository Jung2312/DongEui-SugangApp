package org.techtown.sugang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
public class ContainLecture extends AppCompatActivity {

    private ListView lstLecture;
    private AppCompatButton containLectureBtn;
    private AppCompatButton shoppingBasketBtn;
    private CompletableFuture<ArrayList<HashMap<String, String>>> arr = new CompletableFuture<ArrayList<HashMap<String, String>>>();

    LectureSearch lecturesearch;
    ShoppingBasketDel shopingBasketDel;

    public static ContainLecture containLecture;
    ShoppingBasket shoppingBasket = (ShoppingBasket)ShoppingBasket.shoppingBasket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_containlecture);
        List<Map<String, String>> prolist;

        containLecture = ContainLecture.this;
        if (shoppingBasket != null) {
            shoppingBasket.finish();
        }

        Intent getIntent = getIntent();
        String userID = getIntent.getStringExtra("userID");

        Log.d("ARegisteredSubject",userID);

        lstLecture = findViewById(R.id.lstLecture);
        containLectureBtn = findViewById(R.id.containLectureBtn);
        shoppingBasketBtn = findViewById(R.id.basketBtn);

        lecturesearch = new LectureSearch(this, "");
        arr = lecturesearch.searchBtnClick("", userID, 5);

        // 리스트뷰 데이터 출력 이벤트
        FillList(arr);

        // 리스트뷰 아이템 클릭 이벤트
        lstLecture.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView adapterView, View v, int position, long id){
                Log.v("CLICK", "OnClickListener" + position);

                shopingBasketDel = new ShoppingBasketDel();

                Object vo = (Object)adapterView.getAdapter().getItem(position);  //리스트뷰의 포지션 내용을 가져옴.
                String data = vo.toString();

                String[] data_split = data.split("txt_Lecture_No=");
                String selected_lecture_no = data_split[1].substring(0,10); // 리스트에서 선택한 강의의 강의 번호
                System.out.println("강의 번호 : " + selected_lecture_no);

                AlertDialog.Builder builder = new AlertDialog.Builder(ContainLecture.this);

                builder.setTitle(selected_lecture_no).setMessage("삭제하시겠습니까?");

                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        shopingBasketDel.setContext(getApplicationContext());
                        shopingBasketDel.deleteBtnClick(userID, selected_lecture_no);


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
        shoppingBasketBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), ShoppingBasket.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // 신청 완료 버튼 화면 전환
        containLectureBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            }
        });

    }

    public void FillList(CompletableFuture<ArrayList<HashMap<String, String>>> list) {
        try {

            item items = new item();
            List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
            String[] from = {"txt_Lecture_No", "txt_Lecture_title", "txt_Professor", "txt_Time", "txt_Count", "del_Btn"};
            int[] views = {R.id.txt_Lecture_No, R.id.txt_Lecture_Title,
                    R.id.txt_Professor, R.id.txt_Time, R.id.txt_Count, R.id.del_Btn};


            List<item> lsr = items.GetItems();

            list.thenAccept(filllist -> {
                for (HashMap<String, String> data : filllist) {
                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("txt_Lecture_No", data.get("lecture_no"));
                    datanum.put("txt_Lecture_title", data.get("lecture_title"));
                    datanum.put("txt_Professor", data.get("professor"));
                    datanum.put("txt_Time", data.get("time"));
                    datanum.put("txt_Count", data.get("headcount_basket"));
                    datanum.put("del_Btn", "삭제");
                    prolist.add(datanum);
                    System.out.println(datanum);
                }
                System.out.println("data"+prolist);
                final SimpleAdapter simpleAdapter = new SimpleAdapter(ContainLecture.this,
                        prolist, R.layout.list_item_basket, from,
                        views);
                lstLecture.setAdapter(simpleAdapter);
            });

            final SimpleAdapter simpleAdapter = new SimpleAdapter(ContainLecture.this,
                    prolist, R.layout.list_item_basket, from,
                    views);
            lstLecture.setAdapter(simpleAdapter);
        } catch (Exception ex) {
            Toast.makeText(ContainLecture.this, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

}