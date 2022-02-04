package ZooZoo.Service.Loss;

import ZooZoo.Domain.DTO.Board.LossDTO;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class LossService {
    public ArrayList<LossDTO> Losslist() {
        ArrayList<LossDTO> lossDTOS = new ArrayList<>();
        try {
            // max page = 110
            String urlStr = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=20140301&endde=20140430&pageNo=1&numOfRows=110&ServiceKey=1LB54yX2%2BHgedpJ5WPtUlCgis3Wj3ulEoeJorsRTkrxmbfRPO21aodfkeLX%2BJ5UUM8nOZdSDoY18dKpdd6shAA%3D%3D";
            // Instantiate the Factory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(urlStr);

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            // get <staff>
            NodeList list = doc.getElementsByTagName("item");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);
                HashMap<String, String> map = new HashMap<String, String>();
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // get text
                    String age = element.getElementsByTagName("age").item(0).getTextContent();
                    String careAddr = element.getElementsByTagName("careAddr").item(0).getTextContent();
                    String careNm = element.getElementsByTagName("careNm").item(0).getTextContent();
                    String careTel = element.getElementsByTagName("careTel").item(0).getTextContent();
                    String chargeNm = element.getElementsByTagName("chargeNm").item(0).getTextContent();
                    String colorCd = element.getElementsByTagName("colorCd").item(0).getTextContent();
                    String desertionNo = element.getElementsByTagName("desertionNo").item(0).getTextContent();
                    String filename = element.getElementsByTagName("filename").item(0).getTextContent();
                    String happenDt = element.getElementsByTagName("happenDt").item(0).getTextContent();
                    String happenPlace = element.getElementsByTagName("happenPlace").item(0).getTextContent();
                    String kindCd = element.getElementsByTagName("kindCd").item(0).getTextContent();
                    String neuterYn = element.getElementsByTagName("neuterYn").item(0).getTextContent();
                    String noticeEdt = element.getElementsByTagName("noticeEdt").item(0).getTextContent();
                    String noticeNo = element.getElementsByTagName("noticeNo").item(0).getTextContent();
                    String noticeSdt = element.getElementsByTagName("noticeSdt").item(0).getTextContent();
                    String officetel = element.getElementsByTagName("officetel").item(0).getTextContent();
                    String orgNm = element.getElementsByTagName("orgNm").item(0).getTextContent();
                    String popfile = element.getElementsByTagName("popfile").item(0).getTextContent();
                    String processState = element.getElementsByTagName("processState").item(0).getTextContent();
                    String sexCd = element.getElementsByTagName("sexCd").item(0).getTextContent();
                    String specialMark = element.getElementsByTagName("specialMark").item(0).getTextContent();
                    String weight = element.getElementsByTagName("weight").item(0).getTextContent();

                    // dto에 값 넣기
                    LossDTO lossDTO = LossDTO.builder()
                            .age(age).careAddr(careAddr).careNm(careNm).careTel(careTel).chargeNm(chargeNm)
                            .colorCd(colorCd).desertionNo(desertionNo).filename(filename).happenDt(happenDt)
                            .happenPlace(happenPlace).kindCd(kindCd).neuterYn(neuterYn).noticeEdt(noticeEdt)
                            .noticeNo(noticeNo).noticeSdt(noticeSdt).officetel(officetel).orgNm(orgNm)
                            .popfile(popfile).processState(processState).sexCd(sexCd).specialMark(specialMark)
                            .weight(weight).build();
                    lossDTOS.add(lossDTO);
                }
            }
            // System.out.println(lossDTOS);
            return lossDTOS;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}