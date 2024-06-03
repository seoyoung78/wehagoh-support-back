package com.duzon.lulu.service.MSC.ExternalInterface.Irm.service.impl;

import com.duzon.common.model.LuluResult;
import com.duzon.common.util.ValidationUtil;
import com.duzon.lulu.service.MSC.ExternalInterface.Irm.model.Order;
import com.duzon.lulu.service.MSC.ExternalInterface.Irm.mapper.IrmMapper;
import com.duzon.lulu.service.MSC.common.service.IExamService;
import com.duzon.lulu.service.MSC.ExternalInterface.Irm.service.IIrmService;
import org.apache.commons.codec.DecoderException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class IrmServiceImpl implements IIrmService {
    @Autowired
    IrmMapper irmMapper;
    @Autowired
    IExamService iExamService;

    /**
     * IRM 알림 업데이트
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Object updateSave(Order param) throws Exception {
        int result = irmMapper.updateSave(param);
        if (result > 0) {
            irmMapper.updateConduct(param);
            if (param.getPrsc_prgr_stat_cd().equals("E")) {
                param.setHstr_stat_cd("1");
            } else {
                param.setHstr_stat_cd("2");
            }
            return irmMapper.insertHistory(param);
        } else {
            return 0;
        }
    }

    /**
     * IRM 리포트 업데이트(판독중, 최종판독)
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Object updateInterpret(Order param) throws Exception {
        int result = irmMapper.updateInterpret(param);
        if (result > 0) {
            irmMapper.updateConduct(param);
            param.setExmnRsltSqno(irmMapper.selectExmnRsltSqno(param));
            param.setHstr_stat_cd("2");

            if (param.getStat_cd().equals("O") && param.getPrsc_prgr_stat_cd().equals("M")) {
                if (param.getExmnRsltSqno() != null) {
                    irmMapper.deleteResult(param);
                }
            }
            // 최종보고
            else if (param.getPrsc_prgr_stat_cd().equals("O")) {
                irmMapper.insertResult(param);
            }

            return irmMapper.insertHistory(param);
        } else {
            return 0;
        }
    }

    /**
     * IRM IAN(영상촬영알림) 연동
     * @param param
     * @return LuluResult
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LuluResult sendIan(HashMap<String, Object> param) {
        // 필수 파라미터 체크
        String[] requirementKeys = { "cno", "organization_id", "accession_no", "study_dttm", "pid" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if (result.getResultCode() != 200) return result;

        try {
            Order order = new Order();
            order.setCno(param.get("cno").toString());
            order.setAccession_no(param.get("accession_no").toString());
            order.setStudy_dttm(convertTime(param.get("study_dttm")));
            order.setPid(param.get("pid").toString());
            order.setAuthorization(param.get("authorization").toString());

            result.setResultCode(200);
            result.setResultData(updateSave(order));

            sendPacsNoti(order);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            result.setResultCode(400);
            result.setResultMsg("study_dttm UTC 형식에 맞지 않습니다.");
        } catch (MyBatisSystemException e) {
            e.printStackTrace();
            result.setResultCode(400);
            result.setResultMsg("올바른 accession_no 값이 아닙니다.");
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("알림톡")) {
                result.setResultCode(400);
                result.setResultMsg("알림톡 전송 오류");
            } else {
                result.setResultCode(401);
                result.setResultMsg("인증 오류");
            }
        }

        return result;
    }

    /**
     * IRM Report 연동
     * @param param
     * @return LuluResult
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LuluResult saveReport(HashMap<String, Object> param) {
        // 필수 파라미터 체크
        String[] requirementKeys = { "cno", "organization_id", "accession_no", "prsc_prgr_stat_cd", "author_name", "document_dttm", "pid" };
        LuluResult result = ValidationUtil.checkContainsKeysValue(requirementKeys, param);
        if (result.getResultCode() != 200) return result;

        try {
            Order order = new Order();
            order.setCno(param.get("cno").toString());
            order.setAccession_no(param.get("accession_no").toString());
            order.setPrsc_prgr_stat_cd(param.get("prsc_prgr_stat_cd").toString());
            order.setAuthorization(param.get("authorization").toString());
            order.setDocument_dttm(convertTime(param.get("document_dttm")));
            order.setPid(param.get("pid").toString());

            if (param.get("author_name") != null) {
                byte[] decodedBytes = Base64.getDecoder().decode(param.get("author_name").toString());
                order.setAuthor_name(new String(decodedBytes));
            }
            if (param.get("finding") != null) {
                byte[] decodedBytes = Base64.getDecoder().decode(param.get("finding").toString());
                order.setFinding(new String(decodedBytes));
            }
            if (param.get("dicom_sr_list") != null) {
                order.setDicom_sr_list(param.get("dicom_sr_list").toString());
            }

            switch (order.getPrsc_prgr_stat_cd()) {
                case "M":
                    order.setType("Save");
                    result.setResultData(updateInterpret(order));
                    break;
                case "O":
                    order.setType("Interpret");
                    result.setResultData(updateInterpret(order));
                    break;
                default:
                    result.setResultCode(400);
                    result.setResultMsg("필수 파라미터 prsc_prgr_stat_cd 값이 'M(임시저장)' 또는 'O(최종판독)' 값이여야 합니다.");
                    return result;
            }

            sendPacsNoti(order);
        } catch (DecoderException e) {
            e.printStackTrace();
            result.setResultCode(400);
            result.setResultMsg("Base64 디코딩 할 수 없는 문자열이 포함되어 있습니다.");
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            result.setResultCode(400);
            result.setResultMsg("document_dttm UTC 형식에 맞지 않습니다.");
        } catch (MyBatisSystemException e) {
            e.printStackTrace();
            result.setResultCode(400);
            result.setResultMsg("올바른 accession_no 값이 아닙니다.");
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("알림톡")) {
                result.setResultCode(400);
                result.setResultMsg("알림톡 전송 오류");
            } else {
                result.setResultCode(401);
                result.setResultMsg("인증 오류");
            }
        }
        return result;
    }

    /**
     * timezone UTC -> KST로 변환
     * @param time
     * @return String
     */
    private String convertTime(Object time) {
        // UTC 시간을 OffsetDateTime으로 파싱
        OffsetDateTime utcDateTime = OffsetDateTime.parse(time.toString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        // KST 시간대로 변환
        LocalDateTime kstDateTime = utcDateTime.atZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        // 변환된 KST 시간을 문자열로 변환
        String kstString = kstDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        return kstString;
    }

    // PACS 영상 알림
    @Transactional(propagation = Propagation.NESTED)
    private LuluResult sendPacsNoti(Order param) throws Exception {
        HashMap<String, Object> notiData = irmMapper.selectNotiData(param);
        notiData.put("authorization", param.getAuthorization());
        notiData.put("exrmClsfCd", "R");
        notiData.put("reqCompany", "IRM");
        notiData.put("reqUser", "IRM");
        notiData.put("cno", param.getCno());

        // IAN 영상 알림
        if (param.getType() == null) {
            // 최종보고 취소 알림 (최종판독 후 IAN 영상 알림 시)
            if (param.getPrsc_prgr_stat_cd().equals("O")) {
                notiData.put("type", "InterpretCancel");
                iExamService.sendNoti(notiData);
            }
            notiData.put("type", "PacsAlert");
            return iExamService.sendNoti(notiData);
        } else {
            notiData.put("reqUser", param.getAuthor_name());
            // 최종보고 취소
            if (param.getStat_cd().equals("O") && param.getPrsc_prgr_stat_cd().equals("M")) {
                notiData.put("type", "InterpretCancel");
               return iExamService.sendNoti(notiData);
            }
            // 최종보고
            notiData.put("type", "Interpret");
            return iExamService.sendNoti(notiData);
        }
    }

}
