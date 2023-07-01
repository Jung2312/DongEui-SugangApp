package org.techtown.sugang;


import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LectureDB extends StringRequest {

    final static private String URL = "https://jeong.jftt.kr/LectureSearch.php";

    private String lecture_no;
    private String lecture_title;
    private String professor;
    private String major;
    private String type;
    private String day;
    private String time;
    private String student_id;

    private Map<String, String> map;

    public LectureDB(String lecture_no, String student_id, int check, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        this.lecture_no = lecture_no;
        this.student_id = student_id;
        // 1 : 전공, 2 : 복수전공, 3 : 부전공, 4 : 교양, 5 : 장바구니
        map = new HashMap<>();
        map.put("lecture_no", this.lecture_no);
        map.put("student_id", this.student_id);
        map.put("check", String.valueOf(check));

    }


    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
