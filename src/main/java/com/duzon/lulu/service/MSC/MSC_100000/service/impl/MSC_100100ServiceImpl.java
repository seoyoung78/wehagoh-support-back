package com.duzon.lulu.service.MSC.MSC_100000.service.impl;

import com.duzon.lulu.service.MSC.MSC_100000.mapper.MSC_100100Mapper;
import com.duzon.lulu.service.MSC.MSC_100000.model.*;
import com.duzon.lulu.service.MSC.MSC_100000.service.IMSC_100100Service;
import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MSC_100100ServiceImpl implements IMSC_100100Service {
    @Autowired
    private MSC_100100Mapper msc100100Mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HealthSessionContext healthSessionContext;

    private Logger logger = LoggerFactory.getLogger(MSC_100100ServiceImpl.class);

    @Override
    public List<MSC_100100ExmnDvcdModel> getExmnDvcd() {
        Map<String, Object> param = new HashMap<>();
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return msc100100Mapper.getExmnDvcd(param);
    }

    @Override
    public List<MSC_100100OpnnModel> getOpnnList() {
        Map<String, Object> param = new HashMap<>();
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return msc100100Mapper.getOpnnList(param);
    }

    @Override
    public List<MSC_100100OpnnModel> searchOpnn(MSC_100100Request.SearchOpnn param) {
        Map<String, Object> hashMap = objectMapper.convertValue(param, HashMap.class);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(hashMap);
        return msc100100Mapper.searchOpnn(hashMap);
    }

    @Override
    public MSC_100100OpnnModel getOpnn(long exmn_opnn_sqno) {
        Map<String, Object> param = new HashMap<>();
        param.put("exmn_opnn_sqno", exmn_opnn_sqno);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return msc100100Mapper.getOpnn(param);
    }

    @Override
    public MSC_100100Response.GetEnds getEnds(long exmn_opnn_sqno) {
        Map<String, Object> param = new HashMap<>();
        param.put("exmn_opnn_sqno", exmn_opnn_sqno);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return MSC_100100Response.GetEnds.fromMSC100100EndsModelList(msc100100Mapper.getEnds(param));
    }

    @Override
    public long saveExmnOpnn(MSC_100100OpnnModel param) {
        Map<String, Object> paramMap = objectMapper.convertValue(param, Map.class);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(paramMap);
        msc100100Mapper.saveExmnOpnn(paramMap);
        return (long) paramMap.get("exmn_opnn_sqno");
    }

    @Override
    @Transactional
    public long saveEndsOpnn(MSC_100100ExmnAndEndsDetlList param) {
        Map<String, Object> paramMap = objectMapper.convertValue(param, Map.class);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(paramMap);
        msc100100Mapper.saveExmnOpnn(paramMap);
        if (param.getEnds_detl_list() != null && param.getEnds_detl_list().size() > 0) {
            msc100100Mapper.saveEndsOpnn(paramMap);
        }
        return (long) paramMap.get("exmn_opnn_sqno");
    }

    @Override
    public int editExmnOpnn(MSC_100100OpnnModel param) {
        Map<String, Object> paramMap = objectMapper.convertValue(param, Map.class);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(paramMap);
        return msc100100Mapper.editExmnOpnn(paramMap);
    }


    @Override
    @Transactional
    public int editEndsOpnn(MSC_100100ExmnAndEndsDetlList param) {
        Map<String, Object> paramMap = objectMapper.convertValue(param, Map.class);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(paramMap);

        // 1. 검사소견 마스터를 수정한다.(title 등)
        msc100100Mapper.editExmnOpnn(paramMap);

        // 2. exmn_opnn_sqno 가 같은, 기존 내시경 데이터를 모두 가져온다.
        List<MSC_100100Response.GetEnds.EndsDetl> ori = getEnds(param.getExmn_opnn_sqno())
                .getEnds_detl_list().stream()
                .filter(item -> item.getExmn_opnn_detl_sqno() > 0)
                .collect(Collectors.toList());

        // 3. 기존 데이터에는 있지만 param 에는 없는 값을 삭제처리한다.
        List<MSC_100100Response.GetEnds.EndsDetl> deleteList = ori
                .stream()
                .filter(item -> !param.getEnds_detl_list()
                        .stream()
                        .anyMatch(paramItem -> paramItem.getExmn_opnn_detl_sqno() == item.getExmn_opnn_detl_sqno()))
                .collect(Collectors.toList());
        if (deleteList.size() > 0) {
            paramMap.put("ends_detl_list", deleteList);
            msc100100Mapper.deleteEndsOpnnByDetlSqno(paramMap);
        }
        // 4. 기존 데이터에도 있고 param 에도 있는 값을 update 한다.
        List<MSC_100100EndsDetlModel> udpateList = param.getEnds_detl_list().stream()
                .filter(item -> ori.stream()
                        .anyMatch(oriItem -> oriItem.getExmn_opnn_detl_sqno() == item.getExmn_opnn_detl_sqno()))
                .collect(Collectors.toList());
        if (udpateList.size() > 0) {
            paramMap.put("ends_detl_list", udpateList);
            msc100100Mapper.editEndsOpnn(paramMap);
        }
        // 5. 기존 데이터에 없는 param 값을 insert 한다.
        List<MSC_100100EndsDetlModel> insertList = param.getEnds_detl_list()
                .stream()
                .filter(item -> !ori
                        .stream()
                        .anyMatch(oriItem -> oriItem.getExmn_opnn_detl_sqno() == item.getExmn_opnn_detl_sqno()))
                .collect(Collectors.toList());
        if (insertList.size() > 0) {
            paramMap.put("ends_detl_list", insertList);
            msc100100Mapper.saveEndsOpnn(paramMap);
        }
        return 1;
    }

    @Override
    public int deleteExmnOpnn(long exmn_opnn_sqno) {
        Map<String, Object> param = new HashMap<>();
        param.put("exmn_opnn_sqno", exmn_opnn_sqno);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        return msc100100Mapper.deleteExmnOpnn(param);
    }

    @Override
    @Transactional
    public int deleteEndsOpnn(long exmn_opnn_sqno) {
        Map<String, Object> param = new HashMap<>();
        param.put("exmn_opnn_sqno", exmn_opnn_sqno);
        healthSessionContext.setContext();
        healthSessionContext.setSessionForHashMap(param);
        msc100100Mapper.deleteExmnOpnn(param);
        return msc100100Mapper.deleteEndsOpnn(param);
    }
}
