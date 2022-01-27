package ZooZoo.Service.Share;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

@Service
public class ShareService {
    public void Share() {
        try {
            URL url = new URL("https://openapi.gg.go.kr/AnimalSale?Key=d33e0915e37c453abb4d9a94d8f265ed&Type=json&pIndex=1&pSize=100");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String result = bufferedReader.readLine();
            JSONParser jsonParser = new JSONParser(); // JSON을 쓰기 위해 사용
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result); // 오브젝트에 가져온 데이터 넣기
            JSONArray jsonArray = (JSONArray) jsonObject.get("AnimalSale"); // 오브젝트에 있는 데이터 배열에 넣기
            JSONObject object = (JSONObject) jsonArray.get(1); // head, row가 있는데 이 중 1번째(row)만 오브젝트에 넣기
            JSONArray arr = (JSONArray) object.get("row"); // row(key)의 값(value) 가져와 배열에 넣기
//            System.out.println(arr);
            ArrayList<String> ddddd = new ArrayList<>();
            for(int i = 0 ; i < arr.size(); i++) {
                if(arr.get(i) != null) {
                    JSONObject obj = (JSONObject) arr.get(i); // 오브젝트에 i번째 데이터 넣기
                    // 오브젝트에 있는 값중 REFINE_ROADNM_ADDR(key)의 값(value)을 가져와 배열에 넣기
                    ddddd.add((String) obj.get("REFINE_ROADNM_ADDR"));
                }
                System.out.println(ddddd.get(i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}