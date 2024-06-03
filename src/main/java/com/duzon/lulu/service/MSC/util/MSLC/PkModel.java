package com.duzon.lulu.service.MSC.util.MSLC;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * @author khgkjg12 강현구A PkModel들의 추상화클래스.
 */
@Setter
@Getter
public abstract class PkModel {

	public static <E extends PkModel> PkModel getInstance(Class<E> clazz, Map<String, Object> param)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		E pkModel;
		pkModel = clazz.getDeclaredConstructor().newInstance();
		pkModel.set(param);
		return pkModel;
	}

	public static <E extends PkModel> PkModel getInstance(Class<E> clazz, Object... param)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		E pkModel;
		pkModel = clazz.getDeclaredConstructor().newInstance();
		pkModel.set(param);
		return pkModel;
	}

	/**
	 * @author khgkjg12 강현구A
	 * 
	 *         맵인자를 통해 모델 맴버 변수 세팅하게 해주는 메소드.
	 * @param param 각 키-값 형식까지 모두 검사를 마쳤음을 가정한다.
	 */
	public abstract void set(Map<String, Object> param);

	/**
	 * 컬럼 순서대로 값을 입력해주는 메소드.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 19, 2024
	 * @param param
	 */
	public abstract void set(Object... param);

	public abstract String rtrvModelName();
}