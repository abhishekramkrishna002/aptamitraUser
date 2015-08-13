package entities;

import com.orm.SugarRecord;

/**
 * Created by abhishek on 24-07-2015.
 */
public class MyNotification extends SugarRecord<MyNotification> {

    public String json;
    public MyNotification(String json)
    {
        this.json=json;
    }
}
