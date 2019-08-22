package com.ehr;

import java.util.ArrayList;
import java.util.List;

public class dd {
	static int N = 1041;
	public static void main(String[] args) {
		int gap = 0;
        List<Integer> tmpList = new ArrayList<Integer>();
        List<Integer> gapList = new ArrayList<Integer>();
        for(int k=N; k>0; k/=2){
    		if(k%2!=0) {
        		tmpList.add(k%2);
    		}else {
    			tmpList.add(k%2);
    		}
        }
        int cnt = 1;
        for(int n : tmpList){
            System.out.print(n+" ");
            if(n==1) {
            	gapList.add(cnt);
            }
            cnt++;
        }
        System.out.println();
        System.out.println(gapList);
        for(int k=0; k<=gapList.size()-2; k++){
        	int tmp = gapList.get(k+1)-gapList.get(k)-1;
        	if(gap<tmp) {
        		gap = tmp;
        	}
        	
        }
        System.out.println(gap);
        //0,0,0,0,1
        
	}

}
