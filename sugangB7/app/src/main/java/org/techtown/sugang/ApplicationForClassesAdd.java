package org.techtown.sugang;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : ApplicationForClassesAdd.java
//  @ Date : 2023-05-28
//  @ Author : 
//
//

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

public class ApplicationForClassesAdd {
	private String addLectureNo;
	private String addStdId;
	private Context context;


	RegistrationDB registrationAddRequest;
	DuplicateTimetableDB duplicateTimetableRequest;

	public void setContext(Context context) {
		this.context = context;
	}

	public void applicationBtnClick(String StdId, String Lecture_no) {
		addStdId = StdId;
		addLectureNo = Lecture_no;

		//boolean excressiveStd = registrationDB.excressiveStdCheck();
		//boolean appCredit = registrationDB.appCreditCheck();
		//if (excressiveStd == true || appCredit == true){


		System.out.println("수강신청 / addLectureNo : " + addLectureNo + ", addStdId : " + addStdId);
		Response.Listener<String> responseListener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {}
		};

		registrationAddRequest = new RegistrationDB(this.addLectureNo, this.addStdId, "Add", responseListener);
		RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
		queue.add(registrationAddRequest);
		System.out.println("response 1");

		Toast.makeText(context, "신청되었습니다.", Toast.LENGTH_SHORT).show();

	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	public CompletableFuture<Boolean> duplicateTimetable(String StdId, String Lecture_no) {
		addStdId = StdId;
		addLectureNo = Lecture_no;

		CompletableFuture<Boolean> future = new CompletableFuture<>();
		Response.Listener<String> responseListener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					Boolean timetable = jsonObject.getBoolean("timetable");
					System.out.println("timetable 2: " + timetable);

					future.complete(timetable);

				} catch (JSONException e) {
					System.out.println("timetable 실패: ");
					e.printStackTrace();
				}

			}
		};

		duplicateTimetableRequest = new DuplicateTimetableDB(this.addStdId, this.addLectureNo, responseListener);
		RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
		queue.add(duplicateTimetableRequest);

		System.out.println();
		return future;
	}


	@RequiresApi(api = Build.VERSION_CODES.N)
	public CompletableFuture<Boolean> excessiveStd(String Lecture_no) {
		addLectureNo = Lecture_no;

		CompletableFuture<Boolean> future = new CompletableFuture<>();
		Response.Listener<String> responseListener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					Boolean headcount = jsonObject.getBoolean("headcount");
					System.out.println("headcount: " + headcount);

					future.complete(headcount);

				} catch (JSONException e) {
					System.out.println("excessive 실패: ");
					e.printStackTrace();
				}

			}
		};

		ExcessiveStdDB excessiveStdRequest = new ExcessiveStdDB(this.addLectureNo, responseListener);
		RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
		queue.add(excessiveStdRequest);

		System.out.println();
		return future;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	public CompletableFuture<Boolean> appCredit(String student_id, String Lecture_no) {
		addStdId = student_id;
		addLectureNo = Lecture_no;

		CompletableFuture<Boolean> future = new CompletableFuture<>();
		Response.Listener<String> responseListener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					Boolean credit = jsonObject.getBoolean("credit");
					System.out.println("credit: " + credit);

					future.complete(credit);

				} catch (JSONException e) {
					System.out.println("credit 실패: ");
					e.printStackTrace();
				}

			}
		};

		AppCreditDB appCreditRequest = new AppCreditDB(this.addStdId, this.addLectureNo, responseListener);
		RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
		queue.add(appCreditRequest);

		System.out.println();
		return future;
	}
}
