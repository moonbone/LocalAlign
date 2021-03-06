package com.ibm.compbio.seqalign;

import com.ibm.compbio.Cell;

public class CustomSmithWaterman extends SmithWaterman {

   private double[] m_probs1, m_probs2;
   double m_offset;
   
   public CustomSmithWaterman(String sequence1, String sequence2,double[] probs1,double[] probs2) {
      super(sequence1, sequence2);
      m_probs1 = probs1;
      m_probs2 = probs2;
      m_offset = -3;
   }

   public CustomSmithWaterman(String sequence1, String sequence2, int match,
         int mismatch, int gap, double offset, double[] probs1,double[] probs2) {
      super(sequence1, sequence2, match, mismatch, gap);
      m_probs1 = probs1;
      m_probs2 = probs2;
      m_offset = offset;
   }
   
   public static SmithWaterman createSmithWatermanSolver(String sequence1, String sequence2, int match, int mismatch, int gap, double offset, double[] probs1,double[] probs2,boolean classic)
   {
	   if(classic)
		   return new SmithWaterman(sequence1, sequence2, match, mismatch, gap);
	   else
		   return new CustomSmithWaterman(sequence1, sequence2, match, mismatch, gap, offset, probs1, probs2);
   }
   private double getProbabilityScore(Cell c,int bitmask)
   {
	  // bitmask = 3;
      //return (Math.pow(0.5,Math.max(0,bitmask-2))) * ((1&bitmask)*m_probs1[c.getCol()-1]+((2&bitmask)/2)*m_probs2[c.getRow()-1]);
	   return Math.pow(
			   Math.pow(m_probs1[c.getCol()-1],(1&bitmask) ) * 
			   Math.pow(m_probs2[c.getRow()-1],(2&bitmask)/2) //geo-mean
			   , Math.min(1,3.5-bitmask));
   }
   private double getProbabilityScoreBoth(Cell c)
   {
	   return Math.sqrt(m_probs1[c.getCol()-1] * m_probs2[c.getRow()-1]);
   }
   
   private double getProbabilityScoreRow(Cell c)
   {
	   return m_probs2[c.getRow()-1];
   }
   
   private double getProbabilityScoreCol(Cell c)
   {
	   return m_probs1[c.getCol()-1];
   }
   
   private double getMatchMismatchScore(Cell cell,double prob)
   {
	   if (sequence2.charAt(cell.getRow() - 1) == sequence1.charAt(cell.getCol() - 1))
	   {
		   return match*prob;
	   }
	   else 
	   {
		   return mismatch*prob;
	   } 
   }
   
   @Override
   protected void fillInCell(Cell currentCell, Cell cellAbove, Cell cellToLeft,
         Cell cellAboveLeft) {
      double rowSpaceScore = cellAbove.getScoreDouble() + space*getProbabilityScoreRow(currentCell) + m_offset;
      double colSpaceScore = cellToLeft.getScoreDouble() + space*getProbabilityScoreCol(currentCell) + m_offset;
      double matchOrMismatchScore = cellAboveLeft.getScoreDouble();
      
      matchOrMismatchScore += getMatchMismatchScore(currentCell,getProbabilityScoreBoth(currentCell)) + m_offset;
      
      if (rowSpaceScore >= colSpaceScore) {
         if (matchOrMismatchScore >= rowSpaceScore) {
            if (matchOrMismatchScore > 0) {
               currentCell.setScore(matchOrMismatchScore);
               currentCell.setPrevCell(cellAboveLeft);
            }
         } else {
            if (rowSpaceScore > 0) {
               currentCell.setScore(rowSpaceScore);
               currentCell.setPrevCell(cellAbove);
            }
         }
      } else {
         if (matchOrMismatchScore >= colSpaceScore) {
            if (matchOrMismatchScore > 0) {
               currentCell.setScore(matchOrMismatchScore);
               currentCell.setPrevCell(cellAboveLeft);
            }
         } else {
            if (colSpaceScore > 0) {
               currentCell.setScore(colSpaceScore);
               currentCell.setPrevCell(cellToLeft);
            }
         }
      }
      if (currentCell.getScore() > highScoreCell.getScore()) {
         highScoreCell = currentCell;
      }
   }
   
   @Override
   public String[] getAlignment()
   {
	   String[] retArray = super.getAlignment();
	   StringBuffer align1Buf = new StringBuffer();
	   StringBuffer align2Buf = new StringBuffer();
	   Cell currentCell = getTracebackStartingCell();
	   int lastRow = currentCell.getRow(),lastCol = currentCell.getCol();
	   int firstRow=0,firstCol=0;
	   while (traceBackIsNotDone(currentCell)) {
		   firstRow = currentCell.getRow();
		   firstCol = currentCell.getCol();
		   if (currentCell.getRow() - currentCell.getPrevCell().getRow() == 1) {
			   align2Buf.insert(0, String.format("  %4.4f  ", m_probs2[currentCell.getRow() - 1]).replace("0.", "."));
		   } else {
			   align2Buf.insert(0, "    -    ");
		   }
		   if (currentCell.getCol() - currentCell.getPrevCell().getCol() == 1) {
			   align1Buf.insert(0, String.format("  %4.4f  ", m_probs1[currentCell.getCol() - 1]).replace("0.", "."));
		   } else {
			   align1Buf.insert(0, "    -    ");
		   }
		   currentCell = currentCell.getPrevCell();
	   }
	   retArray[0] = align1Buf.toString() + "\n" + String.format("%3d", firstCol) + retArray[0].replace("", "        ").substring(7) + String.format("%d", lastCol);
	   retArray[1] = String.format("%3d", firstRow) + retArray[1].replace("", "        ").substring(7) + String.format("%d", lastRow) + "\n" +  align2Buf.toString() ;
	   
	   return retArray;
   }
   

}
