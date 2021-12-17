package com.kkk.kspring2;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class PQueTest {
    @Test
    public void test1() {

        PriorityQueue<Student> pq1 = new PriorityQueue<>();	// Comparable 정렬 순서
        PriorityQueue<Student> pq2 = new PriorityQueue<>(comp);	// Comparator 정렬 순서

        Random rnd = new Random();

        char name = 'A';
        for (int i = 0; i < 10; i++) {
            int math = rnd.nextInt(10);
            int science = rnd.nextInt(10);

            pq1.offer(new Student(name, math, science));
            pq2.offer(new Student(name, math, science));
            name++;
        }

        // 힙 내부 배열의 요소 상태
        System.out.println("[pq1 내부 배열 상태]");
        for (Object val : pq1.toArray()) {
            System.out.print(val);
        }
        System.out.println();
        System.out.println();

        // 힙 내부 배열의 요소 상태
        System.out.println("[pq2 내부 배열 상태]");
        for (Object val : pq2.toArray()) {
            System.out.print(val);
        }
        System.out.println();
        System.out.println();


        System.out.println("[수학-과학-이름순 뽑기]");
        System.out.println("name\tmath\tscience");
        while(!pq1.isEmpty()) {
            System.out.print(pq1.poll());
        }
        System.out.println();
        System.out.println("[과학-수학-이름순 뽑기]");
        System.out.println("name\tmath\tscience");
        while(!pq2.isEmpty()) {
            System.out.print(pq2.poll());
        }
    }


    private static Comparator<Student> comp = new Comparator<Student>() {
        /*
         * 과학점수가 높은 순
         * 과학점수가 같을 경우 수학 점수가 높은순
         * 둘 다 같을 경우 이름순
         */
        @Override
        public int compare(Student o1, Student o2) {
            if(o1.science == o2.science) {
                if(o1.math == o2.math) {
                    return o1.name - o2.name;
                }
                return o2.math - o1.math;
            }
            return o2.science - o1.science;
        }
    };

    static class Student implements Comparable<Student> {

        char name;
        int math;
        int science;

        public Student(char name, int math, int science) {
            this.name = name;
            this.math = math;
            this.science = science;
        }

        /*
         * 수학점수가 높은 순
         * 수학점수가 같을 경우 과학 점수가 높은순
         * 둘 다 같을 경우 이름순
         */
        @Override
        public int compareTo(Student o) {
            if (this.math == o.math) {

                if (this.science == o.science) {
                    return this.name - o.name;
                }
                return o.science - this.science;
            }
            return o.math - this.math;
        }

        public String toString() {
            return name + "\t" + math + "\t" + science + "\n";
        }
    }

}
