package com.duzon.lulu.service.MSC.util.MSLC;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MSLCParamModel {
	private HealthSessionContext session;
	private Object[] param;
}
