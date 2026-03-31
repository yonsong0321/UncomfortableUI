package org.androidtown.uui.core;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import org.androidtown.uui.R;
import org.androidtown.uui.stages.stage1.Stage101Activity;
// import org.androidtown.uui.stages.stage2.Stage201Activity;
// import org.androidtown.uui.stages.stage5.Stage501Activity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> stageLauncher;

    private final int MAX_STAGES = 5;
    private int currentStage = 1;

    // 플레이어 누적 클리어 시간 (밀리초)
    private long totalTime = 0;

    // 현재 스테이지에서의 방해꾼 여부
    private boolean isSaboteur = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btn_start);
        Button btnOption = findViewById(R.id.btn_option);
        Button btnExit = findViewById(R.id.btn_exit);

        // 스테이지 결과 수신 및 다음 단계 준비 로직
        stageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // 1. 스테이지 클리어 시간 수신 및 누적
                        long stageTime = result.getData().getLongExtra("CLEAR_TIME", 0);
                        totalTime += stageTime;

                        // 2. 서버 통신: 내 기록 전송 및 다른 플레이어 대기
                        waitForOthersAndSetRoles(stageTime);
                    } else {
                        Toast.makeText(this, "스테이지 이탈", Toast.LENGTH_SHORT).show();
                        resetGame();
                    }
                }
        );

        btnStart.setOnClickListener(v -> {
            resetGame();
            // TODO: NetworkManager 방 접속 및 Ready 확인 로직
            startCurrentStage();
        });

        btnOption.setOnClickListener(v -> Toast.makeText(this, "Option", Toast.LENGTH_SHORT).show());
        btnExit.setOnClickListener(v -> {
            finishAffinity();
            System.exit(0);
        });
    }

    /**
     * 스테이지 클리어 후 모든 플레이어의 결과가 모일 때까지 대기하고,
     * 승자(시간이 가장 짧은 유저)를 판별하여 다음 스테이지의 방해꾼을 지정하는 로직
     */
    private void waitForOthersAndSetRoles(long myStageTime) {
        Toast.makeText(this, "점수 전송 완료! 다른 플레이어 대기 중...", Toast.LENGTH_SHORT).show();

        // TODO: NetworkManager를 통해 모든 유저의 기록 수신
        // if (모두 완료됨) {
        //     isSaboteur = NetworkManager.getInstance().amITheWinner();
        //     progressGame();
        // }

        // (개발용: 임시로 대기 없이 진행하며 역할 랜덤 배정)
        isSaboteur = new Random().nextBoolean();
        progressGame();
    }

    private void progressGame() {
        currentStage++;

        if (currentStage > MAX_STAGES) {
            Toast.makeText(this, "게임 종료! 최종 결과 산출 중...", Toast.LENGTH_LONG).show();
            // TODO: NetworkManager 연동 및 최종 랭킹 UI 표시
            resetGame();
            return;
        }

        startCurrentStage();
    }

    /**
     * 현재 챕터(currentStage)에 맞춰 랜덤으로 스테이지를 선택하고 Intent를 구성
     */
    private void startCurrentStage() {
        Intent intent = null;

        // 챕터별 스테이지 클래스 랜덤 추출
        Class<?> stageClass = getRandomStageClass(currentStage);

        if (stageClass != null) {
            intent = new Intent(this, stageClass);

            // 스테이지 2~4: 방해꾼 여부 전달 (팀원들은 이 값을 통해 UI를 분기 처리해야 함)
            if (currentStage >= 2 && currentStage <= 4) {
                intent.putExtra("IS_SABOTEUR", isSaboteur);
            }

            stageLauncher.launch(intent);
        } else {
            progressGame();
        }
    }

    /**
     * 챕터 번호를 입력받아 해당 챕터의 스테이지들 중 하나를 랜덤으로 반환
     */
    private Class<?> getRandomStageClass(int chapter) {
        Random random = new Random();

        switch (chapter) {
            case 1:
                // TODO: 팀원들이 제작한 1챕터 스테이지 배열 등록
                Class<?>[] chapter1Stages = {Stage101Activity.class /*, Stage102Activity.class, Stage103Activity.class*/};
                return chapter1Stages[random.nextInt(chapter1Stages.length)];

            case 2:
            case 3:
            case 4:
                // TODO: 2~4챕터(방해꾼 등장) 스테이지 배열 등록
                // Class<?>[] midStages = {Stage201Activity.class};
                // return midStages[random.nextInt(midStages.length)];
                return null; // 개발 테스트용 임시 반환

            case 5:
                // TODO: 5챕터(협동전) 스테이지 반환
                // return Stage501Activity.class;
                return null;

            default:
                return null;
        }
    }

    private void resetGame() {
        currentStage = 1;
        totalTime = 0;
        isSaboteur = false;
    }
}