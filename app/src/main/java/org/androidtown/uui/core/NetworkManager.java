package org.androidtown.uui.core;

import android.content.Context;

/**
 * Firebase 통신 및 멀티플레이 방(Room) 관리를 담당할 네트워크 클래스 (구현 예정)
 */
public class NetworkManager {

    private static NetworkManager instance;

    // 싱글톤 패턴: 외부에서 객체를 함부로 생성하지 못하게 막음
    private NetworkManager() {
    }

    // 앱 전체에서 단 하나의 NetworkManager 인스턴스만 사용하도록 보장
    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    // TODO: Firebase 초기화 로직 작성
    public void initialize(Context context) {
        // FirebaseApp.initializeApp(context);
    }

    // TODO: 멀티플레이 방 생성 로직 (방장용)
    public void createRoom(String hostName) {
    }

    // TODO: 멀티플레이 방 입장 로직 (팀원용)
    public void joinRoom(String roomCode, String playerName) {
    }

    // TODO: 스테이지 클리어 후 내 점수(시간)를 서버에 동기화
    public void updateScore(int playerIndex, long score) {
    }

    // TODO: 다른 플레이어들이 스테이지를 깰 때까지 대기/확인하는 로직
    public void checkAllPlayersReady() {
    }
}