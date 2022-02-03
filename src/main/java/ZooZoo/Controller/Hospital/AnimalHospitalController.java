package ZooZoo.Controller.Hospital;

import ZooZoo.Service.Hospital.AnimalHospitalService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class AnimalHospitalController {

    @Autowired
    AnimalHospitalService animalHospitalService;

    HospitalDto hospitalDto = new HospitalDto();

    // 동물병원 API 게시판 출력하기
    @GetMapping("/hospitalboard")
    public String hospitalboard(Model model){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        ArrayList<HospitalDto> parses = new ArrayList<>();

        jsonArray = animalHospitalService.getapi(animalHospitalService.getapitotal());
        parses = animalHospitalService.parseapi(jsonArray);
        model.addAttribute("parses",parses);

        return "Board/Hospital/HospitalBoard";
    }

    // 동물병원맵 작동시키기
    @GetMapping("/hospitalmap")
    public String hospitalmap(Model model){
        System.out.println("값은 찍히냐?"+ hospitalDto);
        model.addAttribute("hospitalDto", hospitalDto);

        return "Board/Hospital/HospitalMap";
    }


    // 동물병원 맵 임시 api
    @GetMapping("/testmap.json")
    @ResponseBody
    public JSONObject testmap(){
        JSONObject jsonObject = new JSONObject(); // json 전체 [ 응답 용 ]
        jsonObject.put("hospital" ,  hospitalDto );  // json 전체에 리스트 넣기

        return jsonObject;
    }

    // 동물병원 맵 컨트롤러 주고받을 데이터 정하기
    @PostMapping("/hospitalmapcontroller")
    @ResponseBody
    public String hsmapcontroller(@RequestBody HospitalDto hospitalDto2, Model model){
        System.out.println("값"+ hospitalDto2);
        hospitalDto.setLogt(hospitalDto2.getLogt()); // 위도
        hospitalDto.setLat(hospitalDto2.getLat()); // 경도
        hospitalDto.setBizplcnm(hospitalDto2.getBizplcnm()); // 병원 이름
        hospitalDto.setRefineroadnmaddr(hospitalDto2.getRefineroadnmaddr()); // 병원 주소
        hospitalDto.setBsnstatenm(hospitalDto2.getBsnstatenm()); // 영업 상태명
        hospitalDto.setLocplcfaclttelno(hospitalDto2.getLocplcfaclttelno()); // 전화번호
        hospitalDto.setSigunnm(hospitalDto2.getSigunnm()); // 시 정보
        hospitalDto.setSiguncd(hospitalDto2.getSiguncd()); // 시 정보코드

        return "1";
    }

    // 동물병원 api 테스트
    @GetMapping("/hospitaltest")
    @ResponseBody
    public String hospital(Model model){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonArray = animalHospitalService.getapi(animalHospitalService.getapitotal());

        return jsonArray.toString();
    }


}
