package ZooZoo.Controller.Hospital;


import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDto {
    private String bizplcnm; // 병원 이름
    private String refineroadnmaddr; // 주소
    private String bsnstatenm; // 영업 상태명
    private String locplcfaclttelno; // 전화번호
    private String logt; // 위도
    private String lat; // 경도
    private String sigunnm; // 시 정보
    private String siguncd; // 시 정보코드
}
