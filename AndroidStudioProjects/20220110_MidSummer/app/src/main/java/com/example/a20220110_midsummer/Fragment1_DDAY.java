package com.example.a20220110_midsummer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

// D-DAY, 기념일에 알려주기
public class Fragment1_DDAY extends Fragment {

    // 날짜 입력 기능 변수
    private TextView tv_date;
    private Button btn_inputDate;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    // 오늘 날짜 변수
    private int sys_year;
    private int sys_month;
    private int sys_day;

    // sp에 담긴 날짜 변수
    private int year;
    private int month;
    private int day;

    // D-DAY 기능 변수
    private TextView tv_dday;
    private int dDay;

    // 기념일 기능




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_1, container, false);

        //=======================================================================-sp 만들기 midSummer
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("midSummer", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = sharedPreferences.edit();

        //========================================================================현재 날짜 변수에 담기
        sys_year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        sys_month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        sys_day = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        //==============================================================================findViewById
        tv_date = v.findViewById(R.id.tv_date);
        btn_inputDate = v.findViewById(R.id.btn_dateInput);
        tv_dday = v.findViewById(R.id.tv_dday);

        //==================================================================sp에 저장된 날짜값 불러오기
        int year = sharedPreferences.getInt("f1_year", sys_year);
        int month = sharedPreferences.getInt("f1_month", sys_month);
        int day = sharedPreferences.getInt("f1_day", sys_day);

        //=============================================================================날짜값 setText
        tv_date.setText(year+ "년 " + (month+ 1) + "월 " + day+ "일");

        //====================================================sp에 저장된 날짜값이 있다면, D-DAY setText
        dDay = sharedPreferences.getInt("f1_dDay",-999999999);
        if(dDay != -999999999){
            tv_dday.setText(dDay+"");
        }

        //===============================================================날짜선택 Dialog에서 확인 클릭시
        // - tv_date, tv_dday를 변한 날짜로 setText
        // - D-DAY 계산은 여기서만 한다. 그 후, sp에 담아서 사용 (진입 전엔 tv_dday Text 표시되지 않음)
        // - sp에 시작날짜, D-DAY put하기
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                tv_date.setText(y + "년 " + (m + 1) + "월 " + d + "일");

                // D-DAY 계산
                Date sysDate = new Date(sys_year,(sys_month-1),sys_day);
                Date starDate = new Date(y, m, d);
                double gapDate = sysDate.getTime() - starDate.getTime();
                double gapDay = Math.ceil(gapDate/(60*1000*60*24));
                tv_dday.setText((int)gapDay+"");

                // 선택 날짜 저장
                edt.putInt("f1_year", y);
                edt.putInt("f1_month", m);
                edt.putInt("f1_day", d);
                edt.putInt("f1_dDay", (int)gapDay);
                edt.commit();

            }
        };

        //========================================================================날짜 선택 버튼 클릭시
        // - DatePickerDialog 생성
        btn_inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), onDateSetListener,
                        sharedPreferences.getInt("f1_year", sys_year),
                        sharedPreferences.getInt("f1_month", sys_month),
                        sharedPreferences.getInt("f1_day", sys_day));
                datePickerDialog.show();
            }
        });

        //================================================================================기념일 기능
        // - 다이얼로그 2개
        // - 1. "600일 축하합니다!" ( 한줄작성, 메세지 확인, 뒤로가기 ) , 애인이 먼저 한줄 작성해놨으면 그게 화면에 먼저 뜬다.
        // ex) "600일이라니 사랑해!" or "애인에게 먼저 메세지를 남겨보세요!"
        // - 작성! 누르면 FireBase로 전송!
        // - 2. 메세지 확인 누를시, 메세지함 프래그먼트로 이동한다.

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("추카추카");
            builder.setNegativeButton("아니오", null);
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("두번째 다이얼");
                    builder.setNegativeButton("두번째아니오", null);
                    builder.setPositiveButton("두번째네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(),"세번째반응",Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.create().show();
                }
            });
            builder.create().show();

        return v;
    }
}