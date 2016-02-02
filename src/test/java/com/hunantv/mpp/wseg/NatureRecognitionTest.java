package com.hunantv.mpp.wseg;

import java.util.List;

import org.junit.Test;

import com.hunantv.mpp.wseg.analysis.ToAnalysis;
import com.hunantv.mpp.wseg.recognition.NatureRecognition;
import com.hunantv.mpp.wseg.segment.Term;

/**
 * 词性标注的一个例子
 * 
 * @author ansj
 * 
 */
public class NatureRecognitionTest {
	
	@Test
	public void test(){
		String str = "结婚的和尚未结婚的孙建是一个好人";
		List<Term> terms = ToAnalysis.parse(str);
		new NatureRecognition(terms).recognition();
		System.out.println(terms);
	}
}
