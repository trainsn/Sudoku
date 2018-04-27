import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Suduko {
	public final static int size=9;
	static int[][] sudoku=new int[9][9];
	static int[][] ans=new int[9][9];
	static int flag=0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stu
		int i,j,row,col;
		while (true)
		{
			if (lasVegas(11)) break;
		}
		int[][] last=new int[9][9];
		for (i=0;i<size;i++)
			for (j=0;j<size;j++)
				last[i][j]=sudoku[i][j];
						
		Scanner in = new Scanner(System.in);
		int known = in.nextInt();		
		int hole=size*size-known;
		int step=(int)Math.sqrt(hole);
		int interval=size/step+1;
		int up=hole/size+1; 
		
		int counter=0;
		Random rnd = new Random();
		int[] countRow=new int[9];
		int[] countCol=new int[9];
		for (i=0;i<size;i++)
		{
			countRow[i]=0;
			countCol[i]=0;
		}
		
		
		//根据用户输入确定step，进行挖空
		for ( i=0;i<step;i++) 
			for (j=0;j<step;j++)
			{
				for (row=i*interval;row<(i+1)*interval&&row<size;row++)
				{
					boolean done=false;
					for (col=j*interval;col<(j+1)*interval&&col<size;col++)
					{
						if (sudoku[row][col]>0  &&countRow[row]<up&&countCol[col]<up && dig(row,col)==true)
						{
							counter++;
							countRow[row]++;
							countCol[col]++;
							done=true;
							break;
						}			
					}
					if (done)
						break;
				}
			}

		//随机选取剩余格子，凑足挖空的个数
		while (true)
		{
			row=rnd.nextInt(size);
			col=rnd.nextInt(size);
			if (sudoku[row][col]>0&& dig(row,col)==true)
			{
				counter++;
				countRow[row]++;
				countCol[col]++;
			}
			if (counter==hole)
				break;
		}		
		for (i=0;i<size;i++)
			for (j=0;j<size;j++)
			{
				if (j!=size-1)
					System.out.print(sudoku[i][j]+" ");
				else 
					System.out.println(sudoku[i][j]);
			}
		System.out.println("----------------------");	
		for (i=0;i<size;i++)
			for (j=0;j<size;j++)
			{
				if (j!=size-1)
					System.out.print(last[i][j]+" ");
				else 
					System.out.println(last[i][j]);
			}
			
	}
	
	//挖空函数，首先判断挖完是否仍然满足唯一解，如果满足，那么置零
	public static boolean dig(int row, int col)
	{
		int num=sudoku[row][col];
		int i;
		for (i=1;i<=size;i++)
			if(i!=num)
			{
				sudoku[row][col]=i;
				if (legal(row,col,i)&&getSolution(0,0)==true)
				{
					sudoku[row][col]=num;
					return false;					
				}					
			}
		sudoku[row][col]=0;
		return true;		
				
	}
	
	//判断在某一位置放置一个数字是否合法
	public static  boolean legal(int row,int col,int num)
	{
		int i,j;
		int row3=row/3;
		int col3=col/3;
		for (i=0;i<size;i++)
			if (sudoku[row][i]>0&&sudoku[row][i]==num)
			{
				return false;
			}
		for (i=0;i<size;i++)
			if (sudoku[i][col]>0&&sudoku[i][col]==num)
			{
				return false;
			}
		for (i=row3*3;i<row3*3+3;i++)
			for (j=col3*3;j<col3*3+3;j++)
				if (sudoku[i][j]>0&&sudoku[i][j]==num)
				{
					return false;
				}
		return true;
		
	}
	
	//求解函数
	public static  boolean getSolution(int row, int col)
	{
		int num,i,j;
		boolean test;
		if (row==size && col==0)
		{
			flag=1;
			for (i=0;i<size;i++)
				for (j=0;j<size;j++)
					ans[i][j]=sudoku[i][j];
		}
		if (flag==1)
			return true;
		
		if (sudoku[row][col]==0)
		{
			for (num=1;num<=size;num++)
			{
				test=legal(row,col,num);
				if (test==true)
				{
					sudoku[row][col]=num;
					if (col==size-1)					
						getSolution(row+1,0);
					else 
						getSolution(row,col+1);
					sudoku[row][col]=0;
				}
			}			
		}	
		else 
		{
			if (col==size-1)					
				getSolution(row+1,0);
			else 
				getSolution(row,col+1);
		}
		return false;
	}
	
	//用拉斯维加斯算法生成数独的终盘
	public static  boolean lasVegas(int n)
	{
		int i,j;
		int row,col,num;
		int counter=0;
		Random rnd = new Random();
		for (i=0;i<size;i++)
			for (j=0;j<size;j++)
			{
				sudoku[i][j]=0;
			}
		while (true)
		{
			row=rnd.nextInt(size);
			col=rnd.nextInt(size);
			num=rnd.nextInt(size)+1;
			if (sudoku[row][col]==0&&legal(row,col,num)==true)
			{
				sudoku[row][col]=num;
				counter++;
			}
			if (counter==n)
				break;
		}
		if (getSolution(0,0)==true)
		{
			for (i=0;i<size;i++)
				for (j=0;j<size;j++)
					sudoku[i][j]=ans[i][j];
			return true;
		}
		return false;		
	}
}
