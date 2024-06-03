package com.duzon.lulu.service.MSC.ExternalInterface.Saerom.mapper;

import com.duzon.lulu.service.MSC.ExternalInterface.Saerom.model.CreatinineCodeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface MSC_CalcMapper {

    CreatinineCodeInfo getExamCodeInfo(HashMap param);
}
