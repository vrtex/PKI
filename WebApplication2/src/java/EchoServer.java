
import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vrtex
 */
@ServerEndpoint("/echo/{roomnumber}")
public class EchoServer {
    @OnOpen
    public void onOpen(Session session, @PathParam("roomnumber") final String roomid){
        SessionHandler.addSession(session, roomid);
        session.getUserProperties().put("roomid", String.valueOf(roomid));
        System.out.println(session.getId() + " has opened a connection"); 
        try {
            SessionHandler.sendToSession(session, "Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("Message from " + session.getId() + ": " + message);
        try {
            String room = (String) session.getUserProperties().get("roomid");
            SessionHandler.sendToRoom(message, room);
            // session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @OnClose
    public void onClose(Session session){
        System.out.println("Session " +session.getId()+" has ended");
        SessionHandler.removeSession(session);
    }
}
