import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DemoArrayListNames {
    public static void main(String[] args) {
        // 初始化两个队伍
        List<String> one = new ArrayList<>();
        one.add("迪丽热巴");
        one.add("宋远桥");
        one.add("苏星河");
        one.add("老子");
        one.add("庄子");
        one.add("孙子");
        one.add("洪七公");

        List<String> two = new ArrayList<>();
        two.add("古力娜扎");
        two.add("张无忌");
        two.add("张三丰");
        two.add("赵丽颖");
        two.add("张二狗");
        two.add("张天爱");
        two.add("张三");

        System.out.println("===== 传统 for 循环方式 =====");
        processWithForLoop(one, two);

        System.out.println("\n===== Stream 流式处理方式 =====");
        processWithStream(one, two);
    }

    // 传统 for 循环实现
    private static void processWithForLoop(List<String> one, List<String> two) {
        // 1. 第一个队伍只要名字为3个字的成员
        List<String> oneA = new ArrayList<>();
        for (String name : one) {
            if (name.length() == 3) {
                oneA.add(name);
            }
        }

        // 2. 第一个队伍筛选后取前3人
        List<String> oneB = new ArrayList<>();
        for (int i = 0; i < 3 && i < oneA.size(); i++) {
            oneB.add(oneA.get(i));
        }

        // 3. 第二个队伍只要姓张的成员
        List<String> twoA = new ArrayList<>();
        for (String name : two) {
            if (name.startsWith("张")) {
                twoA.add(name);
            }
        }

        // 4. 第二个队伍筛选后跳过前2人
        List<String> twoB = new ArrayList<>();
        for (int i = 2; i < twoA.size(); i++) {
            twoB.add(twoA.get(i));
        }

        // 5. 合并两个队伍
        List<String> totalNames = new ArrayList<>();
        totalNames.addAll(oneB);
        totalNames.addAll(twoB);

        // 6. 根据姓名创建 Person 对象
        List<Person> totalPersonList = new ArrayList<>();
        for (String name : totalNames) {
            totalPersonList.add(new Person(name));
        }

        // 7. 打印整个队伍的 Person 对象
        for (Person person : totalPersonList) {
            System.out.println(person);
        }
    }

    // Stream 流式处理实现
    private static void processWithStream(List<String> one, List<String> two) {
        Stream.concat(
                        one.stream().filter(s -> s.length() == 3).limit(3),  // 队伍1：3个字且取前3
                        two.stream().filter(s -> s.startsWith("张")).skip(2) // 队伍2：姓张且跳过前2
                )
                .map(Person::new)      // 转换为 Person 对象
                .forEach(System.out::println); // 打印
    }
}

class Person {
    private String name;

    public Person() {}
    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "'}";
    }

    // Getter 和 Setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
