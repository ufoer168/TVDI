package Inherit;

import java.util.ArrayList;

public class ViewGroup extends View {

    ArrayList<View> views = new ArrayList();

    ViewGroup(int id, int width, int height) {
        super(id, width, height);
    }

    void addView(View v) {
        views.add(v);
    }
    
    View findViewById(int id) {
        for (View v : views) {
            if(v.id == id) {
                return v;
            }
        }
        return null;
    }

    @Override
    void show() {
        for (View v : views) {
            v.show();
        }
    }
}