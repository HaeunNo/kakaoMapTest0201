package com.example.a20220110_midsummer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragment1_DDAY()).commit();

        navView = findViewById(R.id.navView);


        //========================================================BottomNav 클릭시 Fragment 이동 메서드
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu1: //디데이
                        // getSupportFragmentManager: 프래그먼트 속성을 바꿀 수 있는 매니저라는 객체
                        // 바꾸는 행위를 Transaction이라고 부르는데 매니저가 그 트랜잭션을 준비시키는 메서드다.
                        // replace 바꿔줄 것이다. 첫번째매개변수? 프래그먼트가 들어갈 공간의 아이디
                        // 두번째매개변수? 바꾸고 싶은 프래그먼트 객체!
                        // 그다음에 반영될 수 있도록 반드시 .commit을 붙여줘야한다.
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragment1_DDAY()).commit();
                        break;
                    case R.id.menu2: //데이트일정
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragment2()).commit();
                        break;
                    case R.id.menu3: //정혈기간
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragment3()).commit();
                        break;
                    case R.id.menu4: //먹고픈음식
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragment4()).commit();
                        break;
                    case R.id.menu5: //목표적기
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragment5()).commit();
                        break;
                }
                return true; // false면 아직 이 이벤트가 끝나지 않았다. 아직 이벤트가 안 끝났다 생각하기 때문에 ui가 바뀌지 않는다.
            }
        });
    } //★onCreate 끝

    public void onClick(View v){
        switch (v.getId()){
            case R.id.img_msg:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragment_msg()).commit();
                break;
        }
    }

}