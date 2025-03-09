package com.oinym.generated;

import java.io.*;
import java.util.*;

public class CSVParser {

  public static <T> List<T> parseCSV(File file, Class<T> clazz) throws Exception {
    List<T> resultList = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String headerLine = br.readLine();
      if (headerLine == null) {
        throw new IllegalArgumentException("Empty CSV file");
      }

      String[] headers = headerLine.split(",");
      List<String> headerList = Arrays.asList(headers);

      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        T obj = clazz.getDeclaredConstructor().newInstance();

        for (int i = 0; i < values.length; i++) {
          String fieldName = headerList.get(i).trim();
          String value = values[i].trim();

          try {
            var field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
          } catch (NoSuchFieldException ignored) {
            // Ignore missing fields
          }
        }

        resultList.add(obj);
      }
    }
    return resultList;
  }
}
