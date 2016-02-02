package com.hunantv.mpp.wseg.recognition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hunantv.mpp.wseg.dict.Nature;
import com.hunantv.mpp.wseg.segment.Term;

/**
 * 电子邮箱抽取
 * 
 * @author ansj
 *
 */
public class EmailRecognition {
	protected static final Nature EMAIL_NATURE = new Nature("email");

	protected static Set<String> FEATURE_SET = new HashSet<String>();

	private static Map<String, String> FEATURE = new HashMap<String, String>();

	private static final String NOT_HEAD = "NOT";
	private static final String NATURE_HEAD = "nature:";
	private static final String ALL = "ALL";

	static {
		FEATURE.put("-", NOT_HEAD);
		FEATURE.put("_", NOT_HEAD);
		FEATURE.put(".", NOT_HEAD);
		FEATURE.put(NATURE_HEAD + "en", ALL);
		FEATURE.put(NATURE_HEAD + "m", ALL);

	}

	public static List<Term> recognition(List<Term> terms) {

		for (Term term : terms) {
			if (!"@".equals(term.getName())) {
				continue;
			}

		}

		for (Iterator<Term> iterator = terms.iterator(); iterator.hasNext();) {
			Term term = (Term) iterator.next();
			if (term.getName() == null) {
				iterator.remove();
			}
		}

		return terms;
	}
}
