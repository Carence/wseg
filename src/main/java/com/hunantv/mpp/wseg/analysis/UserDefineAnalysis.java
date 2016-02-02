package com.hunantv.mpp.wseg.analysis;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Forest;

import com.hunantv.mpp.wseg.dict.UserDefineLibrary;
import com.hunantv.mpp.wseg.recognition.AsianPersonRecognition;
import com.hunantv.mpp.wseg.recognition.ForeignPersonRecognition;
import com.hunantv.mpp.wseg.recognition.NameFix;
import com.hunantv.mpp.wseg.recognition.NumRecognition;
import com.hunantv.mpp.wseg.recognition.UserDefineRecognition;
import com.hunantv.mpp.wseg.rock.MyStaticValue;
import com.hunantv.mpp.wseg.segment.FilterModifWord;
import com.hunantv.mpp.wseg.segment.Graph;
import com.hunantv.mpp.wseg.segment.Term;
import com.hunantv.mpp.wseg.utils.MyReader;

/**
 * 默认用户自定义词性优先
 * 
 * @author ansj
 *
 */
public class UserDefineAnalysis extends Analysis {

	@Override
	protected List<Term> getResult(final Graph graph) {

		Merger merger = new Merger() {
			@Override
			public List<Term> merger() {

				// 用户自定义词典的识别
				userDefineRecognition(graph, forests);

				graph.walkPath();

				// 数字发现
				if (MyStaticValue.isNumRecognition && graph.hasNum) {
					NumRecognition.recognition(graph.terms);
				}

				// 姓名识别
				if (graph.hasPerson && MyStaticValue.isNameRecognition) {
					// 亚洲人名识别
					new AsianPersonRecognition(graph.terms).recognition();
					graph.walkPathByScore();
					NameFix.nameAmbiguity(graph.terms);
					// 外国人名识别
					new ForeignPersonRecognition(graph.terms).recognition();
					graph.walkPathByScore();
				}

				return getResult();
			}

			private void userDefineRecognition(final Graph graph, Forest... forests) {
				new UserDefineRecognition(graph.terms, 1, forests).recognition();
				graph.rmLittlePath();
				graph.walkPathByScore();
			}

			private List<Term> getResult() {
				// TODO Auto-generated method stub
				List<Term> result = new ArrayList<Term>();
				int length = graph.terms.length - 1;
				for (int i = 0; i < length; i++) {
					if (graph.terms[i] != null) {
						result.add(graph.terms[i]);
					}
				}
				setRealName(graph, result);

				FilterModifWord.modifResult(result);
				return result;
			}
		};
		return merger.merger();
	}

	private UserDefineAnalysis() {
	};

	/**
	 * 用户自己定义的词典
	 * 
	 * @param forest
	 */
	public UserDefineAnalysis(Forest... forests) {
		if (forests == null) {
			forests = new Forest[] { UserDefineLibrary.FOREST };
		}
		this.forests = forests;
	}

	public UserDefineAnalysis(BufferedReader reader, Forest... forests) {
		this.forests = forests;
		super.resetContent(new MyReader(reader));
	}

	public static List<Term> parse(String str) {
		return new UserDefineAnalysis().parseStr(str);
	}

	public static List<Term> parse(String str, Forest... forests) {
		return new UserDefineAnalysis(forests).parseStr(str);
	}
}
