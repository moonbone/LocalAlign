package com.ibm.compbio.seqalign;

import com.ibm.compbio.Cell;

public class MultipleCusomSW extends CustomSmithWaterman {

	private String[] sequence1;
	private String[] sequence2;
	
	public MultipleCusomSW(String[] sequence1, String[] sequence2,double[] probs1,double[] probs2) {
		super(sequence1[0], sequence2[0],probs1,probs2);
		this.sequence1 = sequence1;
		this.sequence2 = sequence2;
	}

	public MultipleCusomSW(String[] sequence1, String[] sequence2, int match,
			int mismatch, int gap, double offset,double probRatio, double[] probs1,double[] probs2) {
		super(sequence1[0], sequence2[0], match, mismatch, gap,offset,probRatio,probs1,probs2);
		this.sequence1 = sequence1;
		this.sequence2 = sequence2;
	}

	@Override
	protected double getProbabilityScoreRow(Cell c)
	{
		// if gap is already introduced at this location we do not add to the penalty
		// this is done by adding the inverse of "space" (the gap penalty)
		boolean foundGap = false;
		
		for (int i=0; i<sequence2.length; i++)
		{
			foundGap |= sequence2[i].charAt(c.getRow()-1) == '-';
		}
		return foundGap ? -space : 0;//m_probs2[c.getRow()-1];
	}
	
	@Override
	protected double getProbabilityScoreCol(Cell c)
	{
		// if gap is already introduced at this location we do not add to the penalty
		// this is done by adding the inverse of "space" (the gap penalty)
		boolean foundGap = false;
		
		for (int i=0; i<sequence1.length; i++)
		{
			foundGap |= sequence1[i].charAt(c.getCol()-1) == '-';
		}
		return foundGap ? -space : 0;//m_probs1[c.getCol()-1];
	}
	
	@Override
	protected double getMatchMismatchScore(Cell cell,double prob)
	{
		//here we set the score according to the profile of sequences in both input alignments.
		double comulativeScore = 0;  
		for (int i=0; i<sequence1.length;i++)
		{
			for(int j=0;j <sequence2.length; j++)
			{
				if (sequence2[j].charAt(cell.getRow() - 1) == sequence1[i].charAt(cell.getCol() - 1))
				{
					if( ! (sequence2[j].charAt(cell.getRow() - 1) == '-') )
					{
						comulativeScore += match;
					}
				}
				else if( (sequence2[j].charAt(cell.getRow() - 1) == '-') || (sequence1[i].charAt(cell.getCol() - 1) == '-') )
				{
					comulativeScore += space;
				}
				else
				{
					comulativeScore += mismatch;
				}
				
				comulativeScore += match*prob*m_ratio;
			}
		}
		return comulativeScore / (sequence1.length * sequence2.length);
	}
	
	
	@Override
	public String[] getAlignment()
	{
		ensureTableIsFilledIn();
		return (String[])getTraceback();
	}
	
	@Override
	protected Object getTraceback() {
				
		StringBuffer[] align1Buf = new StringBuffer[sequence1.length];
		StringBuffer[] align2Buf = new StringBuffer[sequence2.length];
		for (int i=0; i<sequence1.length;i++)
		{
			align1Buf[i] = new StringBuffer();
		}
		for (int i=0; i<sequence2.length;i++)
		{
			align2Buf[i] = new StringBuffer();
		}
		
		Cell currentCell = getTracebackStartingCell();
		while (traceBackIsNotDone(currentCell)) {
			if (currentCell.getRow() - currentCell.getPrevCell().getRow() == 1) {
				for(int i=0; i<sequence2.length;i++)
					align2Buf[i].insert(0, sequence2[i].charAt(currentCell.getRow() - 1));
			} else {
				for(int i=0; i<sequence2.length;i++)
					align2Buf[i].insert(0, '-');
			}
			if (currentCell.getCol() - currentCell.getPrevCell().getCol() == 1) {
				for(int i=0; i<sequence1.length;i++)
					align1Buf[i].insert(0, sequence1[i].charAt(currentCell.getRow() - 1));
			} else {
				for(int i=0; i<sequence1.length;i++)
					align1Buf[i].insert(0, '-');
			}
			currentCell = currentCell.getPrevCell();
		}

		String[] alignments = new String[sequence1.length + sequence2.length];
		int i=0;
		for (;i<sequence1.length;i++)
		{
			alignments[i] = align1Buf[i].toString();
		}
		for (int j=0; i<sequence1.length+sequence2.length;i++)
		{
			alignments[i] = align2Buf[j].toString();
			j++;
		}

		return alignments;
	}
}
