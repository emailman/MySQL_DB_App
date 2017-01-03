package edu.dtcc.emailman.todotodayii;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected DBHelper mDBHelper;
    private List<ToDo_Item> list;
    private MyAdapter adapt;
    private EditText myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myTask = (EditText) findViewById(R.id.editText1);
        mDBHelper = new DBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = mDBHelper.getAllTasks();
        adapt = new MyAdapter(this, R.layout.todo_item, list);
        ListView listTask = (ListView) findViewById(R.id.listView1);
        listTask.setAdapter(adapt);
    }

    // Handler for the ADD TASK button
    public void addTaskNow(View view) {
        String s = myTask.getText().toString();
        if (s.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "A TODO task must be entered!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            ToDo_Item task = new ToDo_Item(0, s, 0);
            mDBHelper.addToDoItem(task);

            myTask.setText("");
            adapt.add(task);
            adapt.notifyDataSetChanged();
        }
    }

    //Handler for the CLEAR LIST button
    public void clearTasks(View view) {
        mDBHelper.clearAll(list);
        adapt.notifyDataSetChanged();
    }

    // *** ADAPTER ***
    private class MyAdapter extends ArrayAdapter<ToDo_Item> {
        Context context;
        List<ToDo_Item> taskList = new ArrayList<>();

        MyAdapter(Context c, int rId, List<ToDo_Item> objects) {
            super(c, rId, objects);
            taskList = objects;
            context = c;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            CheckBox isDoneChBx;
            if (convertView == null) {
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.todo_item, parent, false);
                isDoneChBx = (CheckBox) convertView.findViewById(R.id.chkStatus);
                convertView.setTag(isDoneChBx);

                isDoneChBx.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CheckBox cb = (CheckBox) view;
                                ToDo_Item changeTask =(ToDo_Item) cb.getTag();
                                changeTask.setIs_done(cb.isChecked() ? 1 : 0);
                                mDBHelper.updateTask(changeTask);
                            }
                        }
                );
            }
            else {
                isDoneChBx = (CheckBox) convertView.getTag();
            }

            ToDo_Item current = taskList.get(position);
            isDoneChBx.setText(current.getDescription());
            isDoneChBx.setChecked(current.getIs_done() == 1);
            isDoneChBx.setTag(current);

            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
