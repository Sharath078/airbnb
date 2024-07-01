package com.myblog.myblog34;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestClass1 {
    public static void main(String[] args) {
//        List<Employee> employees = Arrays.asList(
//                new Employee("mike", 23, "chennai"),
//                new Employee("raju", 34, "mangalore"),
//                new Employee("umesh", 23, "pune"),
//                new Employee("sharath", 25, "Bengaluru")
//        );
//
//        List<Employee> emps = employees.stream()
//                .filter(emp -> emp.getAge() > 30)
//                .collect(Collectors.toList());
//
//        for (Employee e : emps) {
//            System.out.println(e.getCity());
//            System.out.println(e.getAge());
//            System.out.println(e.getName());
//        }
//
//     List<Employee> emp1 =   employees.stream().filter(emp->emp.getName().startsWith("u")).collect(Collectors.toList());
//        for (Employee e : emp1) {
//            System.out.println(e.getCity());
//            System.out.println(e.getAge());
//            System.out.println(e.getName());
//        }
                List<Integer> numbers = Arrays.asList(10,12,6,5,7,8,9);

        List<Integer> data = numbers.stream().filter(n1 -> n1 % 2 == 0).map(n2 -> n2 * n2).collect(Collectors.toList());
        System.out.println(data);
    }
}
