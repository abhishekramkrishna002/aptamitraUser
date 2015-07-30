package custom_objects;

/**
 * Created by abhishek on 25-07-2015.
 */
public class ChatMessage {

    public ChatMessage(String jsonMessage, boolean userPosted) {
        this.jsonMessage = jsonMessage;
        this.userPosted = userPosted;
    }

    public String jsonMessage;
    public boolean userPosted;

    @Override
    public String toString() {
        return jsonMessage+" userPosted::"+userPosted;
    }
}
