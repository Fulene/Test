package com.synox.test;

import com.synox.test.model.User;
import com.synox.test.service.RandomTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TestApplication implements CommandLineRunner {

//	@Autowired
//	ScheduleService scheduleService;

	@Autowired
	RandomTestService randomTestService;

	Environment environment;

	@Autowired
	public TestApplication(Environment environment) {
		this.environment = environment;
	}

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		testProperties();
//		testCsvFile();
//		testLombok();
//		randomTestService.optionalTest();
//		System.out.println(randomTestService.streamTest());

		User user = new User("Mehdi", "Hamerlaine", 27, "95", "1.71");
	}



	private void testProperties() {
		System.out.println("=============================");
		System.out.println(environment.getProperty("name"));
		System.out.println(environment.getProperty("lastname"));
		System.out.println("=============================");
	}

	private void testCsvFile() {
		List<User> users = Arrays.asList(new User("Mehdi", "Hamerlaine", 27, "95", "1.71"),
				new User("Driss", "Hamerlaine", 30, "70", "1.75"),
				new User("Ilyes", "Hamerlaine", 16, "65", "1.77"));
		File testCsvFile = new File("C:/Users/Mehdi/Documents/test/test.csv");
		try {
			testCsvFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (PrintWriter pw = new PrintWriter(testCsvFile)) {
			StringBuilder sb = new StringBuilder();
			users.forEach(sb::append);
			pw.write(sb.toString());
			System.out.println("END");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private void testLombok() {
//		User mehdi = new User("Mehdi");
//		System.out.println(mehdi.getName() + " " + mehdi.getLastname());
//		mehdi.setName("gvdsv");
//		System.out.println(mehdi.getName() + " " + mehdi.getLastname());
//	}

}
