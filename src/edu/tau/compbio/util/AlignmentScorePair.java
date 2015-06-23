package edu.tau.compbio.util;

import java.util.Comparator;

public class AlignmentScorePair {

	private String[] m_alignment;
	private double m_score;
	
	
	public AlignmentScorePair(String[] alignment, double score)
	{
		
		m_alignment = alignment;
		m_score = score;
		
	}
	
	public String[] getAlignment()
	{
		return m_alignment;
	}
	
	public double getScore()
	{
		return m_score;
	}
	
	public static Comparator<AlignmentScorePair> getComparator()
	{
		return new Comparator<AlignmentScorePair>() {
			public int compare(AlignmentScorePair lhs, AlignmentScorePair rhs)
			{
				if ((lhs.m_score - rhs.m_score) == 0)
					return 0;
				return (int)(1/(lhs.m_score - rhs.m_score) + (lhs.m_score - rhs.m_score));
			}
			
		};
	}
}
