
package com.duzon.lulu.service.MSC.util.MSLC;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import org.springframework.util.ObjectUtils;

import com.duzon.common.model.LuluResult;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

/**
 * 진단검사-통합검사결사 모듈 유효성 검사기
 * 
 * @author khgkjg12 강현구A
 */
public class MSLCUtil {

	/**
	 * 맵 인자 필수 키 검사.(null허용)
	 * 
	 * @author khgkjg12 강현구A
	 * @param param        검사대상 키-값.
	 * @param requiredKeys 필수키 배열.
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 401 : {@link MSLCError#NO_EXTC_KEY}<i>["key1", "key2",
	 *         "key3",...]</i></li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         </ul>
	 */
	public static LuluResult chckExtc(Map<String, Object> param, String... requiredKeys) {
		if (ObjectUtils.isEmpty(param))
			return MSLCError.EMPTY.createLuluResult();
		ArrayList<String> noList = new ArrayList<>();
		Set<String> keySet = param.keySet();
		for (String requiredKey : requiredKeys) {
			if (!keySet.contains(requiredKey))
				noList.add(requiredKey);
		}
		if (noList.size() > 0) {
			return MSLCError.NO_EXTC_KEY.withKeys(noList).createLuluResult();
		}
		return new LuluResult();
	}

	/**
	 * 맵 목록 인자 필수키 검사.
	 * 
	 * @author khgkjg12 강현구A
	 * @param param        검사대상 키-값 맵 목록
	 * @param requiredKeys 필수키 배열.
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 401 : {@link MSLCError#NO_EXTC_KEY}<i>["key1", "key2",
	 *         "key3",...]</i></li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         </ul>
	 */
	public static LuluResult chckExtcList(List<? extends Map<String, Object>> param, String... requiredKeys) {
		if (ObjectUtils.isEmpty(param))
			return MSLCError.EMPTY.createLuluResult();
		for (Map<String, Object> eachParam : param) {
			LuluResult luluResult = chckExtc(eachParam, requiredKeys);
			if (luluResult.getResultCode() != 200)
				return luluResult;
		}
		return new LuluResult();
	}

	/**
	 * 맵 인자 빈값 검사.
	 * 
	 * @author khgkjg12 강현구A
	 * @param nonEmptyKeys 검사대상 키 배열.
	 * @param param        검사 대상 키-값.
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 402 : {@link MSLCError#EMPTY_VALU} <i>["key1", "key2",
	 *         "key3",...]</i></li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         </ul>
	 */
	public static LuluResult chckEmpty(Map<String, Object> param, String... nonEmptyKeys) {
		if (ObjectUtils.isEmpty(param))
			return MSLCError.EMPTY.createLuluResult();
		ArrayList<String> emptyKeys = new ArrayList<>();
		for (String key : nonEmptyKeys) {
			if (ObjectUtils.isEmpty(param.get(key)))
				emptyKeys.add(key);
		}
		if (emptyKeys.size() > 0) {
			return MSLCError.EMPTY_VALU.withKeys(emptyKeys).createLuluResult();
		}
		return new LuluResult();
	}

	/**
	 * 맵 목록 인자 빈값 검사.
	 * 
	 * @author khgkjg12 강현구A
	 * @param nonEmptyKeys 검사대상 키 배열.
	 * @param param        검사 대상 키-값.
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 402 : {@link MSLCError#EMPTY_VALU} <i>["key1", "key2",
	 *         "key3",...]</i></li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         </ul>
	 */
	public static LuluResult chckEmptyList(List<? extends Map<String, Object>> param, String... nonEmptyKeys) {
		if (ObjectUtils.isEmpty(param))
			return MSLCError.EMPTY.createLuluResult();
		for (Map<String, Object> eachParam : param) {
			LuluResult luluResult = chckEmpty(eachParam, nonEmptyKeys);
			if (luluResult.getResultCode() != 200)
				return luluResult;
		}
		return new LuluResult();
	}

	/**
	 * 인자 빈값 검사.
	 * 
	 * @author khgkjg12 강현구A
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         </ul>
	 */
	public static LuluResult chckEmpty(Object values) {
		if (ObjectUtils.isEmpty(values))
			return MSLCError.EMPTY.createLuluResult();
		return new LuluResult();
	}

	/**
	 * 목록 인자 빈값 검사.
	 * 
	 * @author khgkjg12 강현구A
	 * 
	 * @param values 검사 대상 목록
	 * @return 
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 402 : {@link MSLCError#EMPTY_VALU} <i>["idx1", "idx2", "idx3", ... ]</i></li>
	 *         <li>[CODE] 408 : {@link MSLCError#EMPTY}</li>
	 *         </ul>
	 */
	public static LuluResult chckEmpty(List<?> values) {
		if (ObjectUtils.isEmpty(values))
			return MSLCError.EMPTY.createLuluResult();
		ArrayList<Integer> emptyIdxs = new ArrayList<>();
		for (int idx = 0; idx < values.size(); idx++) {
			if (ObjectUtils.isEmpty(values.get(idx)))
				emptyIdxs.add(idx);
		}
		if (emptyIdxs.size() > 0) {
			return MSLCError.EMPTY_VALU.withIdxs(emptyIdxs).createLuluResult();
		}
		return new LuluResult();
	}

	/**
	 * 맵인자 정수(int)형 값 검사. 문자열도 int로 형변환 시켜줌.
	 * 
	 * <b>빈 값 validation 선행 필수</b>
	 * 
	 * @author khgkjg12 강현구A
	 * 
	 * @param param      검사 대상 맵.
	 * @param targetKeys 검사 대상 키 배열.
	 * @return 
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 405 : {@link MSLCError#NO_INT_VALU} <i>["key1", "key2",
	 *         "key3",...]</i></li>
	 *         </ul>
	 */
	public static LuluResult chckInt(Map<String, Object> param, String... targetKeys) {
		ArrayList<String> errorKeys = new ArrayList<>();
		for (String key : targetKeys) {
			if (param.get(key) instanceof Integer)
				continue;
			try {
				param.put(key, Integer.parseInt(param.get(key).toString()));
				continue;
			} catch (NumberFormatException e) {
			}
			errorKeys.add(key);
		}
		if (errorKeys.size() > 0) {
			return MSLCError.NO_INT_VALU.withKeys(errorKeys).createLuluResult();
		}
		return new LuluResult();
	}

	/**
	 * 맵 목록 인자 정수(int)형 값 검사.
	 * 
	 * <b>빈 값 validation 선행 필수</b>
	 * 
	 * @author khgkjg12 강현구A
	 * 
	 * @param param      검사 대상 맵 목록.
	 * @param targetKeys 검사 대상 키 배열.
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 405 : {@link MSLCError#NO_INT_VALU} <i>["key1", "key2",
	 *         "key3",...]</i></li>
	 *         </ul>
	 */
	public static LuluResult chckIntList(List<? extends Map<String, Object>> param, String... nonEmptyKeys) {
		for (Map<String, Object> eachParam : param) {
			LuluResult luluResult = chckInt(eachParam, nonEmptyKeys);
			if (luluResult.getResultCode() != 200) {
				return luluResult;
			}
		}
		return new LuluResult();
	}

	/**
	 * 맵인자 정수(long)형 값 검사. int, 문자열 자료형일시 형변환 해서 저장함.
	 * 
	 * <b>빈 값 validation 선행 필수</b>
	 * 
	 * @author khgkjg12 강현구A
	 * 
	 * 
	 * @param param      검사 대상 맵.
	 * @param targetKeys 검사 대상 키 배열.
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 406 : {@link MSLCError#NO_LONG_VALU} <i>["key1", "key2",
	 *         "key3",...]</i></li>
	 *         </ul>
	 */
	public static LuluResult chckLong(Map<String, Object> param, String... targetKeys) {
		ArrayList<String> errorKeys = new ArrayList<>();
		for (String key : targetKeys) {
			Object value = param.get(key);
			if (value instanceof Long) {
				continue;
			}
			if (value instanceof Integer) {
				param.put(key, new Long((int) value));
				continue;
			}
			try {
				param.put(key, Long.parseLong(param.get(key).toString()));
				continue;
			} catch (NumberFormatException e) {
			}
			errorKeys.add(key);
		}
		if (errorKeys.size() > 0) {
			return MSLCError.NO_LONG_VALU.withKeys(errorKeys).createLuluResult();
		}
		return new LuluResult();
	}

	/**
	 * 맵 목록인자 정수(long)형 값 검사. int 형일시 형변환 해서 저장함.
	 * 
	 * <b>빈 값 validation 선행 필수</b>
	 * @author khgkjg12 강현구A
	 * 
	 * @param param      검사 대상 맵 목록.
	 * @param targetKeys 검사 대상 키 배열.
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200</li>
	 *         <li>[CODE] 406 : {@link MSLCError#NO_LONG_VALU} <i>["key1", "key2",
	 *         "key3",...]</i></li>
	 *         </ul>
	 */
	public static LuluResult chckLongList(List<? extends Map<String, Object>> param, String... targetKeys) {
		for (Map<String, Object> eachParam : param) {
			LuluResult luluResult = chckLong(eachParam, targetKeys);
			if (luluResult.getResultCode() != 200) {
				return luluResult;
			}
		}
		return new LuluResult();
	}

	/**
	 * 오브젝트를 문자열로 변환 빈값/널은 널로 반환.
	 * 
	 * @author khgkjg12 강현구A
	 * @param obj nullable
	 */
	public static String parseString(Object obj) {
		if (obj == null)
			return null;
		return obj.toString();			
	}

	public static Integer parseInt(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Integer) {
			return ((int) obj);
		}
		try {
			return Integer.parseInt(parseString(obj));
		} catch (NumberFormatException e) {
			throw new MSLCRuntimeException(MSLCError.NO_LONG_VALU);
		}
	}

	public static Long parseLong(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Integer) {
			return (long) ((int) obj);
		}
		if (obj instanceof Long) {
			return (long) obj;
		}
		try {
			return Long.parseLong(parseString(obj));
		} catch (NumberFormatException e) {
			throw new MSLCRuntimeException(MSLCError.NO_LONG_VALU);
		}
	}

	/**
	 * NULL을 포함한 두 문자열을 비교해서 옵션에따라 큰 문자열 혹은 작은 문자열을 반환.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Feb 22, 2024
	 * @param str1
	 * @param str2
	 * @param getMax
	 * @return
	 */
	public static String compareString(String str1, String str2, boolean getMax) {
		if (str1 == null || (str2 != null && str1.compareTo(str2) < 0))
			return getMax ? str2 : str1;
		return getMax ? str1 : str2;
	}

	public static final String DASH_DATE = "yyyy-MM-dd";
	public static final String NARROW_DATE = "yyyyMMdd";
	public static final String DASH_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 현재 날짜를 YYYY-MM-DD형식으로 반환.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Feb 22, 2024
	 * @return
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}

	/**
	 * 필드값 배열 인자를 사용, 중복 없는 모델 생성.
	 * 
	 * <b>단일 식별자를 가진 모델에 대해서만 사용 가능.</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Feb 26, 2024
	 * @param <E>
	 * @param param
	 * @param clazz
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 파라미터로 생성한 모델 집합 HashSet&lt;PkModel&gt;.</li>
	 *         <li>[CODE] 403 : {@link MSLCError#RDDC_TRGT}<i>SPCM | PRSC | EXMN_CD | RSLT</i></li>
	 *         <li>[CODE] 409 : {@link MSLCError#MODEL_CRTN_ERR}</li>
	 *         </ul>
	 */
	@SuppressWarnings("unchecked")
	public static <E extends PkModel> LuluResult chckAndCreateSet(Class<E> clazz, Object... param) {
		HashSet<E> set = new HashSet<>();
		LuluResult luluResult = new LuluResult();
		for (Object eachParam : param) {
			try {
				E model = (E) PkModel.getInstance(clazz, eachParam);
				if (!set.add(model)) {
					return MSLCError.RDDC_TRGT.withBody(model.rtrvModelName()).createLuluResult();
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				return MSLCError.MODEL_CRTN_ERR.createLuluResult();
			}
		}
		luluResult.setResultData(set);
		return luluResult;
	}

	/**
	 * 필드 맵 목록을 사용, 중복을 없는 모델 생성.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Feb 26, 2024
	 * @param <E>
	 * @param param
	 * @param clazz
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 파라미터로 생성한 모델 집합 HashSet&lt;PkModel&gt;.</li>
	 *         <li>[CODE] 403 : {@link MSLCError#RDDC_TRGT}<i>SPCM | PRSC | EXMN_CD | RSLT</i></li>
	 *         <li>[CODE] 409 : {@link MSLCError#MODEL_CRTN_ERR}</li>
	 *         </ul>
	 */
	@SuppressWarnings("unchecked")
	public static <E extends PkModel> LuluResult chckAndCreateSet(Class<E> clazz,
			List<? extends Map<String, Object>> param) {
		HashSet<E> set = new HashSet<>();
		LuluResult luluResult = new LuluResult();
		for (Map<String, Object> eachParam : param) {
			try {
				E model = (E) PkModel.getInstance(clazz, eachParam);
				if (!set.add(model)) {
					return MSLCError.RDDC_TRGT.withBody(model.rtrvModelName()).createLuluResult();
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				return MSLCError.MODEL_CRTN_ERR.createLuluResult();
			}
		}
		luluResult.setResultData(set);
		return luluResult;
	}

	/**
	 * 필드값 배열 인자를 사용, 중복을 무시한 모델 생성.
	 * 
	 * <b>단일 식별자를 가진 모델에 대해서만 사용 가능.</b> <b>필드값에 대한 validation 선수행 필요.</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Feb 26, 2024
	 * @param <E>
	 * @param param
	 * @param clazz
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 파라미터로 생성한 모델 집합 HashSet&lt;PkModel&gt;.</li>
	 *         <li>[CODE] 409 : {@link MSLCError#MODEL_CRTN_ERR}</li>
	 *         </ul>
	 */
	@SuppressWarnings("unchecked")
	public static <E extends PkModel> LuluResult chckAndCreateSetIgnoreRddc(Class<E> clazz, Object... param) {
		LuluResult luluResult = new LuluResult();
		HashSet<E> set = new HashSet<>();
		for (Object eachParam : param) {
			try {
				set.add((E) PkModel.getInstance(clazz, eachParam));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				return MSLCError.MODEL_CRTN_ERR.createLuluResult();
			}
		}
		luluResult.setResultData(set);
		return luluResult;
	}

	/**
	 * 필드 맵 목록을 사용, 중복을 무시한 모델 생성.
	 * 
	 * <b>필드값에 대한 validation 선수행 필요.</b>
	 * 
	 * @author khgkjg12 강현구A
	 * @date Feb 26, 2024
	 * @param <E>
	 * @param param
	 * @param clazz
	 * @return
	 *         <ul>
	 *         LuluResult
	 *         <li>[CODE] 200 : [DATA] 파라미터로 생성한 모델 집합 HashSet&lt;PkModel&gt;.</li>
	 *         <li>[CODE] 409 : {@link MSLCError#MODEL_CRTN_ERR}</li>
	 *         </ul>
	 */
	@SuppressWarnings("unchecked")
	public static <E extends PkModel> LuluResult chckAndCreateSetIgnoreRddc(Class<E> clazz,
			List<? extends Map<String, Object>> param) {
		LuluResult luluResult = new LuluResult();
		HashSet<E> set = new HashSet<>();
		for (Map<String, Object> eachParam : param) {
			try {
				set.add((E) PkModel.getInstance(clazz, eachParam));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				return MSLCError.MODEL_CRTN_ERR.createLuluResult();
			}
		}
		luluResult.setResultData(set);
		return luluResult;
	}
}
