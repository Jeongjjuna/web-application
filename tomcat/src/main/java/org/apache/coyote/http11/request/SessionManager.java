package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 세션들을 저장해 놓는 세션 저장소이다.
 * static 을 활용해 메모리에서 관리한다.
 */
public class SessionManager {

    private static Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession create() {
        String sessionId = UUID.randomUUID().toString();
        HttpSession session = new HttpSession(sessionId);
        sessions.put(sessionId, session);
        return session;
    }

    public static void remove(String id) {
        sessions.remove(id);
    }

    public static boolean isLogin(String jSessionId) {
        return sessions.get(jSessionId) != null;
    }
}
