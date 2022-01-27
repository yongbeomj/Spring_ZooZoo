package ZooZoo.Controller.Api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@RestController
@RequestMapping("/api") // requestparameter로 위치값 넘겨주기
public class HospitalController {

    @GetMapping("/hospital")
    public String HospitalGet() throws Exception {
        String url = "https://openapi.gg.go.kr/Animalhosptl?KEY=ccec045fa339456eaefa46cb9c828bc8&Type=json&plndex=1&pSize=1000";

        HashMap<String, Object> resultMap = getDataFromJson(url, "UTF-8", "get", "");
        System.out.println("# RESULT : " + resultMap);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("data", resultMap);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return jsonObj.toString();
    }

    @GetMapping("/hospital2")
    public String HospitalGet2(@RequestParam("htotal") int htotal) throws Exception {
        System.out.println("값을 출력합니다." + htotal);
        String url = "https://openapi.gg.go.kr/Animalhosptl?KEY=ccec045fa339456eaefa46cb9c828bc8&Type=json&plndex=1&pSize=1000";

        HashMap<String, Object> resultMap = getDataFromJson(url, "UTF-8", "get", "");
        System.out.println("# RESULT : " + resultMap);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("data", resultMap);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return jsonObj.toString();
    }

    public HashMap<String, Object> getDataFromJson(String url, String encoding, String type, String jsonStr) throws Exception {
        boolean isPost = false;
        if ("post".equals(type)) {
            isPost = true;
        } else {
            url = "".equals(jsonStr) ? url : url + "?request=" + jsonStr;
        }

        return getStringFromURL(url, encoding, isPost, jsonStr, "application/json");
    }



    public HashMap<String, Object> getStringFromURL(String url, String encoding, boolean isPost, String parameter, String contentType) throws Exception {
        URL apiURL = new URL(url);
        HttpURLConnection conn = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        try {
            conn = (HttpURLConnection) apiURL.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);

            if (isPost) {
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", contentType);
                conn.setRequestProperty("Accept", "*/*");
            } else {
                conn.setRequestMethod("GET");
            }
            conn.connect();

            if (isPost) {
                bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                bw.write(parameter);
                bw.flush();
                bw = null;
            }
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line=br.readLine()) != null) result.append(line);
            ObjectMapper mapper = new ObjectMapper();
            resultMap = mapper.readValue(result.toString(), HashMap.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(url + " interface failed" + e.toString());
        } finally {
            if (conn != null) conn.disconnect();
            if (br != null) br.close();
            if (bw != null) bw.close();
        }
        return resultMap;
    }
}
