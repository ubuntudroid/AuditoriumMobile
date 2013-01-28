package com.activities.am1;
import com.activities.am1.R;

import java.util.ArrayList;
	 
import android.app.ListActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

/**
 * CreateQuizActivity 
 * NOT FINISHED Activity
 * Activity class used to create a Quiz 
 * Created on Januar 8th, 2013
 * @author Bernardo Plaza
 * 
 */
public class CreateQuizActivity extends ListActivity {

	/* Items are stored in this ArrayList variable */
	ArrayList<String> list = new ArrayList<String>();

	ArrayAdapter<String> adapter;
/**
 * The listView can be edited adding new items or deleting them at any time
 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.create_quiz);
		Button buttonAdd = (Button) findViewById(R.id.Button_addChoice);
        Button buttonDelete = (Button) findViewById(R.id.Button_DeleteChoices);
        Button buttonPublish = (Button) findViewById(R.id.Button_publish_quiz);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list);

		
		/* Defining a click event listener for the button "Add" */
		OnClickListener listenerAdd = new OnClickListener() {
			public void onClick(View v) {
				EditText edit = (EditText) findViewById(R.id.EditText_Choice1);
				list.add(edit.getText().toString());
				edit.setText("");
				adapter.notifyDataSetChanged();
			}
		};
		
		/* Defining a click event listener for the button "Delete" */
        OnClickListener listenerDelete = new OnClickListener() {
            public void onClick(View v) {
                /** Getting the checked items from the listView */
                SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
                int itemCount = getListView().getCount();
 
                for(int i=itemCount-1; i >= 0; i--){
                    if(checkedItemPositions.get(i)){
                        adapter.remove(list.get(i));
                    }
                }
                checkedItemPositions.clear();
                adapter.notifyDataSetChanged();
            }
        };
        
        /* Defining a click event listener for the button "Publish Quiz" */
        OnClickListener listenerPublish = new OnClickListener() {
            public void onClick(View v) {
                /** Getting the checked items from the listView */
                SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
                int itemCount = getListView().getCount();
 
                for(int i=itemCount-1; i >= 0; i--){
                    if(checkedItemPositions.get(i)){
                        
                    }
                }
                
                adapter.notifyDataSetChanged();
                
            }
        };

		buttonAdd.setOnClickListener(listenerAdd);
        buttonDelete.setOnClickListener(listenerDelete);
        buttonPublish.setOnClickListener(listenerPublish);
		setListAdapter(adapter);
	}
}
