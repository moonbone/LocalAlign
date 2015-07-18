/**
 * Author: Paul Reiners
 */
package com.ibm.compbio.seqalign;

import com.ibm.compbio.Cell;

/**
 * @author Paul Reiners
 * 
 */
public class SmithWaterman extends SequenceAlignment {

   protected Cell highScoreCell;

   public SmithWaterman(String sequence1, String sequence2) {
      super(sequence1, sequence2);
   }

   public SmithWaterman(String sequence1, String sequence2, int match,
         int mismatch, int gap) {
      super(sequence1, sequence2, match, mismatch, gap);
   }

   protected void initialize() {
      super.initialize();

      highScoreCell = scoreTable[0][0];
   }

   protected void fillInCell(Cell currentCell, Cell cellAbove, Cell cellToLeft,
         Cell cellAboveLeft) {
      int rowSpaceScore = cellAbove.getScore() + space;
      int colSpaceScore = cellToLeft.getScore() + space;
      int matchOrMismatchScore = cellAboveLeft.getScore();
      if (sequence2.charAt(currentCell.getRow() - 1) == sequence1
            .charAt(currentCell.getCol() - 1)) {
         matchOrMismatchScore += match;
      } else {
         matchOrMismatchScore += mismatch;
      }
      if (rowSpaceScore >= colSpaceScore) {
         if (matchOrMismatchScore >= rowSpaceScore) {
            if (true || matchOrMismatchScore > 0) {
               currentCell.setScore(matchOrMismatchScore);
               currentCell.setPrevCell(cellAboveLeft);
            }
         } else {
            if (true || rowSpaceScore > 0) {
               currentCell.setScore(rowSpaceScore);
               currentCell.setPrevCell(cellAbove);
            }
         }
      } else {
         if (matchOrMismatchScore >= colSpaceScore) {
            if (true || matchOrMismatchScore > 0) {
               currentCell.setScore(matchOrMismatchScore);
               currentCell.setPrevCell(cellAboveLeft);
            }
         } else {
            if (true || colSpaceScore > 0) {
               currentCell.setScore(colSpaceScore);
               currentCell.setPrevCell(cellToLeft);
            }
         }
      }
      if (currentCell.getScore() > highScoreCell.getScore()) {
         highScoreCell = currentCell;
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return "[NeedlemanWunsch: sequence1=" + sequence1 + ", sequence2="
            + sequence2 + "]";
   }

   @Override
   protected boolean traceBackIsNotDone(Cell currentCell) {
      return currentCell.getScore() != 0;
   }

   @Override
   protected Cell getTracebackStartingCell() {
      return scoreTable[scoreTable.length - 1][scoreTable[0].length - 1]; //highScoreCell;
   }

   @Override
   protected Cell getInitialPointer(int row, int col) {
      return null;
   }

   @Override
   protected int getInitialScore(int row, int col) {
      return 0;
   }
   
   public String[] getAlignedSequences()
   {
	   ensureTableIsFilledIn();
	   return (String[])getTraceback();
   }

}
