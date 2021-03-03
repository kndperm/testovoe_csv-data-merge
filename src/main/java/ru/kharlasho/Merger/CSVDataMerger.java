package ru.kharlasho.Merger;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kharlasho.Data.MergedData;

public class CSVDataMerger {
    private String[] defaultKeys;
    private final static Logger log = LoggerFactory.getLogger(CSVDataMerger.class);

    public MergedData DataMergeFromFolder(File folder) {
        MergedData mergedData = new MergedData(defaultKeys);
        log.info("Read files from folder: " + folder.getName());
        File[] csvFiles = getFilesFromFolder(folder);
        for (File file : csvFiles) {
            log.info("Read from file: " + file.getName());
            List<String> fileData = readFile(file);
            HashMap<String, Integer> marksFromFile = DataConvertion(fileData);
            log.info("Merge data from this file");
            mergedData.putData(marksFromFile);
        }
        return mergedData;
    }

    public void setDefaultKeys(String[] defaultKeys) {
        this.defaultKeys = defaultKeys;
    }

    public String[] getDefaultKeys() {
        return defaultKeys;
    }

    private File[] getFilesFromFolder(File folder) {
        File[] csvFiles = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".csv");
            }
        });
        return csvFiles;
    }

    private List<String> readFile(File file) {
        List<String> result = new ArrayList<String>();
        try {
            result = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public HashMap<String, Integer> DataConvertion(List<String> data) {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        for (String line : data) {
            if (!isLineAComment(line)) {
                String[] d = line.split(","); // d[0] - key, d[1] - value
                if (!isKeyExist(result, d[0]))
                    result.put(d[0], Integer.parseInt(d[1]));
                else {
                    Integer newValue = result.get(d[0]) + Integer.parseInt(d[1]);
                    result.put(d[0], newValue);
                }
            }
        }
        return result;
    }

    private boolean isKeyExist(HashMap<String, Integer> map, String key) {
        return map.containsKey(key);
    }

    private boolean isLineAComment(String line) {
        return line.charAt(0) == '#';
    }
}
