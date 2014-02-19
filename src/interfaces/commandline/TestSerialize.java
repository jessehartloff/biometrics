package interfaces.commandline;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import settings.AllSettings;

public class TestSerialize implements Serializable{

	private static final long serialVersionUID = 2L;
	
	private transient Long value;
	private transient Long anotherValue;
	
	
	//Singleton. This block of code must be in all settings files (except settings variables) to enable serialization.
	private static TestSerialize instance;
	private TestSerialize(){}
	public static TestSerialize getInstance(){
		if(instance == null){instance = new TestSerialize();}
		return instance;}
	
	
	private void writeObject(ObjectOutputStream out) throws IOException {
//		System.out.println(TestSerialize.getInstance());
//		TestSerialize what = TestSerialize.getInstance();
//		out.writeObject(what);
//		System.out.println(what);
		out.writeObject(value);
		out.writeObject(anotherValue);
	}
	
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		TestSerialize.getInstance().setValue((Long) in.readObject());
		TestSerialize.getInstance().setAnotherValue((Long) in.readObject());
//		in.defaultReadObject();
//		System.out.println(in.readObject());
//		TestSerialize one = (TestSerialize) in.readObject();
//		System.out.println(one + " :one");
//		TestSerialize.instance.value = one.value;
//		TestSerialize.instance = (TestSerialize) in.readObject();
//		System.out.println(TestSerialize.instance);

	} // TODO does this really work??
	
	
	
	
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "TestSerialize [value=" + value + ", anotherValue="
				+ anotherValue + "]";
	}
	public Long getAnotherValue() {
		return anotherValue;
	}
	public void setAnotherValue(Long anotherValue) {
		this.anotherValue = anotherValue;
	}

	

//	private void writeObject(ObjectOutputStream out) throws IOException{
//		out.writeObject(staticValue);
//	}
//	
//	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
//	{
////		in.defaultReadObject();
//		Long tester  = (Long) in.readObject();
//		System.out.println(tester);
//		TestSerialize.staticValue = tester;
////		TestSerialize.staticValue = tester.getValue();
//	}
	

	
	
}
