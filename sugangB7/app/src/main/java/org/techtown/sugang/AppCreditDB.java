package org.techtown.sugang;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AppCreditDB extends StringRequest {

    final static private String URL = "https://jeong.jftt.kr/appCredit.php";

    private String student_id;
    private String lecture_no;
    private Map<String, String> map;

    public AppCreditDB(String student_id, String lecture_no, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        this.student_id = student_id;
        this.lecture_no = lecture_no;

        map = new HashMap<>();
        map.put("student_id", this.student_id);
        map.put("lecture_no", this.lecture_no);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
