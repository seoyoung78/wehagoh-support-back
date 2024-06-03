package com.duzon.lulu.service.MSC.util.MSLC;

import lombok.Getter;

/**
 * mslc유틸에서 공통적으로 사용되는 RuntimeException 클래스.
 * 진단검사 통합검사결과에서 사용.
 * 
 * @author khgkjg12 강현구A
 * 
 */
@Getter
public class MSLCRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 3203716622230374192L;
	private MSLCError error;

	public MSLCRuntimeException(MSLCError error) {
		super(error.getMessage());
		this.error = error;
	}
}