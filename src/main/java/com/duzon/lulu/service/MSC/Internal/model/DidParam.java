package com.duzon.lulu.service.MSC.Internal.model;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DidParam extends HealthSessionContext {
    List<Integer> exrm_dept_sqno_list;
}
