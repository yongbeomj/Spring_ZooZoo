package ZooZoo.Service.Hospital;

import ZooZoo.Controller.Hospital.HospitalDto;
import ZooZoo.Domain.DTO.Pagination;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 검색 알고리즘 테스트 값 뽑아내기
    public ArrayList<HospitalDto> parseapisearch(JSONArray jsonArray, String keyword, String search, String status){
        JSONObject jsonObject = new JSONObject();
        ArrayList<HospitalDto> parse = new ArrayList<>();
        ArrayList<HospitalDto> parse2 = new ArrayList<>();
        String temp = "";
        try{
            System.out.println(status);
            System.out.println(search);
            System.out.println(keyword);
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

            if(keyword != null || search != null || status != null){
                System.out.println(status);
                System.out.println(search);
                System.out.println(keyword);

                if(status.equals("")&&keyword.equals("")&&keyword.equals("")){
                    parse2.clear();
                    for(int i = 0; i<parse.size(); i++){ // 해당 크기만큼의 사이즈를 가지고와서
                        if(parse.get(i).getBsnstatenm().matches("(.*)"+"정상"+"(.*)")){ // 값이 일치한다면
                            HospitalDto hospitalDto = new HospitalDto(
                                    parse.get(i).getBizplcnm(), // 병원 이름
                                    parse.get(i).getRefineroadnmaddr(), // 주소
                                    parse.get(i).getBsnstatenm(),// 영업 상태명
                                    parse.get(i).getLocplcfaclttelno(), // 전화번호
                                    parse.get(i).getLogt(), // 위도
                                    parse.get(i).getLat(), // 경도
                                    parse.get(i).getSigunnm(), // 시 정보
                                    parse.get(i).getSiguncd() // 시 정보코드
                            );
                            parse2.add(hospitalDto);
                        }
                    }
                    return parse2;
                }
            if(status.equals("정상")) {
                if (keyword.equals("병원")) {
                    parse2.clear();
                    System.out.println(parse.size());
                    for (int i = 0; i < parse.size(); i++) { // 해당 크기만큼의 사이즈를 가지고와서
                        System.out.println("전체 서칭"+i);
                        if (parse.get(i).getBizplcnm().matches("(.*)"+search+"(.*)")&&
                                parse.get(i).getBsnstatenm().matches("(.*)"+status+"(.*)")) { // 값이 일치한다면
                            System.out.println("서칭안함"+i);
                            HospitalDto hospitalDto = new HospitalDto(
                                    parse.get(i).getBizplcnm(), // 병원 이름
                                    parse.get(i).getRefineroadnmaddr(), // 주소
                                    parse.get(i).getBsnstatenm(),// 영업 상태명
                                    parse.get(i).getLocplcfaclttelno(), // 전화번호
                                    parse.get(i).getLogt(), // 위도
                                    parse.get(i).getLat(), // 경도
                                    parse.get(i).getSigunnm(), // 시 정보
                                    parse.get(i).getSiguncd() // 시 정보코드
                            );
                            parse2.add(hospitalDto);
                        }
                    }
                    return parse2;
                }
                if (keyword.equals("주소") || keyword == "주소") {
                    parse2.clear();
                    System.out.println(parse.size());
                    for (int i = 0; i < parse.size(); i++) { // 해당 크기만큼의 사이즈를 가지고와서
                        System.out.println("`````````````````````````````````````````" + search);
                        System.out.println("전체 서칭"+i);
                        System.out.println(parse.get(i).getRefineroadnmaddr());
                        if(parse.get(i).getRefineroadnmaddr() == null || parse.get(i).getRefineroadnmaddr().equals("")){
                            parse.get(i).setRefineroadnmaddr("");

                        }

                        System.out.println(parse.get(i).getRefineroadnmaddr());
                        System.out.println(parse.size());
                        if (parse.get(i).getRefineroadnmaddr().matches("(.*)"+search + "(.*)")&&
                                parse.get(i).getBsnstatenm().matches("(.*)"+status+"(.*)")) { // 값이 일치한다면
                            System.out.println(parse.get(i).getRefineroadnmaddr());

                            System.out.println("중간"+i);
                            HospitalDto hospitalDto = new HospitalDto(
                                    parse.get(i).getBizplcnm(), // 병원 이름
                                    parse.get(i).getRefineroadnmaddr(), // 주소
                                    parse.get(i).getBsnstatenm(),// 영업 상태명
                                    parse.get(i).getLocplcfaclttelno(), // 전화번호
                                    parse.get(i).getLogt(), // 위도
                                    parse.get(i).getLat(), // 경도
                                    parse.get(i).getSigunnm(), // 시 정보
                                    parse.get(i).getSiguncd() // 시 정보코드
                            );
                            parse2.add(hospitalDto);
                        }
                    }
                    return parse2;
                }
            }
            if(status.equals("폐업")) {
                if (keyword.equals("병원")) {
                    parse2.clear();
                    for (int i = 0; i < parse.size(); i++) { // 해당 크기만큼의 사이즈를 가지고와서
                        if (parse.get(i).getBizplcnm().matches("(.*)"+search+"(.*)")&&
                                parse.get(i).getBsnstatenm().matches("(.*)"+status+"(.*)")) { // 값이 일치한다면
                            HospitalDto hospitalDto = new HospitalDto(
                                    parse.get(i).getBizplcnm(), // 병원 이름
                                    parse.get(i).getRefineroadnmaddr(), // 주소
                                    parse.get(i).getBsnstatenm(),// 영업 상태명
                                    parse.get(i).getLocplcfaclttelno(), // 전화번호
                                    parse.get(i).getLogt(), // 위도
                                    parse.get(i).getLat(), // 경도
                                    parse.get(i).getSigunnm(), // 시 정보
                                    parse.get(i).getSiguncd() // 시 정보코드
                            );
                            parse2.add(hospitalDto);
                        }
                    }
                    return parse2;
                }
                if (keyword.equals("주소") || keyword == "주소") {
                    parse2.clear();
                    for (int i = 0; i < parse.size(); i++) { // 해당 크기만큼의 사이즈를 가지고와서
                        System.out.println("`````````````````````````````````````````" + search);
                        if(parse.get(i).getRefineroadnmaddr() == null || parse.get(i).getRefineroadnmaddr().equals("")){
                            parse.get(i).setRefineroadnmaddr("");

                        }
                        if (parse.get(i).getRefineroadnmaddr().matches( "(.*)"+search+"(.*)")&&
                                parse.get(i).getBsnstatenm().matches("(.*)"+status+"(.*)")) { // 값이 일치한다면
                            System.out.println(parse.get(i).getRefineroadnmaddr());
                            HospitalDto hospitalDto = new HospitalDto(
                                    parse.get(i).getBizplcnm(), // 병원 이름
                                    parse.get(i).getRefineroadnmaddr(), // 주소
                                    parse.get(i).getBsnstatenm(),// 영업 상태명
                                    parse.get(i).getLocplcfaclttelno(), // 전화번호
                                    parse.get(i).getLogt(), // 위도
                                    parse.get(i).getLat(), // 경도
                                    parse.get(i).getSigunnm(), // 시 정보
                                    parse.get(i).getSiguncd() // 시 정보코드
                            );
                            parse2.add(hospitalDto);
                        }
                    }
                    return parse2;
                }
            }
                if(status.equals("선택")) {
                    if (search == null || search.equals("")) {
                        return parse;
                    }

                    if (keyword.equals("병원")) {
                        parse2.clear();
                        for (int i = 0; i < parse.size(); i++) { // 해당 크기만큼의 사이즈를 가지고와서
                            if (parse.get(i).getBizplcnm().matches("(.*)"+search+"(.*)")&&
                                    parse.get(i).getBsnstatenm().matches("(.*)"+status+"(.*)")) { // 값이 일치한다면
                                HospitalDto hospitalDto = new HospitalDto(
                                        parse.get(i).getBizplcnm(), // 병원 이름
                                        parse.get(i).getRefineroadnmaddr(), // 주소
                                        parse.get(i).getBsnstatenm(),// 영업 상태명
                                        parse.get(i).getLocplcfaclttelno(), // 전화번호
                                        parse.get(i).getLogt(), // 위도
                                        parse.get(i).getLat(), // 경도
                                        parse.get(i).getSigunnm(), // 시 정보
                                        parse.get(i).getSiguncd() // 시 정보코드
                                );
                                parse2.add(hospitalDto);
                            }
                        }
                        return parse2;
                    }
                    if (keyword.equals("주소") || keyword == "주소") {
                        parse2.clear();
                        for (int i = 0; i < parse.size(); i++) { // 해당 크기만큼의 사이즈를 가지고와서
                            System.out.println("`````````````````````````````````````````" + search);
                            if (parse.get(i).getRefineroadnmaddr().matches( "(.*)"+search+"(.*)")&&
                                    parse.get(i).getBsnstatenm().matches("(.*)"+status+"(.*)")) { // 값이 일치한다면
                                System.out.println(parse.get(i).getRefineroadnmaddr());
                                HospitalDto hospitalDto = new HospitalDto(
                                        parse.get(i).getBizplcnm(), // 병원 이름
                                        parse.get(i).getRefineroadnmaddr(), // 주소
                                        parse.get(i).getBsnstatenm(),// 영업 상태명
                                        parse.get(i).getLocplcfaclttelno(), // 전화번호
                                        parse.get(i).getLogt(), // 위도
                                        parse.get(i).getLat(), // 경도
                                        parse.get(i).getSigunnm(), // 시 정보
                                        parse.get(i).getSiguncd() // 시 정보코드
                                );
                                parse2.add(hospitalDto);
                            }
                        }
                        return parse2;
                    }
                }

                return parse2;
            }
        }catch (Exception e){

        } return parse2;
    }


    // 내가 원하는 값 파싱해서 다 가져오기
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

    // 페이징 처리 값 가져와서
    public ArrayList<HospitalDto> parsenum(ArrayList<HospitalDto> parses, int page){
        ArrayList<HospitalDto> parsepage = new ArrayList<>();
        Pagination pagination = new Pagination();
        /*시작 페이지 값을 가져온다*/
        /*int page */
        /*화면에 뿌릴 페이지 사이즈 가져오기 */
        int pagesize = pagination.getPageSize();

        // 끝 페이지
        int maxPage = page * pagesize;

        // 시작페이지
        int minPage = (maxPage-pagesize)+1;  // maxPage - maxpage-pagesize   1000 -

        // 전체 리스트의 사이즈의 갯수보다 maxPage가 크다면 maxPage를 parses.size()값을 줘서 값을 맞추는것임
        if(maxPage > parses.size()){
            maxPage = parses.size();
            /*minPage = */
        }
//        System.out.println("시작 페이지 입니다." + minPage);
//        System.out.println("마지막 페이지 입니다." + maxPage);

        for(int i = minPage-1; i<maxPage; i++){
            parsepage.add(parses.get(i));
//            System.out.println(i);
        }

        return parsepage;
    }
}