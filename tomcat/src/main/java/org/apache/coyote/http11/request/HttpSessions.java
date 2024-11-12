package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;

/**
 * 세션들을 저장해 놓는 세션 저장소이다.
 * static 을 활용해 메모리에서 관리한다.
 */
public class HttpSessions {

    private static Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

    public static HttpSession getOrCreateSession(String sessionId) {
        HttpSession session = sessions.get(sessionId);

        if (session == null) {
            session = new HttpSession(sessionId);
            sessions.put(sessionId, session);
            return session;
        }

        return session;
    }

    public static void remove(String id) {
        sessions.remove(id);
    }
}
