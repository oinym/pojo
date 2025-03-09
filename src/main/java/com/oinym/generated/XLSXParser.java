package com.oinym.generated;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class XLSXParser {
  public static <T> List<T> parseXLSX(File file, Class<T> clazz, Map<String, String> fieldMapping) throws Exception {
    List<T> resultList = new ArrayList<>();
    List<String> headers = new ArrayList<>();
    List<List<String>> rows = new ArrayList<>();

    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(file))) {
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {
        if (entry.getName().equals("xl/worksheets/sheet1.xml")) {
          BufferedReader br = new BufferedReader(new InputStreamReader(zis));
          String line;
          List<String> row = new ArrayList<>();
          boolean headerCaptured = false;

          while ((line = br.readLine()) != null) {
            if (line.contains("<v>")) {
              String value = line.substring(line.indexOf("<v>") + 3, line.indexOf("</v>"));
              row.add(value);
            } else if (line.contains("</row>")) {
              if (!headerCaptured) {
                headers.addAll(row);
                headerCaptured = true;
              } else {
                rows.add(new ArrayList<>(row));
              }
              row.clear();
            }
          }
        }
      }
    }

    for (List<String> rowData : rows) {
      T obj = clazz.getDeclaredConstructor().newInstance();
      for (int i = 0; i < headers.size(); i++) {
        String fileColumn = headers.get(i).trim();
        String javaField = fieldMapping.get(fileColumn);

        if (javaField != null && i < rowData.size()) {
          try {
            Field field = clazz.getDeclaredField(javaField);
            field.setAccessible(true);
            field.set(obj, rowData.get(i).trim());
          } catch (NoSuchFieldException ignored) {
            // Ignore missing fields
          }
        }
      }
      resultList.add(obj);
    }

    return resultList;
  }
}
