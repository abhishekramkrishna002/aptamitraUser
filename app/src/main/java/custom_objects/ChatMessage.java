package custom_objects;

/**
 * Created by abhishek on 25-07-2015.
 */
public class ChatMessage {


    public String message;
    public boolean userPosted;
    public boolean sent;

    public ChatMessage(String message, boolean userPosted) {
        this.message = message;
        this.userPosted = userPosted;
        this.sent=false;
    }
    public void setSent()
    {
            this.sent=true;
    }


    @Override
    public String toString() {
        return message + " userPosted::" + userPosted;
    }
}
