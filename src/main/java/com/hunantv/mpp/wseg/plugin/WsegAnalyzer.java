package com.hunantv.mpp.wseg.plugin;

import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import com.hunantv.mpp.wseg.analysis.ToAnalysis;

public class WsegAnalyzer extends Analyzer {

	boolean pstemming;
	public Set<String> filter;

	/**
	 * @param filter
	 *            停用词
	 * @param pstemming
	 *            是否分析词干
	 */
	public WsegAnalyzer(Set<String> filter, boolean pstemming) {
		this.filter = filter;
		this.pstemming = pstemming;
	}

	/**
	 * @param pstemming
	 *            是否分析词干.进行单复数,时态的转换
	 */
	public WsegAnalyzer(boolean pstemming) {
		this.pstemming = pstemming;
	}

	public WsegAnalyzer() {
		super();
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName, final Reader reader) {
		Tokenizer tokenizer = new WsegTokenizer(new ToAnalysis(reader), reader, filter, pstemming);
		return new TokenStreamComponents(tokenizer);
	}

}
