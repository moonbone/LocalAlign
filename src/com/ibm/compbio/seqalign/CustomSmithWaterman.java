package com.ibm.compbio.seqalign;

import com.ibm.compbio.Cell;

public class CustomSmithWaterman extends SmithWaterman {

   private double[] m_probs1, m_probs2;
   
   public CustomSmithWaterman(String sequence1, String sequence2,double[] probs1,double[] probs2) {
      super(sequence1, sequence2);
      m_probs1 = probs1;
      m_probs2 = probs2;
   }

   public CustomSmithWaterman(String sequence1, String sequence2, int match,
         int mismatch, int gap,double[] probs1,double[] probs2) {
      super(sequence1, sequence2, match, mismatch, gap);
      m_probs1 = probs1;
      m_probs2 = probs2;
   }
   
   private double getProbabilityScore(Cell c)
   {
      return 0.5*(m_probs1[c.getCol()-1]+m_probs2[c.getRow()-1]);
   }
   
   @Override
   protected void fillInCell(Cell currentCell, Cell cellAbove, Cell cellToLeft,
         Cell cellAboveLeft) {
      double rowSpaceScore = cellAbove.getScoreDouble() + space*getProbabilityScore(currentCell);
      double colSpaceScore = cellToLeft.getScoreDouble() + space*getProbabilityScore(currentCell);
      double matchOrMismatchScore = cellAboveLeft.getScoreDouble();
      if (sequence2.charAt(currentCell.getRow() - 1) == sequence1
            .charAt(currentCell.getCol() - 1)) {
         matchOrMismatchScore += match*getProbabilityScore(currentCell);
      } else {
         matchOrMismatchScore += mismatch*getProbabilityScore(currentCell);
      }
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

}
