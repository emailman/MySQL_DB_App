package edu.dtcc.emailman.todotodayii;

/**
 * Created by eric2 on 1/2/2017.
 *
 */

class ToDo_Item {
    private int _id;
    private  String description;
    private int is_done;

    ToDo_Item() {
    }

    ToDo_Item(int id, String desc, int done) {
        _id = id;
        description = desc;
        is_done = done;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String desc) {
        description = desc;
    }

    int getIs_done() {
        return is_done;
    }

    void setIs_done(int done) {
        is_done = done;
    }
}
