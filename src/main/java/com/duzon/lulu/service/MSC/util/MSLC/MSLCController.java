package com.duzon.lulu.service.MSC.util.MSLC;

import org.springframework.web.bind.annotation.ExceptionHandler;
import com.duzon.common.model.LuluResult;

/**
 * 진단검사-통합검사결과 예외처리 로직. MslcExcpetion,MslcRuntimeExcpetion 처리기능 제공 컨트롤러.
 * 
 * @author khgkjg12 강현구A
 */
public abstract class MSLCController {
	
	@ExceptionHandler({MSLCRuntimeException.class})
	protected LuluResult handleMslcException(MSLCRuntimeException e) {
		LuluResult luluResult = new LuluResult();
		e.getError().write(luluResult);
		return luluResult;
	}

	@ExceptionHandler(RuntimeException.class)
	protected LuluResult handleRuntimeException(RuntimeException e) {
		LuluResult luluResult = new LuluResult();
		e.printStackTrace();
		luluResult.setResultCode(500);
		return luluResult;
	}
}
