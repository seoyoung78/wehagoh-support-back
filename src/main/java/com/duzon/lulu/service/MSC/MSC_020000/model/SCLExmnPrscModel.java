package com.duzon.lulu.service.MSC.MSC_020000.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SCLExmnPrscModel {
	private String hbarcode; // SCL_검체번호 = spcm_no_1 (바코드번호(검체번호))
	private String chartno; // SCL_환자번호 = pid (등록번호)
	private String sex; // SCL_성별 = age_cd (성별/나이)
	private String pname; // SCL_환자명 = pt_nm (환자 이름(or등록번호))
	private String hitemcode; // SCL_병원검사코드 = prsc_cd (검사코드)
	private String hsampcode; // SCL_병원검체코드 = spcm_cd_1 (검체코드)
	private String hitemname; // SCL_검사명 = prsc_nm (검사명)
	private String hsampname; // SCL_검체명 = spcm_hnm (검체명)
	private String hgetdate; // SCL_채취일자 = cndt_dt (검사일자(A))
	private String drname; // SCL_진료의 = mdcr_user_nm (진료의)
	private String pt_frrn;
	private String pt_srrn;
}
