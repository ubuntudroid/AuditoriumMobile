package com.adapters.am1;

import java.util.ArrayList;
import java.util.List;


import com.activities.am1.R;
import com.models.am1.Question;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListQuestionAdapter extends BaseAdapter
{    
	//This class is used to adapt each element of the list to my specific view.
	//Each object showed have to have to fields: course and subject
	
	List<Question> questionsList = new ArrayList<Question>();
	Context context = null;
	int layoutId;

	public ListQuestionAdapter(Context context, List<Question> items, int layoutId){
		this.context = context;
		this.questionsList = items;
		this.layoutId = layoutId;
	}

	public int getCount(){
		return questionsList.size();//number of element in List
	}

	public Object getItem(int position){
		return questionsList.get(position);//return the object at specific position in list
	}

	public long getItemId(int position)
	{
		return position;//return position
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		if (convertView == null){
			//inflate assign layout to object view
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(layoutId,null);
		}
		
		TextView questionCourseShown = (TextView) view.findViewById(R.id.textViewCourse);
		TextView questionSubjectShown  = (TextView) view.findViewById(R.id.textViewTitle);
		//get the specific element from the list
		Question questionInList = questionsList.get(position);
		//setting the field about course and object
		questionCourseShown.setText(questionInList.getCourse());
		questionSubjectShown.setText(questionInList.getSubject());
		return view;
	}
}





