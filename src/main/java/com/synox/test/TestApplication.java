package com.synox.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synox.test.model.Dishe;
import com.synox.test.model.Ingredient;
import com.synox.test.model.User;
import com.synox.test.service.RandomTestService;
import com.synox.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class TestApplication implements CommandLineRunner {

//	@Autowired
//	ScheduleService scheduleService;

    @Autowired
    RandomTestService randomTestService;
    @Autowired
    UserService userService;

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
//		User user = new User("Mehdi", "Hamerlaine", 27, "95", "1.71");
//		streamTest();
//		mapTest();
//		streamTest2();
//		testStrBuilder();
//		testDate();
//		testStr();
//      testRegexEscap();
        objectMapping();
    }

    private void objectMapping() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(1, "Mehdi");
        String userJson = mapper.writeValueAsString(user);
        System.out.println(user);
        System.out.println(userJson);
    }

    private void testRegexEscap() {
//		String pattern = Matcher.quoteReplacement("1252343% 8 567 hdfg gf^$545");
//		System.out.println("Pattern is : " + pattern);
        String regex = "[^a-zA-Z\\d\\s:]";

        String text = "125/2343% 8 \\ 567 a hdêgèkkékkàkkçkk&kkkëyyy$sddd*ff%fgdùvffd!sfds; gf^$545";

        System.out.println(text.replaceAll("[^a-zA-Zéèêëàùç\\d\\s:]", "\\\\$0"));

    }

//	private void testStr() {
//		String str = "tes/t/ygu";
//		System.out.println(str.replace("/", "-"));
//	}

    private void testDate() throws ParseException {
//		Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("15/02/2020 21:00");
//		Date date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("15/02/2020 22:00");
//		String currentDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date());
//		Date currentDate = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(currentDateFormat);
//		System.out.println("date : " + date);
//		System.out.println("currentDate : " + currentDate);
//		System.out.println(date.compareTo(currentDate));
//		System.out.println(currentDate.compareTo(date));
//		System.out.println(date2.compareTo(date));
        System.out.println(new Date().compareTo(null));
    }

    private void testStrBuilder() {
        String strB = "Test";
        strB += "klou";
        System.out.println(strB);

        Dishe dishe = new Dishe("med", Collections.emptyList());
        if (dishe.getIsTest() != null && dishe.getIsTest()) System.out.println("ok");
        else System.out.println("ko");
    }

    private void testProperties() {
        System.out.println("=============================");
        System.out.println(environment.getProperty("name"));
        System.out.println(environment.getProperty("lastname"));
        System.out.println("=============================");
    }

//	private void testCsvFile() {
//		List<User> users = Arrays.asList(new User("Mehdi", "Hamerlaine", 27, "95", "1.71"),
//				new User("Driss", "Hamerlaine", 30, "70", "1.75"),
//				new User("Ilyes", "Hamerlaine", 16, "65", "1.77"));
//		File testCsvFile = new File("C:/Users/Mehdi/Documents/test/test.csv");
//		try {
//			testCsvFile.createNewFile();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try (PrintWriter pw = new PrintWriter(testCsvFile)) {
//			StringBuilder sb = new StringBuilder();
//			users.forEach(sb::append);
//			pw.write(sb.toString());
//			System.out.println("END");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	private void testLombok() {
//		User mehdi = new User("Mehdi");
//		System.out.println(mehdi.getName() + " " + mehdi.getLastname());
//		mehdi.setName("gvdsv");
//		System.out.println(mehdi.getName() + " " + mehdi.getLastname());
//	}

    private void streamTest() {
        List<User> users = userService.getUsers();
        Predicate<String> nameNoLessThan5 = n -> n.length() <= 5;
        List<String> usersStream =
                users.stream().map(user -> user.getFavoriteDishes().stream().map(Dishe::getName).filter(nameNoLessThan5).collect(Collectors.toList())).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        System.out.println(usersStream);
        System.out.println(usersStream.size());
    }

    private void streamTest2() {
        List<User> userList1 =
                Arrays.asList(new User(1, "Mehdi", "Hamerlaine", null),
                        new User(2, "Narj", "Tona", null),
                        new User(3, "Toto", "Titi", null));

        List<User> userList2 =
                Arrays.asList(new User(1, "autre1", "de", null),
                        new User(2, "autre2", "fe", null),
                        new User(3, "autre3", "xs", null));

        System.out.println(userList2);
        userList1.forEach(u1 ->
                userList2.stream().filter(u2 -> u2.getId() == u1.getId())
                        .findFirst().ifPresent(u2 -> u2.setName(u1.getName())));
        System.out.println(userList2);

    }

    private void mapTest() {
        Map<Integer, String> m = new HashMap<>();
        m.put(3, "test1");
        m.put(5, "test2");
        m.put(8, "test3");
        for (Map.Entry<Integer, String> entry : m.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println(m.keySet().stream().findFirst().get());
        System.out.println(m.values().stream().findFirst().get());
    }

}
