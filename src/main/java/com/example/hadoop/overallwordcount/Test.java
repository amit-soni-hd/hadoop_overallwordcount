package com.example.hadoop.overallwordcount;

import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        long task;
        task = sc.nextLong();
        Test test = new Test();

        while (task-- > 0) {

            Map<Long, Pair<Long, Long>> pairs = new HashMap();
            long size, noOfSubArray;
            size = sc.nextLong();
            noOfSubArray = sc.nextLong();

            ArrayList<Long> aliceArray = new ArrayList<>();
            test.inputArrayElement(size, aliceArray, pairs, sc);
            ArrayList<Long> bobArray = (ArrayList<Long>) aliceArray.clone();
            ArrayList<Pair<Long, Long>> subArray = new ArrayList<>();
            test.inputSubArrayIndex(noOfSubArray, subArray, pairs, sc);
            bobArray.sort(Collections.reverseOrder());

            bobArray = test.getBobArray(pairs.values().stream().collect(Collectors.toList()), bobArray);
            test.addPreviousElement(bobArray, aliceArray);

//            System.out.println(aliceArray);
//            System.out.println(bobArray);

            test.calculateDifference(aliceArray, bobArray, subArray);

        }
    }

    private ArrayList<Long> getBobArray(List<Pair<Long, Long>> pairs, ArrayList<Long> array) {
        Comparator<Pair<Long, Long>> comparator = (o1, o2) -> {
            Long value1 = o1.getValue();
            Long value2 = o2.getValue();
            return value2.compareTo(value1);
        };

        pairs.sort(comparator);

        return (ArrayList<Long>) pairs.stream()
                .map((pair) -> array.get(Math.toIntExact(pair.getKey())))
                .collect(Collectors.toList());

    }


    private void addPreviousElement(List<Long> bob, List<Long> alice) {
        for (int i = 1; i < bob.size(); i++) {
            bob.set(i, bob.get(i) + bob.get(i - 1));
            alice.set(i, alice.get(i) + alice.get(i - 1));
        }

    }

    private void inputSubArrayIndex(long noOfSubArray, ArrayList<Pair<Long, Long>> subArray, Map<Long, Pair<Long, Long>> pairs, Scanner sc) {
        for (long i = 0; i < noOfSubArray; i++) {
            long first = sc.nextLong();
            long second = sc.nextLong();
            subArray.add(new Pair<>(first - 1, second - 1));
            addOccurrenceOfElement(first, second, pairs);
        }

    }

    private void inputArrayElement(long size, ArrayList<Long> aliceArray, Map<Long, Pair<Long, Long>> pairs, Scanner sc) {
        for (long i = 0L; i < size; i++) {
            aliceArray.add(sc.nextLong());
            pairs.put(i, new Pair(i, 0L));
        }
    }

    private void addOccurrenceOfElement(long start, long end, Map<Long, Pair<Long, Long>> pairs) {

        for (long f = start - 1; f < end; f++) {
                Pair<Long, Long> longLongPair = pairs.get(f);
                pairs.put(f, new Pair<>(longLongPair.getKey(), longLongPair.getValue() + 1));
        }

    }

    private void calculateDifference(ArrayList<Long> aliceArray, ArrayList<Long> bob, List<Pair<Long, Long>> subArray) {
        AtomicLong sum = new AtomicLong(0L);
        subArray.forEach((pair) -> {
            long start = pair.getKey();
            long end = pair.getValue();

            Long aliceSum = aliceArray.get((int) end);
            Long bobSum = bob.get((int) end);

            if (start > 0) {
                aliceSum = aliceSum - aliceArray.get((int) start - 1);
                bobSum = bobSum - bob.get((int) start - 1);
                sum.addAndGet(bobSum - aliceSum);
            } else {
                sum.addAndGet(bobSum - aliceSum);
            }

        });
        System.out.println(sum);
    }

}
