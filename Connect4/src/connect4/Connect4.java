/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.util.Scanner;

/**
 *
 * @author johnwashington
 */
public class Connect4 {

   final static int MAXROW = 6;
   final static int MAXCOL = 7;
   
   public static boolean takeTurn(char[][] board, int column, char color)
   {
       if(board[0][column] != ' ')
           return false;
       
       for(int row =0; row<7; row++)
       {
          if(board[row][column] != ' ')
          {
              board[row-1][column] = color;
              return true;
          }
       }
       board[6][column] = color;
       return true;
   }
   
   private static char checkRow(char[][] board)
   {
       for(int row =0; row < 7; row++)
       {
           int count =0;
           
           for(int col=1; col<7; col++)
           {
               if(board[row][col] != ' ' && 
                  board[row][col] == board[row][col-1])
                   count++;
               else
                   count =1;
               if(count >=4)
                   return board[row][col];
           }
       }
       return ' ';
   }
  
   private static char checkColumn(char[][] board)
   {
       for(int col =0; col < 7; col++)
       {
           int count =0;
           
           for(int row=1; row<7; row++)
           {
               if(board[row][col] != ' ' && 
                  board[row][col] == board[row-1][col])
                   count++;
               else
                   count =1;
               if(count >=4)
                   return board[row][col];
           }
       }
       return ' ';
   }
   
   private static char checkDiags(char[][] board)
   {
       for(int col =0; col < 7; col++)
       {
           int count =0;
           
           for(int row=1; row<7; row++)
           {
               if(col + row >=7) break;
               if(board[row][col + row] != ' ' && 
                  board[row-1][col + row -1] == board[row][col + row])
                   count++;
               else
                   count =1;
               if(count >=4)
                   return board[row][col+row];
           }
       }
       //Diag that starts on left of each row
       for(int row =0; row < 7; row++)
       {
           int count =0;
           
           for(int col=1; col<7; col++)
           {
               if(col + row >=7) break;
               if(board[row+col][col] != ' ' && 
                  board[row+col-1][col-1] == board[row+col][col])
                   count++;
               else
                   count =1;
               if(count >=4)
                   return board[row+col][col];
           }
       }
       //Diags that starts on top
       for(int col =0; col <7; col++)
       {
           int count =0;
           
           for(int row=1; row<7; row++)
           {
               if(col - row <0) break;
               if(board[row][col -row] != ' ' && 
                  board[row-1][col - row +1] == board[row][col - row])
                   count++;
               else
                   count =1;
               if(count >=4)
                   return board[row][col-row];
           }
       }
       //Diagonal that starts on each left row
       for(int row =0; row < 7; row++)
       {
           int count =0;
           
           for(int col=5; col>=0; col--)
           {
               if(col - row < 0) break;
               if(board[col-row][col] != ' ' && 
                  board[col-row-1][col + 1] == board[col - row][col])
                   count++;
               else
                   count =1;
               if(count >=4)
                   return board[col-row][col];
           }
       }
      return ' '; 
   }
  
   public static char getWinner(char[][] board)
   {
       char winner = checkRow(board);
       if(winner != ' ') return winner;
       winner = checkColumn(board);
       if(winner != ' ') return winner;
       winner = checkDiags(board);
       if(winner != ' ') return winner;
       
       for(int i=0; i<board.length; i++)
       {
           for(int j=0; j<board[i].length; j++)
           {
               if(board[i][j] == ' ') return ' ';
           }
       }
      return 'T';
   }
   
   public static void displayBoard(char[][] board)
   {
       for(int row=0; row<MAXCOL; row++)
       {
           System.out.print("|");
           for(int col = 0; col<MAXCOL; col++)
           {
               System.out.print(board[row][col] + "|");
           }
           System.out.println();
       }
       
       for(int col=0; col<MAXCOL; col++)
       {
           System.out.print("---");
           System.out.println();
           System.out.println(" 1 | 2| 3| 4| 5| 6| 7|");
       }
   }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       Scanner scan = new Scanner(System.in);
       char[][] board = new char[7][72];
       
       for(int i=0; i< MAXROW; i++)
           for(int j=0; j<MAXCOL; j++)
               board[i][j] = ' ';
       displayBoard(board);
       
       boolean red = true;
       
       OUTER:
       while (true) {
           if(red)
           {
               System.out.println("It's the red player's turn!");
           }
           else
           {
               System.out.println("It's the yellow player's turn");
           }
           System.out.print("Choose a column (1-7:");
           int column = scan.nextInt();
           if (column < 1 || column > 7) {
               System.out.println("Column should be from 1 to 7");
           }
           if (!takeTurn(board, column - 1, red ? 'R' : 'Y')) {
               System.out.println("Column's filled! Choose another one.");
           }displayBoard(board);
           char result = getWinner(board);
           int choice=0;
           switch (result) {
               case 'T':
                   System.out.println("It's a draw!");
                   break OUTER;
               case 'R':
                   System.out.println("Red wins!");
                   System.out.println();
                   System.out.println("Another round?");
                   System.out.println("1- Yes   2- Exit");
                   if(choice==1){
                       displayBoard(board);
                       takeTurn(board, choice, result);
                   }  break;
               case 'Y':
                   System.out.println("Yellow wins!");
                   System.out.println();
                   System.out.println("Another round?");
                   System.out.println("1- Yes   2- Exit");
                   if(choice==1){
                       takeTurn(board, choice, result);
                   }  break;
               default:
                   break;
           }         
       }
       red = !red;
    }
}
