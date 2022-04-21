package ro.msg.learning.shop.interfaces;


public interface Vehicle {
	static String producer() {
		return "test producer";
	}
	
	default String getOverview() {
		return "get overview method " + producer();
	}
	
	void test();
}
