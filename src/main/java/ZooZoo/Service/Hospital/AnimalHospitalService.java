package ZooZoo.Service.Hospital;

import ZooZoo.Controller.Hospital.HospitalDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

@Service
public class AnimalHospitalService {


    // 총 토탈 값 가져오는 함수
    public int getapitotal(){
        try {
            String asd2 [] = new String[2];
            String test[] = new String[2];
            URL url = new URL("https://openapi.gg.go.kr/Animalhosptl?KEY=ccec045fa339456eaefa46cb9c828bc8&Type=json&pIndex=1&pSize=1000");
            BufferedReader bf = new BufferedReader( new InputStreamReader( url.openStream() , "UTF-8") );
            String result = bf.readLine();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONArray jsonArray = (JSONArray)jsonObject.get("Animalhosptl");
            JSONObject jsonObject3 = (JSONObject) jsonArray.get(0);
            JSONArray jsonArray2 = (JSONArray)jsonObject3.get("head");
            String asd = jsonArray2.get(0).toString();
            asd2 = asd.split(":");
            test = asd2[1].split("}");
            /*System.out.println("값나와요?");*/
            int total = Integer.parseInt(test[0]);
            return total;
        }catch(Exception e){
            return 0;
        }
    }

    // 토탈값에 맞게 array를 합친 함수
    public JSONArray getapi(int total){
        try {
            JSONArray arr = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            JSONArray rowArray = new JSONArray();
            int defdt = total/1000;
            String s_defdt = null;

            for(int i = 1; i<=defdt+1; i++){
                s_defdt = Integer.toString(defdt);
                String s_i = Integer.toString(i);
                URL url = new URL("https://openapi.gg.go.kr/Animalhosptl?KEY=ccec045fa339456eaefa46cb9c828bc8&Type=json&pIndex="+s_i+"&pSize=1000");
                BufferedReader bf = new BufferedReader( new InputStreamReader( url.openStream() ,"UTF-8") );
                String result = bf.readLine();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject2 = (JSONObject)jsonParser.parse(result);
                JSONArray jsonArray = (JSONArray)jsonObject2.get("Animalhosptl");
                JSONObject jsonObject3 = (JSONObject) jsonArray.get(1);
                rowArray = (JSONArray)jsonObject3.get("row");
                arr.addAll(rowArray);

            }
            return arr;
        }catch(Exception e){
            return null;
        }
    }

    // 내가 원하는 값 파싱하기
    public ArrayList<HospitalDto> parseapi(JSONArray jsonArray){
        JSONObject jsonObject = new JSONObject();
        ArrayList<HospitalDto> parse = new ArrayList<>();
        try{
            for(int i = 0; i<jsonArray.size(); i++){
                jsonObject = (JSONObject)jsonArray.get(i);
                HospitalDto hospitalDto = new HospitalDto(
                        (String)jsonObject.get("BIZPLC_NM"), // 병원 이름
                        (String)jsonObject.get("REFINE_ROADNM_ADDR"), // 주소
                        (String)jsonObject.get("BSN_STATE_NM"),// 영업 상태명
                        (String)jsonObject.get("LOCPLC_FACLT_TELNO"), // 전화번호
                        (String)jsonObject.get("REFINE_WGS84_LOGT"), // 위도
                        (String)jsonObject.get("REFINE_WGS84_LAT"), // 경도
                        (String)jsonObject.get("SIGUN_NM"), // 시 정보
                        (String)jsonObject.get("SIGUN_CD") // 시 정보코드
                );
                parse.add(hospitalDto);
            }
            return parse;
        }catch (Exception e){

        } return parse;
    }








}
