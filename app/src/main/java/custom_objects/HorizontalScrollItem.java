package custom_objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amogh on 6/21/15.
 *
 * HorizontalScrollItem represents a horizontal row of Items, each having an ImageView and an Intent
 */

public class HorizontalScrollItem {

    public String title;
    public ArrayList<HorizontalScrollItem.Item> items;

    public HorizontalScrollItem(String title, ArrayList<HorizontalScrollItem.Item> items) {
        this.title = title;
        this.items=new ArrayList<HorizontalScrollItem.Item>(items.size());
        this.items = items;
    }

    @Override
    public String toString() {
        return "title::"+title+" size::"+items.size();
    }

    /**
     * Item is a single ImageView associated with an Intent
     */
    public static class Item {

        public int image;
        public String name;
        public String description;
        public Item(int image,String name) {
            this.image=image;
            this.name=name;
        }
        public Item(int image,String name,String description) {
            this.image=image;
            this.name=name;
            this.description=description;
        }


        @Override
        public String toString() {
            return image+" ";
        }


    }

}
