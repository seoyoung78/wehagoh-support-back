package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

/**
 * LockSpcm 결과 모델
 * 
 * 접수취소에서 사용.
 * @author hyungu 강현구
 */
@Getter
@Setter
public class LockSpcmModel extends SpcmPkModel{
	private String prsc_prgr_stat_cd;
}
