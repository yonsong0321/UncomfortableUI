package org.androidtown.uui.stages.stage5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.androidtown.uui.R;

public class Stage501Activity extends AppCompatActivity {

    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage101);

        // 시간 측정 시작
        startTime = System.currentTimeMillis();

        Button btnClear = findViewById(R.id.s101_btn_clear);

        // TODO: UI 조작 및 스테이지 로직 구현 위치

        // 클리어 조건 달성 시 호출
        btnClear.setOnClickListener(v -> {
            clearStage();
        });
    }

    private void clearStage() {
        long endTime = System.currentTimeMillis();
        long clearTime = endTime - startTime;

        Intent resultIntent = new Intent();
        resultIntent.putExtra("CLEAR_TIME", clearTime);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // 게임 진행 중 뒤로가기 버튼 비활성화
        // 필요 시 super.onBackPressed(); 활성화
    }
}