package com.duzon.lulu.service.MSC.common.model.Common;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchPatientParam extends HealthSessionContext {
	private String pid;// pid 일치(equals) 검색
	private String keyword;// pid, pt_nm에 대한 키워드 검색
	private Boolean isDobr; // 생년월일 검색 조건 여부
}
