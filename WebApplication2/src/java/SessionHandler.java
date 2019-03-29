
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.Session;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vrtex
 */
public class SessionHandler {
    private static final HashMap<String, HashSet<Session>> sessions = new HashMap<>();
    
    public static void addSession(Session s, String roomid)
    {
        if(!sessions.containsKey(roomid))
            sessions.put(roomid, new HashSet<>());
        
        sessions.get(roomid).add(s);
    }
    
    public static void removeSession(Session s)
    {
        for(String k : sessions.keySet())
            sessions.get(k).remove(s);
    }
    
    public static void sendToSession(Session s, String msg) throws IOException
    {
        s.getBasicRemote().sendText(msg);
    }
    
    public static void sendToAll(String msg) throws IOException
    {        
        for(String k : sessions.keySet())
            for(Session s : sessions.get(k))
                s.getBasicRemote().sendText(msg);
    }
    
    public static void sendToRoom(String msg, String room) throws IOException
    {
        for(String k : sessions.keySet())
            if(!k.equals(room))
                continue;
            else
            for(Session s : sessions.get(k))
                s.getBasicRemote().sendText(msg);
    }
    
}
