package com.duzon.lulu.service.MSC.util.MSLC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;

/**
 * 진단검사/통합검사결과 세션 삽입로직.
 * 
 * @author khgkjg12 강현구A
 */
@Component
public class MSLCSessionHelper {

	@Autowired
	private HealthSessionContext healthSessionContext;

	/**
	 * 여러 맵, 모델 객체에 세션 정보를 한번에 삽입. setContext까지 한번에 수행.
	 * 
	 * @author khgkjg12 강현구A
	 * @param objects 세션을 삽입할 HealthSessionContext 상속 객체 혹은 Map<String, Object> 객체
	 *                배열.
	 */
	public void setSessionForAll(Object... objects) {
		healthSessionContext.setContext();
		setPreparedSessionForAll(objects);
	}

	/**
	 * 여러 맵, 모델 객체에 세션 정보를 한번에 삽입. setContext가 완료된 HealthSessionContext 필요.
	 * 
	 * @author khgkjg12 강현구A
	 * @param objects 세션을 삽입할 HealthSessionContext 상속 객체 혹은 Map<String, Object> 객체
	 *                배열.
	 */
	public void setPreparedSessionForAll(Object... objects) {
		for (Object object : objects) {
			if (object instanceof Collection) {
				((Collection<?>) object).forEach((each) -> {
					if (each instanceof Map) {
						healthSessionContext.setSessionForHashMap((Map<?, ?>) each);
					} else {
						healthSessionContext.setSessionForModel(each);
					}
				});
			} else if (object instanceof Map) {
				healthSessionContext.setSessionForHashMap((Map<?, ?>) object);
			} else {
				healthSessionContext.setSessionForModel(object);
			}
		}
	}

	public HealthSessionContext getSession() {
		return healthSessionContext;
	}

	public void setContext() {
		healthSessionContext.setContext();
	}

	public MSLCParamModel getParamModel(Object... param) {
		return new MSLCParamModel(healthSessionContext, param);
	}

	public ArrayList<MSLCParamModel> getBatchParamModel(Collection<?> param) {
		ArrayList<MSLCParamModel> mslcParamList = new ArrayList<>();
		param.forEach(each -> {
			mslcParamList.add(new MSLCParamModel(healthSessionContext, new Object[] { each }));
		});
		return mslcParamList;
	}

	/**
	 * setContext별도 호출 필요.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Feb 22, 2024
	 * @param model
	 * @return
	 */
	public HashMap<String, Object> getSessionMap(Object param) {
		HashMap<String, Object> result = new HashMap<>();
		HashMap<String, Object> session = new HashMap<>();
		healthSessionContext.setSessionForHashMap(session);
		result.put("session", session);
		result.put("param", param);
		return result;
	}

	/**
	 * setContext별도 호출 필요.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Feb 22, 2024
	 * @param model
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getSessionListMap(Collection<?> param) {
		ArrayList<HashMap<String, Object>> resultList = new ArrayList<>();
		param.forEach(each -> {
			HashMap<String, Object> result = new HashMap<>();
			HashMap<String, Object> session = new HashMap<>();
			healthSessionContext.setSessionForHashMap(session);
			result.put("session", session);
			result.put("param", param);
			resultList.add(result);
		});
		return resultList;
	}
}
