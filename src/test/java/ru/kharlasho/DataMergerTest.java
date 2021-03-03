package ru.kharlasho;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import ru.kharlasho.Merger.CSVDataMerger;

public class DataMergerTest {
    private static CSVDataMerger dataMerger;

    @BeforeClass
    public static void initialize(){
        dataMerger = new CSVDataMerger();
    }

    @Test
    public void dataConvertionTestWithoutComments(){
        List<String> data = new ArrayList<String>();
        HashMap<String, Integer> expected;
        HashMap<String, Integer> actual;
        
        data.add("mark17,24");
        data.add("mark35,567");
        expected = new HashMap<String, Integer>();
        expected.put("mark17", 24);
        expected.put("mark35", 567);
        actual =dataMerger.DataConvertion(data);

        assertEquals(expected, actual);
    }

    @Test
    public void dataConvertionTestWithComments(){
        List<String> data = new ArrayList<String>();
        HashMap<String, Integer> expected;
        HashMap<String, Integer> actual;
        
        data.add("mark17,24");
        data.add("# комментарий");
        data.add("mark35,567");
        expected = new HashMap<String, Integer>();
        expected.put("mark17", 24);
        expected.put("mark35", 567);
        actual =dataMerger.DataConvertion(data);

        assertEquals(expected, actual);
    }

    @Test
    public void dataConvertionTestOnlyComments(){
        List<String> data = new ArrayList<String>();
        HashMap<String, Integer> expected;
        HashMap<String, Integer> actual;
        
        data.add("# комментарий номер раз");
        data.add("# комментарий");
        data.add("# еще один коментарий");
        expected = new HashMap<String, Integer>();
        actual =dataMerger.DataConvertion(data);

        assertEquals(expected, actual);
    }
}
