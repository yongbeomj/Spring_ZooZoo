package ZooZoo.Service.Loss;

import ZooZoo.Domain.DTO.Board.LossDTO;
import ZooZoo.Domain.DTO.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class LossService {

    // 필터링(조회) 리스트
    public ArrayList<LossDTO> losslist(String sex, String kind, String city) {
        ArrayList<LossDTO> totLosslist = totlosslist();
        ArrayList<LossDTO> actLosslist = new ArrayList<>();
        ArrayList<LossDTO> getlist = new ArrayList<>();

        for(int i = 0; i < totLosslist.size(); i++){
            if (totLosslist.get(i).getSTATE_NM().contains("보호중")) {
                actLosslist.add(totLosslist.get(i));
            }
        }




        try {
            if ((sex == null && kind == null && city == null) || (sex.equals("total") && kind.equals("total") && city.equals("total"))) {
                return actLosslist; // 초기화면 (검색 없음)
            }
            for (int i = 0; i < actLosslist.size(); i++) {
                // 전체 조건 검색
                if (sex != null && kind != null && city != null && actLosslist.get(i).getSEX_NM().equals(sex) && actLosslist.get(i).getSPECIES_NM().equals(kind) && actLosslist.get(i).getCity().equals(city)) {
                    getlist.add(actLosslist.get(i));
                    // 성별만 total
                } else if ((sex == null || sex.equals("total")) && kind != null && actLosslist.get(i).getSPECIES_NM().equals(kind) && city != null && actLosslist.get(i).getCity().equals(city)) {
                    getlist.add(actLosslist.get(i));
                    // 종류만 total
                } else if ((kind == null || kind.equals("total")) && sex != null && actLosslist.get(i).getSEX_NM().equals(sex) && city != null && actLosslist.get(i).getCity().equals(city)) {
                    getlist.add(actLosslist.get(i));
                    // 시군구만 total
                } else if ((city == null || city.equals("total")) && sex != null && actLosslist.get(i).getSEX_NM().equals(sex) && kind != null && actLosslist.get(i).getSPECIES_NM().equals(kind)) {
                    getlist.add(actLosslist.get(i));
                    // 종류, 시군구 total / 성별만 검색
                } else if (((kind == null && city == null) || (kind.equals("total") && city.equals("total"))) && sex != null && actLosslist.get(i).getSEX_NM().equals(sex)) {
                    getlist.add(actLosslist.get(i));
                    // 성별, 시군구 total / 종류만 검색
                } else if (((sex == null && city == null) || (sex.equals("total") && city.equals("total"))) && kind != null && actLosslist.get(i).getSPECIES_NM().equals(kind)) {
                    getlist.add(actLosslist.get(i));
                    // 성별, 종류 total / 시군구만 검색
                } else if (((sex == null && kind == null) || (sex.equals("total") && kind.equals("total"))) && city != null && actLosslist.get(i).getCity().equals(city)) {
                    getlist.add(actLosslist.get(i));
                }
            }
            return getlist;

        } catch (Exception e) {
        }
        return null;
    }

    // 전체 리스트 (API 데이터 호출)
    public ArrayList<LossDTO> totlosslist() {

        ArrayList<LossDTO> lossDTOS = new ArrayList<>();

        try {
            // max page = 110
            String urlStr = "https://openapi.gg.go.kr/AbdmAnimalProtect?KEY=f116bb9347d04a38a639e01395505d21&pIndex=1&pSize=500";
            // Instantiate the Factory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(urlStr);

            // optional, but recommended
            doc.getDocumentElement().normalize();

            // get <staff>
            NodeList list = doc.getElementsByTagName("row");

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                Element element = (Element) node;


                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    // get text
                    String SIGUN_CD = element.getElementsByTagName("SIGUN_CD").item(0).getTextContent();
                    String SIGUN_NM = element.getElementsByTagName("SIGUN_NM").item(0).getTextContent();
                    String ABDM_IDNTFY_NO = element.getElementsByTagName("ABDM_IDNTFY_NO").item(0).getTextContent();
                    String THUMB_IMAGE_COURS = element.getElementsByTagName("THUMB_IMAGE_COURS").item(0).getTextContent();
                    String RECEPT_DE = element.getElementsByTagName("RECEPT_DE").item(0).getTextContent();
                    String DISCVRY_PLC_INFO = element.getElementsByTagName("DISCVRY_PLC_INFO").item(0).getTextContent();
                    String STATE_NM = element.getElementsByTagName("STATE_NM").item(0).getTextContent();
                    String SPECIES_NM = element.getElementsByTagName("SPECIES_NM").item(0).getTextContent();
                    SPECIES_NM = SPECIES_NM.replace("[", "");
                    String result = SPECIES_NM.split("] ")[0];
                    String details = null;
                    try {
                        details = SPECIES_NM.split("]")[1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        details = "";
                    }
                    String COLOR_NM = element.getElementsByTagName("COLOR_NM").item(0).getTextContent();
                    String AGE_INFO = element.getElementsByTagName("AGE_INFO").item(0).getTextContent();
                    String BDWGH_INFO = element.getElementsByTagName("BDWGH_INFO").item(0).getTextContent();
                    String PBLANC_IDNTFY_NO = element.getElementsByTagName("PBLANC_IDNTFY_NO").item(0).getTextContent();
                    String PBLANC_BEGIN_DE = element.getElementsByTagName("PBLANC_BEGIN_DE").item(0).getTextContent();
                    String PBLANC_END_DE = element.getElementsByTagName("PBLANC_END_DE").item(0).getTextContent();
                    String IMAGE_COURS = element.getElementsByTagName("IMAGE_COURS").item(0).getTextContent();
                    String SEX_NM = element.getElementsByTagName("SEX_NM").item(0).getTextContent();
                    String sex = null;
                    if (SEX_NM.equals("F")) {
                        sex = "암컷";
                    } else if (SEX_NM.equals("M")) {
                        sex = "수컷";
                    } else if (SEX_NM.equals("Q")) {
                        sex = "미상";
                    }
                    String NEUT_YN = element.getElementsByTagName("NEUT_YN").item(0).getTextContent();
                    String SFETR_INFO = element.getElementsByTagName("SFETR_INFO").item(0).getTextContent();
                    String SHTER_NM = element.getElementsByTagName("SHTER_NM").item(0).getTextContent();
                    String SHTER_TELNO = element.getElementsByTagName("SHTER_TELNO").item(0).getTextContent();
                    String PROTECT_PLC = element.getElementsByTagName("PROTECT_PLC").item(0).getTextContent();
                    String JURISD_INST_NM = element.getElementsByTagName("JURISD_INST_NM").item(0).getTextContent();
                    String CHRGPSN_NM = element.getElementsByTagName("CHRGPSN_NM").item(0).getTextContent();
                    String CHRGPSN_CONTCT_NO = element.getElementsByTagName("CHRGPSN_CONTCT_NO").item(0).getTextContent();
                    String PARTCLR_MATR = element.getElementsByTagName("PARTCLR_MATR").item(0).getTextContent();
                    String REFINE_LOTNO_ADDR = element.getElementsByTagName("REFINE_LOTNO_ADDR").item(0).getTextContent();
                    String REFINE_ROADNM_ADDR = element.getElementsByTagName("REFINE_ROADNM_ADDR").item(0).getTextContent();
                    String city = REFINE_ROADNM_ADDR.split(" ")[1];
                    String REFINE_ZIP_CD = element.getElementsByTagName("REFINE_ZIP_CD").item(0).getTextContent();
                    String REFINE_WGS84_LOGT = element.getElementsByTagName("REFINE_WGS84_LOGT").item(0).getTextContent();
                    String REFINE_WGS84_LAT = element.getElementsByTagName("REFINE_WGS84_LAT").item(0).getTextContent();

                    // dto에 값 넣기
                    LossDTO lossDTO = LossDTO.builder()
                            .SIGUN_CD(SIGUN_CD)
                            .SIGUN_NM(SIGUN_NM)
                            .ABDM_IDNTFY_NO(ABDM_IDNTFY_NO)
                            .THUMB_IMAGE_COURS(THUMB_IMAGE_COURS)
                            .RECEPT_DE(RECEPT_DE)
                            .DISCVRY_PLC_INFO(DISCVRY_PLC_INFO)
                            .SPECIES_NM(result)
                            .details(details)
                            .COLOR_NM(COLOR_NM)
                            .AGE_INFO(AGE_INFO)
                            .BDWGH_INFO(BDWGH_INFO)
                            .PBLANC_IDNTFY_NO(PBLANC_IDNTFY_NO)
                            .PBLANC_BEGIN_DE(PBLANC_BEGIN_DE)
                            .PBLANC_END_DE(PBLANC_END_DE)
                            .IMAGE_COURS(IMAGE_COURS)
                            .STATE_NM(STATE_NM)
                            .SEX_NM(sex)
                            .NEUT_YN(NEUT_YN)
                            .SFETR_INFO(SFETR_INFO)
                            .SHTER_NM(SHTER_NM)
                            .SHTER_TELNO(SHTER_TELNO)
                            .PROTECT_PLC(PROTECT_PLC)
                            .JURISD_INST_NM(JURISD_INST_NM)
                            .CHRGPSN_NM(CHRGPSN_NM)
                            .CHRGPSN_CONTCT_NO(CHRGPSN_CONTCT_NO)
                            .PARTCLR_MATR(PARTCLR_MATR)
                            .REFINE_LOTNO_ADDR(REFINE_LOTNO_ADDR)
                            .REFINE_ROADNM_ADDR(REFINE_ROADNM_ADDR)
                            .city(city)
                            .REFINE_ZIP_CD(REFINE_ZIP_CD)
                            .REFINE_WGS84_LOGT(REFINE_WGS84_LOGT)
                            .REFINE_WGS84_LAT(REFINE_WGS84_LAT)
                            .build();
                    lossDTOS.add(lossDTO);
                }
            }
            return lossDTOS;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 페이징 처리
    public ArrayList<LossDTO> parsenum(ArrayList<LossDTO> parses, int page) {
        ArrayList<LossDTO> parsepage = new ArrayList<>();
        Pagination pagination = new Pagination();
        /*시작 페이지 값을 가져온다*/
        /*int page */
        /*화면에 뿌릴 페이지 사이즈 가져오기 */
        pagination.setPageSize(12);
        int pagesize = pagination.getPageSize();

        // 끝 페이지
        int maxPage = page * pagesize;

        // 시작페이지
        int minPage = (maxPage - pagesize) + 1;  // maxPage - maxpage-pagesize   1000 -
        System.out.println(parses.size());
        // 전체 리스트의 사이즈의 갯수보다 maxPage가 크다면 maxPage를 parses.size()값을 줘서 값을 맞추는것임
        if (maxPage > parses.size()) {
            maxPage = parses.size();
            /*minPage = */
        }
        for (int i = minPage - 1; i < maxPage; i++) {
            parsepage.add(parses.get(i));
        }
        return parsepage;
    }

    // 상세페이지
    public ArrayList<LossDTO> getlossboard(String ABDM_IDNTFY_NO) {
        ArrayList<LossDTO> totLosslist = totlosslist();
        ArrayList<LossDTO> getlossDTOS = new ArrayList<>();
        for (int i = 0; i < totLosslist.size(); i++) {
            if (totLosslist.get(i).getABDM_IDNTFY_NO().equals(ABDM_IDNTFY_NO)) {
                getlossDTOS.add(totLosslist.get(i));
            }
        }
        return getlossDTOS;
    }


}