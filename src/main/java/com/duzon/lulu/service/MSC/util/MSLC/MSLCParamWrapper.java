package com.duzon.lulu.service.MSC.util.MSLC;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * MSC_020000용 cno를 비롯한 위하고 파라미터들이 요청 바디에 안정적으로 삽입될수있게+충돌방지를 위해 감싸는 요청 파라미터 모델.
 * 
 * 진단검사-통합검사결과에서 사용.
 * 
 * @author khgkjg12 강현구A
 * 
 */
@Data
public class MSLCParamWrapper {

	private Map<String, Object> mslcMap;
	private List<? extends Map<String, Object>> mslcMapList;
	private List<String> mslcStringList;
	private List<Long> mslcLongList;
	private String mslcString;
	private long mslcLong;
	private int mslcInt;
}
