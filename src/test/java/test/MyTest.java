package test;

import java.util.ArrayList;
import java.util.List;

public class MyTest {
	public static void main(String[] args) {
		List<Integer> list1 = new ArrayList<Integer>();
		
		List<Integer> list2 = new ArrayList<Integer>();
		
		list2.addAll(list1);
		list2.addAll(list1);
		
		for (Integer i : list2) {
			System.out.println("#########" + i);
		}
	}

}
