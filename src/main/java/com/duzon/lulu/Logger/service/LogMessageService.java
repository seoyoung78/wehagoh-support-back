package com.duzon.lulu.Logger.service;

import com.duzon.common.model.LogModel;
import com.duzon.common.service.LuluService;

/**
 * CommonCdoe Service
 * @author 이용상
 * @since 2017.08.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *
 *
 * </pre>
 */
public interface LogMessageService extends LuluService {
	
	void insertLog(LogModel logModel);
	void insertTestLog(LogModel logModel);
}
