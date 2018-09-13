package com.sp.questionnaire;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoSbApplicationTests {

    @Test
    public void contextLoads() {
        //language=JSON
        String ans = "[\"苹果\", \"香蕉\", \"梨\"]";
        JSONArray array = JSONArray.fromObject(ans);
        System.out.println(array.size());
        System.out.println(array);
    }


}
