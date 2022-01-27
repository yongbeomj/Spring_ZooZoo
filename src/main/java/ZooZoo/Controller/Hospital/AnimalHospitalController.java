package ZooZoo.Controller.Hospital;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnimalHospitalController {
    @GetMapping("/hospitalboard")
    public String hospitalboard(){
        return "Board/Hospital/HospitalBoard";
    }

    @GetMapping("/hospitalmap")
    public String hospitalmap(){

        return "Board/Hospital/HospitalMap";
    }
}
