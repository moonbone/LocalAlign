package edu.tau.compbio.util;

public class AlignmentPair {

	public int first,second;
	
	public AlignmentPair(int first,int second)
	{
		this.first = first;
		this.second = second;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		AlignmentPair ap = (AlignmentPair)obj;
		return this.first == ap.first && this.second == ap.second;
	}
	
}
