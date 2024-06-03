package com.duzon.lulu.service.MSC.common.mapper;

import com.duzon.lulu.service.MSC.common.model.Exam.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface ExamMapper {

    // 검사 접수 목록 조회
    List<Exam> selectReceptionList(HashMap<String, Object> param);

    // 검사 결과 목록 조회
    List<Exam> selectResultList(HashMap<String, Object> param);

    // 검사 처방 목록
    List<Details> selectPrscList(HashMap<String, Object> param);

    // 처방진행 상태 변경
    // 신규 검사(접수 + 검사완료)
    Integer updateExmn(HashMap<String, Object> param);
    // 신규 검사 취소(검사취소 + 접수취소)
    Integer updateExmnCancel(HashMap<String, Object> param);

    // 접수
    Integer updateReceipt(HashMap<String, Object> param);
    // 접수 취소
    Integer updateReceiptCancel(HashMap<String, Object> param);
    // 검사 완료
    Integer updateConduct(HashMap<String, Object> param);
    // 검사 취소
    Integer updateConductCancel(HashMap<String, Object> param);
    // 저장 : 판독중
    Integer updateSave(HashMap<String, Object> param);
    // 판독중 취소
    Integer saveCancel(HashMap<String, Object> param);
    // 최종 판독
    Integer updateInterpret(HashMap<String, Object> param);
    // 최종 판독 취소
    Integer updateInterpretCancel(HashMap<String, Object> param);
    // 최종 보고
    Integer updateReport(HashMap<String, Object> param);
    // 최종보고 취소
    Integer updateReportCancel(HashMap<String, Object> param);
    // 검사 취소 상태 확인
    String selectExamCheck(HashMap<String, Object> param);

    // 최종보고/판독 완료 취소 시 통합결과 삭제
    Integer deleteResult(HashMap<String, Object> param);

    // DC 요청 업데이트
    Integer updateDcRqstY(HashMap<String, Object> param);

    // 동의여부 업데이트
    Integer updateWrcnWrtnY(HashMap<String, Object> param);
    // 서식명칭 조회
    String selectErmNm(HashMap<String, Object> param);
}
