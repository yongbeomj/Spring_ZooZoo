package ZooZoo.Service.Share;

import ZooZoo.Domain.DTO.Board.ShareDTO;
import ZooZoo.Domain.DTO.Pagination;
import ZooZoo.Domain.Entity.Board.BoardRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

@Service
public class ShareService {
    @Autowired
    BoardRepository boardRepository;

    public ArrayList<String> Share(String area) {
        System.out.println(area);
        try {
            ArrayList<String> total = new ArrayList<>();
            for (int qq = 1; qq <= 8; qq++) {
                URL url = new URL("https://openapi.gg.go.kr/AnimalSale?Key=d33e0915e37c453abb4d9a94d8f265ed&Type=json&pIndex=" + qq + "&pSize=1000");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                String result = bufferedReader.readLine();
                JSONParser jsonParser = new JSONParser(); // JSON을 쓰기 위해 사용
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result); // 오브젝트에 가져온 데이터 넣기
                JSONArray jsonArray = (JSONArray) jsonObject.get("AnimalSale"); // 오브젝트에 있는 데이터 배열에 넣기
                JSONObject object = (JSONObject) jsonArray.get(1); // head, row가 있는데 이 중 1번째(row)만 오브젝트에 넣기
                JSONArray arr = (JSONArray) object.get("row"); // row(key)의 값(value) 가져와 배열에 넣기
                System.out.println(arr.size());
                ArrayList<String> address = new ArrayList<>();
                ArrayList<String> address2 = new ArrayList<>();
                ArrayList<String> addr = new ArrayList<>();
                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> info = new ArrayList<>();
//                System.out.println(arr);
                if (area == null) {
                    for (int i = 0; i < arr.size(); i++) {
                        if (arr.get(i) != null) {
                            System.out.println("1111111111111111111");
                            JSONObject obj = (JSONObject) arr.get(i); // 오브젝트에 i번째 데이터 넣기
                            JSONObject obj1 = (JSONObject) arr.get(i);
                            // 오브젝트에 있는 값중 REFINE_ROADNM_ADDR(key)의 값(value)을 가져와 배열에 넣기
                            address.add((String) obj.get("REFINE_ROADNM_ADDR"));
                            name.add((String) obj1.get("BIZPLC_NM"));
                            if (qq == 1) {
                                info.add((i + 1) + ":" + address.get(i) + ":" + name.get(i));
//                            System.out.println(info.size());
                            } else {
                                info.add((total.size() + 1) + ":" + address.get(i) + ":" + name.get(i));
                            }
                        }
                        total.add(info.get(i));
                    }
                    return total;
                } else {
                    for (int i = 0; i < arr.size(); i++) {
                        JSONObject obj = (JSONObject) arr.get(i); // 오브젝트에 i번째 데이터 넣기
                        System.out.println("1");
                        JSONObject obj1 = (JSONObject) arr.get(i);
                        System.out.println("2");
                        // 오브젝트에 있는 값중 REFINE_ROADNM_ADDR(key)의 값(value)을 가져와 배열에 넣기
                        address.add((String) obj.get("REFINE_ROADNM_ADDR"));
                        if(area.equals("정보 없음") && address.get(i) == null) {
                        System.out.println("3");
                            address2.add("정보 없음");
                            name.add((String) obj1.get("BIZPLC_NM"));
                            if (qq == 1) {
                                info.add((i + 1) + ":" + address2.get(i)  + ":" + name.get(i));
                            } else {
                                info.add((total.size() + 1) + ":" + address2.get(i) + ":" + name.get(i));
                            }
                            total.add(info.get(i));
                        System.out.println("4");
                        }
                        if(address.get(i).contains("" + area + "")) {
                        System.out.println("5");
                            address2.add(address.get(i));
                            name.add((String) obj1.get("BIZPLC_NM"));
                            if (qq == 1) {
                                info.add((i + 1) + ":" + address2.get(i)  + ":" + name.get(i));
                            } else {
                                info.add((total.size() + 1) + ":" + address2.get(i) + ":" + name.get(i));
                            }
                            System.out.println("123123123 : " + address2.get(i) + " address2Size : " + address2.size());
                            total.add(info.get(i));
                        System.out.println("6");
                        }
                        if(!area.contains("경기도") && address.get(i).contains(" 인천광역시 ")) {
                        System.out.println("7");
                            address2.add("인천광역시");
                            name.add((String) obj1.get("BIZPLC_NM"));
                            if (qq == 1) {
                                info.add((i + 1) + ":" + address2.get(i)  + ":" + name.get(i));
                            } else {
                                info.add((total.size() + 1) + ":" + address2.get(i) + ":" + name.get(i));
                            }
                            total.add(info.get(i));
                        System.out.println("8");
                        }
                        System.out.println("9");
                    }
                        System.out.println("10");
                }
            }
            return total;
        } catch(Exception e){
            //            System.out.println(e.getMessage());
            return null;
        }
    }
}