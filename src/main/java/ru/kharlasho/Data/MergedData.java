package ru.kharlasho.Data;

import java.util.HashMap;

import org.json.JSONObject;

public class MergedData {
    private JSONObject marks;

    public JSONObject getMarks() {
        return marks;
    }

    public MergedData(String[] defaultKeys) {
        marks = new JSONObject();
        for (String key : defaultKeys) {
            marks.put(key.toLowerCase(), JSONObject.NULL);
        }
    }

    public void putData(HashMap<String, Integer> data) {
        for (String key : data.keySet()) {
            if (isKeyExist(key.toLowerCase()) && !isValueNull(key.toLowerCase()))
                increaseMarks(key.toLowerCase(), data.get(key));
            else
                marks.put(key.toLowerCase(), data.get(key));
        }
    }

    private void increaseMarks(String key, Integer value) {
       int v = (int) marks.get(key);
        Integer newValue = v + value;
        marks.put(key, newValue);
    }

    private boolean isValueNull(String key) {
        return this.marks.isNull(key);
    }

    private boolean isKeyExist(String key) {
        return marks.has(key);
    }
}
