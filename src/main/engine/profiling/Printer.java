package main.engine.profiling;

public class Printer {

	public Printer() {
		
	}
	
	public void print(Object o) {
		
		System.out.println(o);
		
	}
	
	public void print(String name, Object o) {
		
		System.out.println(name + ": " + o);
		
		//Possibly get all methods? Run all getters? Nah that's fuckin stupid. This whole class is stupid lmao
		
		//o.getClass().getMethods();
		
	}
	
	public void say(String string) {
		
		System.out.println(string);
		
	}
	
}
