package clonegod.dubbo.service.consumer;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import clonegod.dubbo.api.IUserService;
import clonegod.dubbo.api.User;

public class UserServiceInvoker {
	
	public static void main(String[] args) throws InterruptedException {
		
		// start spring container
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
        context.start();
        
        // obtain proxy object for remote invocation
        IUserService userService = context.getBean("userService", IUserService.class); 
        
        // execute remote invocation
        while(true) {
        	for(int i = 1; i <= 3; i++) {
            	User user = userService.getById(i);
            	System.out.printf("invoker userService.getById, result = %s\n", user.toString());
            	
            	String ret = userService.sayHello(user.getName());
            	System.out.printf("invoker userService.sayHello, result = %s\n", ret);
            	
            	System.out.println("-----------------------\n");
            	Thread.sleep(1000);
            }
        
	        List<User> users = userService.getAllUsers();
	    	System.out.printf("invoker userService.getAllUsers, \nresult = %s", users);
	    	
	    	if(Math.random() > 1.0) {
	    		break;
	    	}
        }
        
        context.close();
	}
}
