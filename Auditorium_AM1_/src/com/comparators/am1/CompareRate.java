package com.comparators.am1;
import java.util.Comparator;

import com.models.am1.Question;

public class CompareRate implements Comparator<Question> {
	public int compare(Question q0, Question q1) {
		// TODO Auto-generated method stub
		return q0.getNumberOfRates();//.compareToIgnoreCase(q1.getNumberOfRates());
	}

}


 