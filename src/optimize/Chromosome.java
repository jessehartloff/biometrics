package optimize;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Chromosome {
	//TODO Matt: Make chromosome class for genetic algorithm
	private String name;
	private Long value;
	private Method method;
	protected Object self;
	private final ArrayList<Long> bounds;
	
	public ArrayList<Long> getBounds(){
		return this.bounds;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	
	public Chromosome(Object self, Long value, String name, Method m, ArrayList<Long> bounds){
		this.value = value;
		this.method = m;
		this.name = name;
		this.self = self;
		this.bounds = bounds; 
	}
	
	public void execute() {
		try {
			this.method.invoke(self, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
