package com.hunantv.mpp.wseg.plugin;


import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import com.hunantv.mpp.wseg.analysis.IndexAnalysis;

public class WsegIndexAnalyzer extends Analyzer {

	boolean pstemming;
	public Set<String> filter;

	/**
	 * @param filter
	 *            停用词
	 * @param pstemming
	 *            是否分析词干
	 */
	public WsegIndexAnalyzer(Set<String> filter, boolean pstemming) {
		this.filter = filter;
	}

	/**
	 * @param pstemming
	 *            是否分析词干.进行单复数,时态的转换
	 */
	public WsegIndexAnalyzer(boolean pstemming) {
		this.pstemming = pstemming;
	}

	public WsegIndexAnalyzer() {
		super();
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName, final Reader reader) {
		// TODO Auto-generated method stub
		Tokenizer tokenizer = new WsegTokenizer(new IndexAnalysis(reader), reader, filter, pstemming);
		return new TokenStreamComponents(tokenizer);
	}

}
