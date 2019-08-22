package com.ehr;

import java.util.ArrayList;
import java.util.List;

public class dd {
	static int N = 32;
	public static void main(String[] args) {
		int output = 0;
        List<Integer> biList = new ArrayList();
        for(int k=N; k>0; k--){
            if((k/2)>0){
                biList.add(1);
            }
        }
        for(int n : biList){
            System.out.println(n);
        }

	}

}
