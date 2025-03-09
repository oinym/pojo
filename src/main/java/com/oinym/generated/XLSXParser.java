package com.oinym.generated;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class XLSXParser {
  public static List<List<String>> parseXLSX(File file) throws Exception {
    List<List<String>> rows = new ArrayList<>();
    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(file))) {
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {
        if (entry.getName().equals("xl/worksheets/sheet1.xml")) {
          BufferedReader br = new BufferedReader(new InputStreamReader(zis));
          String line;
          List<String> row = new ArrayList<>();
          while ((line = br.readLine()) != null) {
            if (line.contains("<v>")) {
              String value = line.substring(line.indexOf("<v>") + 3, line.indexOf("</v>"));
              row.add(value);
            } else if (line.contains("</row>")) {
              rows.add(new ArrayList<>(row));
              row.clear();
            }
          }
        }
      }
    }
    return rows;
  }
}
