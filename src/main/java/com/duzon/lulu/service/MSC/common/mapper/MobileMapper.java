package com.duzon.lulu.service.MSC.common.mapper;

import com.duzon.lulu.service.MSC.common.model.Mobile.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MobileMapper {
    List<ExrmListModel> getExrmList(HashMap param);
    List<ExrmRsltListModel> getExrmRsltList(HashMap param);
}
