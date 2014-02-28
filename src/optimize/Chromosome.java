package optimize;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Chromosome {
	//TODO Matt: Make chromosome class for genetic algorithm
	private String name;
	private Long value;
	private Method method;
	protected Object self;
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

	
	public Chromosome(Object self, Long value, String name, Method m ){
		this.value = value;
		this.method = m;
		this.name = name;
		this.self = self;
	}
	
	public void execute() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		this.method.invoke(self, value);
	}
}
