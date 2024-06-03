package com.duzon.lulu.service.MSC.MSC_010000.mapper;

import java.util.*;

import com.duzon.lulu.service.MSC.MSC_010000.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MSC_010100Mapper {
    List<MSC_010100ResModel> selectPatient(Map<String, Object> model);
}
