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
/**
 * ListQuestionAdapter
 * Created on November 18, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 */
public class ListQuestionAdapter extends BaseAdapter
{    
	/**This class is used to adapt each element of the list to specific view.
	 * Each object shown has to have two fields: course and subject.
	 */
	List<Question> questionsList = new ArrayList<Question>();
	Context context = null;
	int layoutId;
	/**
	 * Constructor with parameters used by the Adapter.
	 * @param context Context
	 * @param items List<Question>
	 * @param layoutId int 
	 */
	public ListQuestionAdapter(Context context, List<Question> items, int layoutId){
		this.context = context;
		this.questionsList = items;
		this.layoutId = layoutId;
	}
	/**
	 * Default method in which is returned the number of element in list.
	 * @return int
	 */
	public int getCount(){
		return questionsList.size();//number of element in List
	}
	/**
	 * Default method in which is returned the the object at specific position in list.
	 * @param  position int
	 * @return Object
	 */
	public Object getItem(int position){
		return questionsList.get(position);//return the object at specific position in list
	}
	/**
	 * Default method in which is returned position of the element in list.
	 * @param position int 
	 * @return long
	 */

	public long getItemId(int position)
	{
		return position;//return position
	}

	/**
	 * In this method as a first thing there is a control in which is a assigned the layout to object view by the use of inflate
	 *  and as a second one is taken the specific element from the list.
	 *  From this is derived the course parameter  and the subject parameter and those are set in the field of the question showed.
	 * @param int , View , ViewGroup 
	 * @return View
	 */
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





