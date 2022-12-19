package com.synox.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.synox.test.config.AssosRoleFeature;
import com.synox.test.model.Dishe;
import com.synox.test.model.Ingredient;
import com.synox.test.model.User;
import com.synox.test.service.AsyncService;
import com.synox.test.service.RandomTestService;
import com.synox.test.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
public class TestApplication implements CommandLineRunner {

    @Value("${test-list}")
    private List<String> testList;

//	@Autowired
//	ScheduleService scheduleService;

    @Autowired
    RandomTestService randomTestService;
    @Autowired
    UserService userService;

    @Autowired
    AsyncService asyncService;

    @Autowired
    AssosRoleFeature assosRoleFeature;

    @Autowired
    Dishe dishe;

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
//      objectMapping();
//        testAmine();
//        testIf();
//        testList();
//        testOf();
//        testFromMasterBranch();
//        testFromTestBranch();
//        testBool();
//        testBigDecimal();
//        testConversion();
//        testValueInj();
//        testRef();
//        testAsync();
//        testYmlProp();
//        testRolesFeatures();
//        testRegex();
//        testDuration();
//        testBean();
//        testFuture();
//        testListFromAppYml();
//        testTrunc();
//        testRange();
        testModulo();
    }

    private void testModulo() {
        System.out.println(200%4);
        System.out.println(200%100);
        System.out.println(200%400);
    }

    private void testRange() {
        List<Integer> list = IntStream.range(1, 5)
            .boxed()
            .collect(Collectors.toList());

        System.out.println(list);
    }

    private void testTrunc() {
        double d = 1234.56;
        BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.DOWN);
        System.out.println(bd.doubleValue());
    }

    private void testListFromAppYml() {
        testList.forEach(System.out::println);
        testList.stream().map(String::length).collect(Collectors.toList()).forEach(System.out::println);
    }

    private void testFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        });
        f
            .thenApply(s -> s + " !")
                .thenAccept(System.out::println);

        System.out.println("1");
        System.out.println(f.get());
        System.out.println("2");

    }

    private void testBean() {
        System.out.println(dishe.getName());
    }

    private void testDuration() {
        String oneH = String.valueOf(Duration.of(1, ChronoUnit.HOURS).toMillis());
        String oneS = String.valueOf(Duration.of(10, ChronoUnit.SECONDS).toMillis());

        System.out.println(oneH);
        System.out.println(oneS);
    }

    private void testRolesFeatures() {
        Set<Ingredient> ingredientSet1 = new HashSet<>();
        ingredientSet1.add(new Ingredient("aa"));
        ingredientSet1.add(new Ingredient("bb"));
        ingredientSet1.add(new Ingredient("cc"));

        Set<Ingredient> ingredientSet2 = new HashSet<>();
        ingredientSet2.add(new Ingredient("dd"));
        ingredientSet2.add(new Ingredient("ee"));
        ingredientSet2.add(new Ingredient("ff"));

        Set<Ingredient> ingredientSet3 = new HashSet<>();
        ingredientSet3.add(new Ingredient("gg"));
        ingredientSet3.add(new Ingredient("hh"));
        ingredientSet3.add(new Ingredient("ii"));

        Set<Dishe> disheSet1 = new HashSet<>();
        disheSet1.add(new Dishe("d1", ingredientSet1));
        disheSet1.add(new Dishe("d2", ingredientSet2));
        disheSet1.add(new Dishe("d3", ingredientSet3));

        User u1 = new User(3, "Med", "Ham", disheSet1);

        System.out.println(
            u1.getFavoriteDishes().stream().map(Dishe::getIngredients).flatMap(Set::stream).map(Ingredient::getName).collect(Collectors.toSet())
        );
    }

    private void testYmlProp() {
        System.out.println(assosRoleFeature.getRole1());
        System.out.println(assosRoleFeature.getRole2());
        System.out.println(assosRoleFeature.getRole3());
    }

    private void testAsync() {
        System.out.println("Parent : "
            + Thread.currentThread().getName());
        Future<String> future = asyncService.asyncMethodWithReturnType();
        while (true) {
            if (future.isDone()) {
                try {
                    System.out.println("Result from asynchronous process - " + future.get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            System.out.println("Continue doing something else.");
            System.out.println("And again...");
        }
        System.out.println("Out of loop");
    }

    private void testRef() {
//        HashMap<String, String> map = new HashMap<>();
//        refTest(map);
//        System.out.println(map);
        User u = new User();
        u.setName("Mehdi");
        refTest(u);
        System.out.println(u);
    }

    private void refTest(User u) {
        u.setLastname("Ham");
    }

    private void testValueInj() {
        User user = new User();
        System.out.println(user);
    }

    private void testConversion() {
//        Long i = 12356497L;
//        System.out.println(i);

//        Double y = 1800.0 / 100_000_000;
//        System.out.println(y);

        String v = "357500";
        BigDecimal bigDecimal = BigDecimal.valueOf(Long.parseLong(v));
        System.out.println(bigDecimal.multiply(BigDecimal.valueOf(2)));
    }

    private void testBigDecimal() {
//        BigDecimal bd1 = new BigDecimal("1.0");
//        BigDecimal bd2 = new BigDecimal("1.00");
//        BigDecimal bd3 = new BigDecimal("3.1");
//
//        System.out.println(bd3.subtract(bd1));

//        Double d = 444.505;
//        BigDecimal bigDecimal = BigDecimal.valueOf(d);
//        BigDecimal bd = bigDecimal.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
//        System.out.println(d);
//        System.out.println(bigDecimal);
//        System.out.println(bd);
//        System.out.println(bd.doubleValue());

        BigDecimal pi = BigDecimal.valueOf(444.515);

        // Arrondir le nombre; retenir deux chiffres après la virgule
        pi = pi.setScale(2, RoundingMode.DOWN);

        System.out.println(pi.doubleValue());
    }

    private void testBool() {
        System.out.println(true);
        System.out.println(!true);
    }

    private void testFromMasterBranch() {
        System.out.println("test From Master Branch");
    }

    private void testFromTestBranch() {
        System.out.println("test from test branch !!");
        System.out.println("Added test from test branch !!");
    }

    private void testFromTest2Branch() {
        System.out.println("test from test2 branch !!");
    }


    private void testList() {
//        List<String> test = new ArrayList<>();
//        test.add("Pizza");
//        test.add("Burger");
//        test.add("Crepe");
//
//        List<Dishe> dishes = test.stream().map(n -> new Dishe(n, new ArrayList<>())).peek(d -> d.setIsTest(true)).collect(Collectors.toList());
//
//        System.out.println(dishes);

//        String test = "test";
//        List<String> tests = List.of(test);
//
//        System.out.println(tests.size());
//        System.out.println(tests);

//        Map<String, String> testMap = new HashMap<>();
//        testMap.put("testKey", "TestVal");
//        testMap.put("totoKey", "TotoVal");
//
//        System.out.println(testMap.values().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Err")));

        List<Test1> l1 = List.of(new Test1("t1", BigDecimal.valueOf(3)), new Test1("t1", BigDecimal.valueOf(7)), new Test1("t1", BigDecimal.valueOf(9)));
        List<Test2> l2 = List.of(new Test2("t2", BigDecimal.valueOf(5)), new Test2("t2", BigDecimal.valueOf(31)), new Test2("t2", BigDecimal.valueOf(71)));

        System.out.println(l1);
        System.out.println(l2);
        System.out.println(Stream.of(l1, l2).flatMap(Collection::stream).collect(Collectors.toList()));

        BigDecimal t1 = BigDecimal.valueOf(2);
        BigDecimal t2 = BigDecimal.valueOf(2).add(t1);
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t1);
//
//        Object res =
//            Stream.of(
//                l1.stream().map(Test1::getVal).collect(Collectors.toList()),
//                l2.stream().map(Test2::getVal).collect(Collectors.toList())
//            )
//                .flatMap(Collection::stream)
//                .reduce(BigDecimal::add)
//                .get();
//
//        System.out.println(res);

//        List<Integer> list1 = List.of(0, 0, 0, 0, 0);
//        List<Integer> list2 = List.of(0, 1, 2, 1, 2);
//        System.out.println(list1);
//        System.out.println(list2);
//
//        boolean b1 = list1.stream().allMatch(elt -> elt == 0);
//        boolean b2 = list2.stream().allMatch(elt -> elt == 0);
//
//        System.out.println(b1);
//        System.out.println(b2);
    }

    private void testIf() {
        int age = 15;
        if (age > 10) System.out.println("1");
        else if (age > 12) System.out.println("2");
        if (age > 12) System.out.println("3");
    }

    private void testAmine() {
        System.out.println("===== Begin =====");
        Scanner sc = new Scanner(System.in);

//        int[] arr = new int[50];
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i <= 3; i++) {
            System.out.println("2) Entrer un nb : ");
            list.add(Integer.parseInt(sc.nextLine()));
        }

        System.out.println("res : " + list);

        // Arrays.stream(arr)... <= si array
        int sum = list.stream().reduce(0, Integer::sum);
        System.out.println(sum);
    }

    private void objectMapping() throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        User user = new User(1, "Mehdi");
//        String userJson = mapper.writeValueAsString(user);
//        System.out.println(user);
//        System.out.println(userJson);
    }

    private void testRegexEscap() {
//		String pattern = Matcher.quoteReplacement("1252343% 8 567 hdfg gf^$545");
//		System.out.println("Pattern is : " + pattern);
        String regex = "[^a-zA-Z\\d\\s:]";

        String text = "125/2343% 8 \\ 567 a hdêgèkkékkàkkçkk&kkkëyyy$sddd*ff%fgdùvffd!sfds; gf^$545";

        System.out.println(text.replaceAll("[^a-zA-Zéèêëàùç\\d\\s:]", "\\\\$0"));

    }

    private void testRegex() {
        String pwdRegex = "^(?!.*\\s)(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~`!@#$%^&*()--+={}\\[\\]|\\\\:;\"'<>,.?/_₹]).{8,127}$";
        String pwd = "Mehdiiii3+";
        System.out.println(Pattern.matches(pwdRegex, pwd));
    }

//	private void testStr() {
//		String str = "tes/t/ygu";
//		System.out.println(str.replace("/", "-"));
//	}

    private void testDate() throws ParseException {
//		Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("15/02/2020 21:00");
//		Date date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("15/02/2025 22:00");
//		String currentDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date());
//		Date currentDate = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(currentDateFormat);
//		System.out.println("date : " + date);
//		System.out.println("currentDate : " + currentDate);
//		System.out.println(date.compareTo(currentDate)); // si date avant currentDate => -1
//		System.out.println(currentDate.compareTo(date));
//        System.out.println(new Date());
//		System.out.println(date2.compareTo(new Date()));


//        Date d = Date.from(LocalDate.now().minusMonths(3).atStartOfDay(ZoneId.systemDefault()).toInstant()); // 3 mois en arrière
//        System.out.println(d);

        ZonedDateTime tenMinAgo = ZonedDateTime.now().minusMinutes(10);
        System.out.println(tenMinAgo);

        ZonedDateTime t = ZonedDateTime.now().minusMinutes(5);
        System.out.println(t);

        System.out.println(t.isAfter(tenMinAgo));
    }

    private void testStrBuilder() {
//        String strB = "Test";
//        strB += "klou";
//        System.out.println(strB);
//
//        Dishe dishe = new Dishe("med", Collections.emptyList());
//        if (dishe.getIsTest() != null && dishe.getIsTest()) System.out.println("ok");
//        else System.out.println("ko");

        StringBuilder sb = new StringBuilder(" Je fais un super test <a href=\\\"https://stackinsat.com/dashboard/home?from_email=true\\\">service client</a> GGHHG");
        sb.append("YOUHOU");
        System.out.println(sb.toString());
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

//        System.out.println(userList2);
//        userList1.forEach(u1 ->
//                userList2.stream().filter(u2 -> u2.getId() == u1.getId())
//                        .findFirst().ifPresent(u2 -> u2.setName(u1.getName())));
//        System.out.println(userList2);

        List<Integer> userIds = userList1.stream().map(User::getId).collect(Collectors.toList());
        System.out.println(userIds);
    }

    private void mapTest() {
//        Map<Integer, String> m = new HashMap<>();
//        m.put(3, "test1");
//        m.put(5, "test2");
//        m.put(8, "test3");
//        for (Map.Entry<Integer, String> entry : m.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }
//        System.out.println(m.keySet().stream().findFirst().get());
//        System.out.println(m.values().stream().findFirst().get());
//        System.out.println(m.get(4));

        Map<String, String> map = Map.of("key1", "value1", "key2", "value2");

        System.out.println(map);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public class Test1 {
        String type;
        BigDecimal val;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public class Test2 {
        String type;
        BigDecimal val;
    }

}
