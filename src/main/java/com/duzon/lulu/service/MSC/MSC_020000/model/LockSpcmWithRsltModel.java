package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

/**
 * lockFour 를 수행한 결과 모델.
 * 
 * 최종보고, 최종보고 취소에서 사용.
 * @author 강현구
 */
@Setter
@Getter
public class LockSpcmWithRsltModel extends ExmnRsltPkModel{
	private String rslt_prgr_stat_cd;//결과 진행 상태
}
