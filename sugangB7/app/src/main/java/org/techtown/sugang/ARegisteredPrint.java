package org.techtown.sugang;

import androidx.annotation.Nullable;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.android.volley.AuthFailureError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ARegisteredPrint extends StringRequest {

    final static private String URL= "https://jeong.jftt.kr/ARegisteredPrint.php";

    private String lecture_no;
    private String lecture_title;
    private String professor;
    private String major;
    private String type;
    private String day;
    private String time;
    private String student_id;

    private Map<String, String> map;

    public ARegisteredPrint(String student_id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        this.student_id = student_id;

        map = new HashMap<>();
        map.put("student_id", this.student_id);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
