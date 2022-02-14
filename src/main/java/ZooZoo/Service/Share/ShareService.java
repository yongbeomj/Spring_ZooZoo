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
				ArrayList<String> address = new ArrayList<>();
				ArrayList<String> address2 = new ArrayList<>();
				ArrayList<String> addrX = new ArrayList<>();
				ArrayList<String> addrY = new ArrayList<>();
				ArrayList<String> addrpost = new ArrayList<>();
				ArrayList<String> oldaddress = new ArrayList<>();
				ArrayList<String> code = new ArrayList<>();
				ArrayList<String> agreedate = new ArrayList<>();
				ArrayList<String> tel = new ArrayList<>();
				ArrayList<String> name = new ArrayList<>();
				ArrayList<String> info = new ArrayList<>();
				if (area == null) {
					for (int i = 0; i < arr.size(); i++) {
						if (arr.get(i) != null) {
							JSONObject obj = (JSONObject) arr.get(i); // 오브젝트에 i번째 데이터 넣기
							JSONObject obj1 = (JSONObject) arr.get(i);
//                            System.out.println(obj);

							if (obj.get("REFINE_ROADNM_ADDR") == null) {
								address.add("정보없음");
							} else {
								address.add((String) obj.get("REFINE_ROADNM_ADDR"));
							}
							if (obj.get("ROADNM_ZIP_CD") == null) {
								addrpost.add("정보없음");
							} else {
								addrpost.add((String) obj.get("ROADNM_ZIP_CD"));
							}
							if (obj.get("LOCPLC_FACLT_TELNO") == null) {
								tel.add("정보없음");
							} else {
								tel.add((String) obj.get("LOCPLC_FACLT_TELNO"));
							}
							if (obj.get("BSN_STATE_DIV_CD") == null) {
								code.add("정보없음");
							} else {
								code.add((String) obj.get("BSN_STATE_DIV_CD"));
							}
							if (obj.get("LICENSG_DE") == null) {
								agreedate.add("정보없음");
							} else {
								agreedate.add((String) obj.get("LICENSG_DE"));
							}
							if (obj.get("REFINE_LOTNO_ADDR") == null) {
								oldaddress.add("정보없음");
							} else {
								oldaddress.add((String) obj.get("REFINE_LOTNO_ADDR"));
							}
							if (obj.get("REFINE_WGS84_LAT") == null) {
								addrX.add("정보없음");
							} else {
								addrX.add((String) obj.get("REFINE_WGS84_LAT"));
							}
							if (obj.get("REFINE_WGS84_LOGT") == null) {
								addrY.add("정보없음");
							} else {
								addrY.add((String) obj.get("REFINE_WGS84_LOGT"));
							}
							if (obj.get("BIZPLC_NM") == null) {
								name.add("정보없음");
							} else {
								name.add((String) obj.get("BIZPLC_NM"));
							}

							if (qq == 1) {
								info.add((i + 1) + ":" + address.get(i) + ":" + name.get(i) + ":" + addrX.get(i) + ":" + addrY.get(i) + ":" + addrpost.get(i) + ":" + code.get(i) + ":" + agreedate.get(i) + ":" + oldaddress.get(i) + ":" + tel.get(i));
//                            System.out.println(info.size());
							} else {
								info.add((total.size() + 1) + ":" + address.get(i) + ":" + name.get(i) + ":" + addrX.get(i) + ":" + addrY.get(i) + ":" + addrpost.get(i) + ":" + code.get(i) + ":" + agreedate.get(i) + ":" + oldaddress.get(i) + ":" + tel.get(i));
							}
						}
						total.add(info.get(i));
					}
				} else {
					int count = 0;
					for (int i = 0; i < arr.size(); i++) {
						JSONObject obj = (JSONObject) arr.get(i); // 오브젝트에 i번째 데이터 넣기
						JSONObject obj1 = (JSONObject) arr.get(i);

						if (obj.get("REFINE_ROADNM_ADDR") == null) {
							address.add("정보없음");
						} else {
							address.add((String) obj.get("REFINE_ROADNM_ADDR"));
						}
						if (obj.get("ROADNM_ZIP_CD") == null) {
							addrpost.add("정보없음");
						} else {
							addrpost.add((String) obj.get("ROADNM_ZIP_CD"));
						}
						if (obj.get("LOCPLC_FACLT_TELNO") == null) {
							tel.add("정보없음");
						} else {
							tel.add((String) obj.get("LOCPLC_FACLT_TELNO"));
						}
						if (obj.get("BSN_STATE_DIV_CD") == null) {
							code.add("정보없음");
						} else {
							code.add((String) obj.get("BSN_STATE_DIV_CD"));
						}
						if (obj.get("LICENSG_DE") == null) {
							agreedate.add("정보없음");
						} else {
							agreedate.add((String) obj.get("LICENSG_DE"));
						}
						if (obj.get("REFINE_LOTNO_ADDR") == null) {
							oldaddress.add("정보없음");
						} else {
							oldaddress.add((String) obj.get("REFINE_LOTNO_ADDR"));
						}
						if (obj.get("REFINE_WGS84_LAT") == null) {
							addrX.add("정보없음");
						} else {
							addrX.add((String) obj.get("REFINE_WGS84_LAT"));
						}
						if (obj.get("REFINE_WGS84_LOGT") == null) {
							addrY.add("정보없음");
						} else {
							addrY.add((String) obj.get("REFINE_WGS84_LOGT"));
						}
						if (obj.get("BIZPLC_NM") == null) {
							name.add("정보없음");
						} else {
							name.add((String) obj.get("BIZPLC_NM"));
						}

						if (address.get(i).equals("정보없음") && area == null) {
							address2.add(address.get(i));
							if (qq == 1) {
								info.add((i + 1) + ":" + address.get(i) + ":" + name.get(i) + ":" + addrX.get(i) + ":" + addrY.get(i) + ":" + addrpost.get(i) + ":" + code.get(i) + ":" + agreedate.get(i) + ":" + oldaddress.get(i) + ":" + tel.get(i));
							} else {
								info.add((total.size() + 1) + ":" + address.get(i) + ":" + name.get(i) + ":" + addrX.get(i) + ":" + addrY.get(i) + ":" + addrpost.get(i) + ":" + code.get(i) + ":" + agreedate.get(i) + ":" + oldaddress.get(i) + ":" + tel.get(i));
							}
							total.add(info.get(count - 1));
						}
						if (address.get(i).contains(" " + area + " ")) {
							address2.add(address.get(i));
							if (qq == 1 && address.size() == 0) {
								info.add((i + 1) + ":" + address.get(i) + ":" + name.get(i) + ":" + addrX.get(i) + ":" + addrY.get(i) + ":" + addrpost.get(i) + ":" + code.get(i) + ":" + agreedate.get(i) + ":" + oldaddress.get(i) + ":" + tel.get(i));
								count += 1;
							} else {
								info.add((total.size() + 1) + ":" + address.get(i) + ":" + name.get(i) + ":" + addrX.get(i) + ":" + addrY.get(i) + ":" + addrpost.get(i) + ":" + code.get(i) + ":" + agreedate.get(i) + ":" + oldaddress.get(i) + ":" + tel.get(i));
								count += 1;
							}
							total.add(info.get(count - 1));
						}
						if (area.contains("인천광역시") && address.get(i).contains("인천광역시 ")) {
							address2.add("인천광역시");
							if (qq == 1) {
								info.add((i + 1) + ":" + address.get(i) + ":" + name.get(i) + ":" + addrX.get(i) + ":" + addrY.get(i) + ":" + addrpost.get(i) + ":" + code.get(i) + ":" + agreedate.get(i) + ":" + oldaddress.get(i) + ":" + tel.get(i));
							} else {
								info.add((total.size() + 1) + ":" + address.get(i) + ":" + name.get(i) + ":" + addrX.get(i) + ":" + addrY.get(i) + ":" + addrpost.get(i) + ":" + code.get(i) + ":" + agreedate.get(i) + ":" + oldaddress.get(i) + ":" + tel.get(i));
							}
							total.add(info.get(count - 1));
						}
					}
				}
			}
			return total;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}