package com.oinym.generated;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class CSVParser {
  public static <T> List<T> parseCSV(File file, Class<T> clazz, Map<String, String> fieldMapping) throws Exception {
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
          String fileColumn = headerList.get(i).trim();
          String javaField = fieldMapping.get(fileColumn); // Map column to Java field

          if (javaField != null) {
            try {
              Field field = clazz.getDeclaredField(javaField);
              field.setAccessible(true);
              field.set(obj, values[i].trim());
            } catch (NoSuchFieldException ignored) {
              // Ignore missing fields
            }
          }
        }

        resultList.add(obj);
      }
    }
    return resultList;
  }
}
