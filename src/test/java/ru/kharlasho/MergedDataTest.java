package ru.kharlasho;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ru.kharlasho.Data.MergedData;

public class MergedDataTest {
    private  String[] defaultKeys = { "Test01", "Test02" };
    private  MergedData mergedData;

    @Before
    public void initialize() {
        mergedData = new MergedData(defaultKeys);
    }

    @Test
    public void putDataTestFirstInput(){
        HashMap<String, Integer> param = getParam();
        JSONObject actual = new JSONObject();
        JSONObject expected = new JSONObject();
        expected.put("test01", 5);
        expected.put("test02", 41);
        mergedData.putData(param);
        actual = mergedData.getMarks();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void putDataTestSecondInput(){
        HashMap<String, Integer> param = getParam();
        JSONObject actual = new JSONObject();
        JSONObject expected = new JSONObject();
        expected.put("test01", 15);
        expected.put("test02", 123);
            mergedData.putData(param);
            mergedData.putData(param);
            mergedData.putData(param);
        actual = mergedData.getMarks();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void putDataTestThirdInput(){
        HashMap<String, Integer> param = getParam();
        HashMap<String, Integer> adition = new HashMap<String, Integer>();
        JSONObject actual = new JSONObject();
        JSONObject expected = new JSONObject();
        adition.put("Test03", 62);
        expected.put("test01", 5);
        expected.put("test02", 41);
        expected.put("test03", 62);
            mergedData.putData(param);
            mergedData.putData(adition);
        actual = mergedData.getMarks();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void putDataTestForthInput(){
        HashMap<String, Integer> adition = new HashMap<String, Integer>();
        JSONObject actual = new JSONObject();
        JSONObject expected = new JSONObject();
        adition.put("Test03", 62);
        expected.put("test01", JSONObject.NULL);
        expected.put("test02", JSONObject.NULL);
        expected.put("test03", 62);
            mergedData.putData(adition);
        actual = mergedData.getMarks();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void putDataTestFifthInput(){
        HashMap<String, Integer> param = new HashMap<String, Integer>();
        JSONObject actual = new JSONObject();
        JSONObject expected = new JSONObject();
        expected.put("test01", 5);
        expected.put("test02", 41);
        param.put("test01", 5);
        param.put("tESt02", 41);
        mergedData.putData(param);
        actual = mergedData.getMarks();
        assertEquals(expected.toString(), actual.toString());
    }

    public HashMap<String, Integer> getParam(){
        HashMap<String, Integer> param = new HashMap<String, Integer>();
        param.put("Test01", 5);
        param.put("Test02", 41);
        return param;
    }
}
