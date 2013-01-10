package com.adapters.am1;

import java.util.ArrayList;
import java.util.List;


import com.activities.am1.R;
import com.models.am1.Answer;
import com.models.am1.Question;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * ListAnswerAdapter
 * Created on November 26, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 */
public class ListAnswerAdapter extends BaseAdapter
{    
	/**This class is used to adapt each element of the list to specific view.
	 * Each object shown has to have only the content of the answer.
	 */
	List<Answer> answerList = new ArrayList<Answer>();
	Context context = null;
	int layoutId;
	/**
	 * Constructor with parameters used by the Adapter.
	 * @param context Context
	 * @param items List<Question>
	 * @param layoutId int 
	 */
	public ListAnswerAdapter(Context context, List<Answer> items, int layoutId){
		this.context = context;
		this.answerList = items;
		this.layoutId = layoutId;
	}
	/**
	 * Default method in which is returned the number of element in list.
	 * @return int
	 */
	public int getCount(){
		return answerList.size();//number of element in List
	}
	/**
	 * Default method in which is returned the the object at specific position in list.
	 * @param  position int
	 * @return Object
	 */
	public Object getItem(int position){
		return answerList.get(position);//return the object at specific position in list
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
	 *  From this is derived the answer parameter and this is set in the field of the answer showed at the end of the queue.
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

		TextView elementListAnswer = (TextView) view.findViewById(R.id.textView_ElementListAnswer);
		Answer answer = (Answer) answerList.get(position);
		elementListAnswer.setText(answer.getAnswer());
		return view;
	}
}



