package ru.kharlasho;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kharlasho.Data.MergedData;
import ru.kharlasho.Merger.CSVDataMerger;

public class App {
    private final static Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        String folderPath = "TestFiles/";
        String[] defaultKeys = { "mark01", "mark17", "mark23", "mark35", "markFV", "markFX", "markFT" };
        CSVDataMerger merger = new CSVDataMerger();
        merger.setDefaultKeys(defaultKeys);
        MergedData mergedData = merger.DataMergeFromFolder(new File(folderPath));
        JSONObject json = mergedData.getMarks();
        toFirstJSON(json, folderPath + "first.json");
        log.info("Save first JSON in first.json");
        toSecondJSON(json, folderPath + "second.json");
        log.info("Save second JSON in second.json");
        toThirdJSON(json, folderPath + "third.json");
        log.info("Save third JSON in third.json");
    }

    private static void CreateJSONFile(String strJSON, String fileName) {
        try {
            FileWriter file = new FileWriter(fileName);
            file.write(strJSON);
            file.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static void toFirstJSON(JSONObject obj, String fileName) {
        JSONObject objWithoutNull = new JSONObject();
        for (String key : obj.keySet())
            if (!obj.isNull(key))
                objWithoutNull.put(key, obj.get(key));
        CreateJSONFile(objWithoutNull.toString(), fileName);
    }

    private static void toSecondJSON(JSONObject obj, String fileName) {
        CreateJSONFile(obj.toString(), fileName);
    }

    private static void toThirdJSON(JSONObject obj, String fileName) {
        String strObj = getSortMarksByValue(obj);
        CreateJSONFile(strObj, fileName);
    }

    private static String getSortMarksByValue(JSONObject obj) {
        String result;
        class Mark {
            String key;
            int value;
        }
        List<Mark> list = new ArrayList<Mark>();
        for (String key : obj.keySet())
            if (!obj.isNull(key)) {
                Mark m = new Mark();
                m.key = key;
                m.value = (int) obj.get(key);
                list.add(m);
            }
        Collections.sort(list, new Comparator<Mark>(){
            @Override
            public int compare(Mark o1, Mark o2) {
                return Integer.compare(o2.value, o1.value);
            };
        });
        StringBuilder b = new StringBuilder("{");
        for (Mark mark : list) {
          b.append("\"" + mark.key + "\":"+mark.value + ",");
        }
        b.setCharAt(b.length()-1, '}');
        result = b.toString();
        return result;
    }
}
