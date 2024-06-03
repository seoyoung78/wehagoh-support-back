package com.duzon.lulu.service.MSC.Internal.mapper;

import com.duzon.lulu.service.MSC.Internal.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface InternalMapper {
    Patient selectRcpnPatient(HashMap<String, Integer> param);
    List<Patient> selectWaitPatientList(DidParam param);
}
