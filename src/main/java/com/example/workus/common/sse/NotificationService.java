package com.example.workus.common.sse;

import com.example.workus.common.exception.WorkusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class NotificationService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    /**
     * 클라이언트의 연결을 관리하기 위해 새 emitter를 emitters 리스트에 추가합니다.
     *
     * @param emitter 등록할 SseEmitter 인스턴스
     */
    public void addEmitter(SseEmitter emitter) {
        // emitter를 리스트에 추가
        emitters.add(emitter);

        // 더미 데이터 전송하여 503 에러 방지
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected!")); // 더미 데이터 전송
        } catch (Exception e) {
            // 오류가 발생한 emitter는 제거
            emitters.remove(emitter);
            // WorkusException으로 예외를 던짐
            throw new WorkusException("EMITTER_ERROR", "Emitter 오류: " + e.getMessage(), e);
        }
    }

    /**
     * 모든 emitter에 메시지를 전송합니다.
     *
     * @param message 전송할 메시지
     */
    @Transactional
    public void sendMessageToAll(String message) {
        for (SseEmitter emitter : emitters) {
            try {
                // 각 emitter에 메시지 전송
                emitter.send(message);
            } catch (Exception e) {
                // 오류가 발생한 emitter는 제거
                emitters.remove(emitter);
                // 오류 로그 기록
                log.error("Emitter 오류: {}", e.getMessage(), e);
                throw new WorkusException("EMITTER_ERROR", "Emitter 오류: " + e.getMessage(), e);
            }
        }
    }

    /**
     * 클라이언트와의 연결이 종료되었을 때 해당 emitter를 목록에서 안전하게 제거합니다.
     *
     * @param emitter 제거할 SseEmitter
     */
    public void removeEmitter(SseEmitter emitter) {
        emitters.remove(emitter);
    }
}