
public class stringTester {
	
	public static void main(String[] args)
	{
		
	
	String names = "danny\nbill\nbob\nanne\njones\n";
	
	for(String name : names.split("\n"))
	{
		System.out.println(name);
	}
	
	
	String user = "user: name";
	user = user.substring(6);
	System.out.println(user);
	}
}
