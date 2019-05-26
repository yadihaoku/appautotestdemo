package com.hxj.lear;

public class Test {

	static class Child {
		String name;
		
		void generaicName() {
			
		}
	}
	
	public static void main(String[] args) {
			
		Child muhe = new Child();
		muhe.name = "yanmuhe";
		
		Child baobao = new Child();
		
		System.out.println(baobao.name);
	}

}
