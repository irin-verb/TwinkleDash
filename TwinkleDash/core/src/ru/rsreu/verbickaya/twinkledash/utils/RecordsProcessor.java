package ru.rsreu.verbickaya.twinkledash.utils;

import java.io.*;
import java.util.*;

public class RecordsProcessor {

    private static final String PATH = Assets.RECORD_PATH;

    public static String getRecordsText() {
        StringBuilder text = new StringBuilder("Top 10 records:" + "\n" + "\n");
        List<String> records = getTopRecords();
        int i = 1;
        for (String record: records) {
            text.append(i);
            text.append(". " );
            text.append(record);
            text.append("\n");
            i++;
        }
        return text.toString();
    }

    private static List<String> getTopRecords() {
        Map<String, Integer> recordsMap = new HashMap<>();
        try {
            File file = new File(PATH);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");

                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);

                    recordsMap.put(name, score);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) { }

        List<Map.Entry<String, Integer>> sortedRecords = new ArrayList<>(recordsMap.entrySet());
        sortedRecords.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        List<String> topRecords = new ArrayList<>();
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedRecords) {
            topRecords.add(entry.getKey() + " " + entry.getValue());
            count++;
            if (count >= 10) {
                break;
            }
        }
        return topRecords;
    }

    public static void addRecord(String name, int record) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH, true));
            writer.write(name + " " + record + "\n");
            writer.close();
        } catch (IOException e) {}
    }

    public static boolean isInTop(int record) {
        List<Integer> topRecords = new ArrayList<>();

        try {
            File file = new File(PATH);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");

                if (parts.length == 2) {
                    int score = Integer.parseInt(parts[1]);
                    topRecords.add(score);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {}

        topRecords.sort(Comparator.reverseOrder());

        return topRecords.size() < 10 || record > topRecords.get(9);
    }

    public static void clearRecords() {
        try {
            FileWriter fileWriter = new FileWriter(PATH, false);
            fileWriter.close();
        } catch (IOException e) { };
    }

}
