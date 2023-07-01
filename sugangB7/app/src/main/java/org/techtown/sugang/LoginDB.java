package org.techtown.sugang;


import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginDB extends StringRequest {

    final static private String URL = "https://jeong.jftt.kr/Stdlogin.php";

    private String id;
    private String password;
    private Map<String, String> map;

    public LoginDB(String id, String password, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        this.id = id;
        this.password = password;

        map = new HashMap<>();
        map.put("student_id", this.id);
        map.put("password", this.password);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
