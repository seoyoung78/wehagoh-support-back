package com.duzon.lulu.service.MSC.common.model.Common;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPrscListParam extends HealthSessionContext {
	private String rcpn_no;
}
