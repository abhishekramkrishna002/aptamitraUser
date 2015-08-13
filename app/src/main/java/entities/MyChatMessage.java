package entities;

import com.orm.SugarRecord;

/**
 * Created by abhishek on 24-07-2015.
 */
public class MyChatMessage extends SugarRecord<MyChatMessage> {

    public String message;
    public String domain;
    public String servieName;
    public MyChatMessage(String domain,String serviceName,String message)
    {
        this.domain=domain;
        this.servieName=serviceName;
        this.message=message;
    }
}
