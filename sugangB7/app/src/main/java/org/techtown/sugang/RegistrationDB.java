package org.techtown.sugang;


import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistrationDB extends StringRequest {

    final static private String URL = "https://jeong.jftt.kr/RegistrationDB.php";

    private String lecture_no;
    private String student_id;
    private String check; // check 값은 "add" 또는 "del"

    private Map<String, String> map;

    public RegistrationDB(String lecture_no, String student_id, String check, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        this.lecture_no = lecture_no;
        this.student_id = student_id;
        this.check = check;

        map = new HashMap<>();
        map.put("lecture_no", this.lecture_no);
        map.put("student_id", this.student_id);
        map.put("check", this.check);

        System.out.println("RegistrationRequest:"+ lecture_no + " / "+ student_id + check);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
