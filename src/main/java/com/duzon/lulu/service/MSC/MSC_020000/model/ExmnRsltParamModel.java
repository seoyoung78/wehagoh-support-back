package com.duzon.lulu.service.MSC.MSC_020000.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExmnRsltParamModel {
	String pid;
	String rcpn_no;
	String spcm_no;
	private List<LockSpcmWithPrscModel> prscInfoList;
}
