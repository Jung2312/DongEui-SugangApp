package org.techtown.sugang;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ARegisteredSearch {

    private String student_id;
    private ArrayList<HashMap<String, String>> arr;
    private ARegisteredPrint aregisteredPrint;
    private Context context;

    ARegisteredSearch(Context context, String student_id){
        this.student_id = student_id;
        this.context = context;
    }

    public CompletableFuture<ArrayList<HashMap<String, String>>> aRegisteredSubjectbtnClick(String userID) {
        this.student_id = userID;
        Log.d("***********student_id", student_id);
        CompletableFuture<ArrayList<HashMap<String, String>>> future = new CompletableFuture<>();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("response 1", "접근");
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("response 2", "접근");
                    arr = new ArrayList<HashMap<String, String>>();
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String json = jsonObject.toString();
                        Log.v("msg", json);
                        String type = jsonObject.getString("type");
                        String lecture_no = jsonObject.getString("lecture_no");
                        String lecture_title = jsonObject.getString("lecture_title");
                        String professor = jsonObject.getString("professor");
                        String time = jsonObject.getString("time");

                        HashMap<String, String> arrMap = new HashMap<String, String>();
                        arrMap.put("lecture_no", lecture_no);
                        arrMap.put("lecture_title", lecture_title);
                        arrMap.put("professor", professor);
                        arrMap.put("type", type);
                        arrMap.put("time", time);

                        arr.add(arrMap);
                    }
                    future.complete(arr);

                    System.out.println(future);
                    Log.d("notice", "for 문 시작 후");
                    Log.d("jsonArray.length", Integer.toString(jsonArray.length()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Log.d("response 끝", "접근");
        aregisteredPrint = new ARegisteredPrint(student_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        queue.add(aregisteredPrint);
        return future;
    }
}