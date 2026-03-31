package org.androidtown.uui.utils;

import android.content.Context;

/**
 * 게임 내 배경음악(BGM)과 효과음(SFX)을 관리할 사운드 클래스 (구현 예정)
 */
public class SoundManager {

    private static SoundManager instance;

    // 싱글톤 패턴
    private SoundManager() {
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    // TODO: SoundPool(효과음용) 및 MediaPlayer(BGM용) 초기화
    public void initialize(Context context) {
    }

    // TODO: 배경음악 재생 로직
    public void playBGM(int audioResId) {
    }

    // TODO: 효과음 재생 로직 (예: 에러 소리, 비웃는 소리 등)
    public void playSFX(int audioResId) {
    }

    // TODO: 배경음악 일시 정지 로직
    public void pauseBGM() {
    }

    // TODO: 메모리 누수 방지를 위한 모든 소리 자원 해제 로직
    public void release() {
    }
}