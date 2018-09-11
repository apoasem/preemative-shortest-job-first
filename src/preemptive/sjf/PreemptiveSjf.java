package preemptive.sjf;
import java.io.*;
import java.util.Scanner;


public class PreemptiveSjf {

    public static void main(String[] args)throws IOException {
        
        
        Scanner br = new Scanner(System.in);
      int n=0;//number of processes
      boolean isNumber;//flag to check validation
      
      System.out.println("Please enter the number of Processes: ");
       
       
       do{
           if(br.hasNextInt())
           {
               isNumber=true;   
               n=br.nextInt();
               while(n < 0)
               {
                   System.out.println("Sorry Invalid Number \t Please Re-enter the number of Processes: ");
                   do{
                   if(br.hasNextInt())
                   {
                       n = br.nextInt();
                       isNumber=true;
                       if(n>0)
                       {
                       break;
                       }
                   }
                   else
                       {
                           System.out.println("Sorry it's character!! Please re-enter the number of Processes: ");
                           isNumber=false;
                           br.next();
                       }
                   }while(!(isNumber));
               }
           }
           else
           {
               System.out.println("Sorry it's character!! Please re-enter the number of Processes:");
               isNumber=false;
                br.next();
               
           }
           
        }while(!(isNumber));
       
       
       
       
      
        
           
       
      
       
       int proc[][] = new int[n + 1][5];// (proc[][0] is the ArrivalTime array),([][1] - BurstTime),([][2] - WaitingTime),([][3] - TurnaroundTime),([][4] -ResponseTime)
       for(int i = 1; i <= n; i++)//for loop to pass through processes
       {
      
         System.out.println("Please enter the Arrival Time for Process " + i + ": ");
         do{
           if(br.hasNextInt())
           {
               isNumber=true;   
               proc[i][0]=br.nextInt();
               while(proc[i][0] < 0)
               {
                   System.out.println("Sorry Invalid Number \t Please Re-enter the Arrival Time for Process " + i + ": ");
                   do{
                   if(br.hasNextInt())
                   {
                       proc[i][0] = br.nextInt();
                       isNumber=true;
                       if(proc[i][0]>0)
                       {
                       break;
                       }
                   }
                   else
                       {
                           System.out.println("Sorry it's character!! Please re-enter the Arrival Time for Process: " + i + ": ");
                           isNumber=false;
                           br.next();
                       }
                   }while(!(isNumber));
               }
           }
           else
           {
               System.out.println("Sorry it's character!! Please re-enter the Arrival Time for Process " + i + ": ");
               isNumber=false;
                br.next();
               
           }
           
        }while(!(isNumber));
      
      
      
      System.out.println("Please enter the Burst Time for Process " + i + ": ");
      do{
           if(br.hasNextInt())
           {
               isNumber=true;   
               proc[i][1]=br.nextInt();
               while(proc[i][1] < 0)
               {
                   System.out.println("Sorry Invalid Number \t Please Re-enter the Burst Time for Process " + i + ": ");
                   do{
                   if(br.hasNextInt())
                   {
                       proc[i][1] = br.nextInt();
                       isNumber=true;
                       if(proc[i][1]>0)
                       {
                       break;
                       }
                   }
                   else
                       {
                           System.out.println("Sorry it's character!! Please re-enter the Burst Time for Process " + i + ": ");
                           isNumber=false;
                           br.next();
                       }
                   }while(!(isNumber));
               }
           }
           else
           {
               System.out.println("Sorry it's character!! Please re-enter the Burst Time for Process " + i + ": ");
               isNumber=false;
                br.next();
               
           }
           
        }while(!(isNumber));
     }
       System.out.println();
     
       //Calculation of Total Time of burst time and Initialization of Time Chart array
     int total_time = 0;
     for(int i = 1; i <= n; i++)
     {
      total_time += proc[i][1];
     }
     int time_chart[] = new int[total_time];
     
     for(int i = 0; i < total_time; i++)
     {
      
      int sel_proc = 0;//variable to assign the process in cpu
    
      int min = 99999;//variable to assign the shortest process
      for(int j = 1; j <= n; j++)//Selection of shortest process which has arrived
      {
       if(proc[j][0] <= i)//Condition to check if Process has arrived
       {
        if(proc[j][1] < min && proc[j][1] != 0)//condition to check for Selection of shortest process && in the same time not completing its execution
        {
         min = proc[j][1];//the shortest process
         sel_proc = j;//the process which is in the cpu
        }
       }
      }
      ////////////////////////////////////////////////////
      //Assign selected process to current time in the Chart
      time_chart[i] = sel_proc;
      
      //Decrement Remaining Time of selected process by 1 since it has been assigned the CPU for 1 unit of time
      proc[sel_proc][1]--;
      
      //WT and TT Calculation
      for(int j = 1; j <= n; j++)
      {
       if(proc[j][0] <= i)//Condition to check if Process has arrived
       {
        if(proc[j][1] != 0)//not completing its execution
        {
         proc[j][3]++;//If process has arrived and it has not already completed execution its TT is incremented by 1
            if(j != sel_proc)//If the process has not been currently assigned the CPU and has arrived its WT is incremented by 1
             proc[j][2]++;// waiting time
           
            
        }
        else if(j == sel_proc)//This is a special case in which the process has been assigned CPU and has completed its execution
         proc[j][3]++;//turn around time
       }
       
     else  if(proc[j][0]>i&&j!= sel_proc)//Condition to check if Process has not arrived && not been in cpu
           proc[j][4]++;
       
      }
      
      //Printing the Time Chart
      if(i != 0)
      {
       if(sel_proc != time_chart[i - 1])
        //If the CPU has been assigned to a different Process we need to print the current value of time and the name of 
        //the new Process
       {
        System.out.print("--" + i + "--P" + sel_proc);
       }
      }
      else//If the current time is 0 i.e the printing has just started we need to print the name of the First selected Process
       System.out.print(i + "--P" + sel_proc);
      if(i == total_time - 1)//All the process names have been printed now we have to print the time at which execution ends
       System.out.print("--" + (i + 1));
      
     }
     System.out.println();
     System.out.println();
     
     //Printing the WT and TT for each Process
     System.out.println("P\t WT \t TT  \t RT");
     for(int i = 1; i <= n; i++)
     {
      System.out.printf("%d\t%2dms\t%2dms\t%2dms",i,proc[i][2],proc[i][3],proc[i][4]-proc[i][0]);
      System.out.println();
     }
     
     System.out.println();
     
     //Printing the average WT & TT
     float WT = 0,TT = 0,RT=0;
     for(int i = 1; i <= n; i++)
     {
      WT += proc[i][2];
      TT += proc[i][3];
      RT += (proc[i][4]-proc[i][0]);
     }
     WT /= n;
     TT /= n;
     RT /= n;
     System.out.println("The Average WT is: " + WT + "ms");
     System.out.println("The Average TT is: " + TT + "ms");
     System.out.println("The Average RT is: " + RT + "ms");
     //-------------
      
       
    }
    
           
   
}
    
