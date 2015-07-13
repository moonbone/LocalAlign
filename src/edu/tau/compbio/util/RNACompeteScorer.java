package edu.tau.compbio.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;



public class RNACompeteScorer {

	private Vector<Double> m_scores;
	private double m_normalizationFactor;
	private static final int s_k = 7;
	private static final String FUNC1_KMER = "GAAGAGC";
	private static final String FUNC2_KMER = "GCUCUUC";
	
	public RNACompeteScorer(File inputData, String proteinID) throws FileNotFoundException, IOException
	{
		m_scores = new Vector<Double>(32);
		BufferedReader lineScanner = new BufferedReader(new FileReader(inputData));
		String line = lineScanner.readLine();
		Scanner valuesScanner = new Scanner(line).useDelimiter("\\s+");
		Vector<String> titles = new Vector<String>(32);
		while( valuesScanner.hasNext() )
		{
			titles.add(valuesScanner.next());
		}
		int proteinCol = titles.indexOf(proteinID);
		if (proteinCol < 0)
		{
			throw new FileNotFoundException("Protein ID not found in RNA_compete scores file.");
		}
		while (null != (line = lineScanner.readLine()))
		{
			valuesScanner = new Scanner(line).useDelimiter("\\s+");
			for (int i=0; i<proteinCol; i++)
			{
				valuesScanner.next();
			}
			m_scores.add(valuesScanner.nextDouble());
			
		}
		
		correctFuncKmerScore(FUNC1_KMER);
		correctFuncKmerScore(FUNC2_KMER);
		setRefSequence(null);
	}
	
	private void correctFuncKmerScore(String funcKmer)
	{
		double scoreTemp = 0;
		StringBuffer sb = new StringBuffer(funcKmer);
		char funcLastChar = funcKmer.charAt(s_k-1);
		for (char c : "ACGU".toCharArray())
		{
			if (c != funcLastChar)
			{
				sb.setCharAt(s_k-1, c);
				scoreTemp += m_scores.elementAt(kMerToIndex(sb.toString()));
			}
		}
		m_scores.setElementAt(scoreTemp/3.0,kMerToIndex(funcKmer));
	}
	
	
	public void setRefSequence(String seq)
	{
		m_normalizationFactor = Collections.max(m_scores);
	}
	
	public double getScoreForShortSubsequence(String subSeq)
	{
		return (	getScoreForSubsequence(subSeq+'A') +
					getScoreForSubsequence(subSeq+'C') +
					getScoreForSubsequence(subSeq+'G') +
					getScoreForSubsequence(subSeq+'T') +
					getScoreForSubsequence('A'+subSeq) +
					getScoreForSubsequence('C'+subSeq) +
					getScoreForSubsequence('G'+subSeq) +
					getScoreForSubsequence('T'+subSeq)	)/8;								
	}
	
	public double getScoreForSubsequence(String subSeq)
	{
		subSeq = subSeq.replace("-", "");
		//TODO: add average in addition to max (2 options)
		if(subSeq.length() < s_k)
		{
			return getScoreForShortSubsequence(subSeq);
		}
		StringBuffer sb = new StringBuffer(subSeq.substring(0, s_k));
		double avgScore = m_scores.elementAt(kMerToIndex(sb.toString()));
		
		for(char c : subSeq.substring(s_k).toCharArray())
		{
			sb.append(c);
			sb.deleteCharAt(0);
			avgScore += m_scores.elementAt(kMerToIndex(sb.toString()));
		}
		
		return (avgScore / m_normalizationFactor) / (subSeq.length() + 1  - s_k) ;
	}
	
	private int kMerToIndex(String kmer)
	{
		int index = 0;
		for (char c : kmer.toCharArray())
		{
			index *= 4;
			index += baseToDigit(c);
		}
		return index;
	}
	
	private int baseToDigit(char base)
	{
		switch(base)
		{
		case 'A':
			return 0;
		case 'C':
			return 1;
		case 'G':
			return 2;
		case 'T':
		case 'U':
			return 3;
		}
		return -1;
	}
	
	private String indexToKMer(int index, int k)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<k ;i++)
		{
			sb.insert(0,digitToBase(index%4));
			index /= 4;
		}
		return sb.toString();
	}
	
	
	private char digitToBase(int digit)
	{
		switch(digit)
		{
		case 0:
			return 'A';
		case 1:
			return 'C';
		case 2:
			return 'G';
		case 3:
			return 'U';
		}
		return 0;
	}
}
