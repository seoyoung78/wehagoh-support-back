package com.duzon.lulu.service.MSC.MSC_080000.mapper;

import com.duzon.lulu.service.MSC.MSC_080000.model.MSC_080100ResModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MSC_080100Mapper {
    List<MSC_080100ResModel> getExmPatient(Map<String, Object> param);
}
