package com.duzon.lulu.service.MSC.util.MSLC;

/**
 * 계산식 유틸
 * 
 * @author 강현구A
 * @since 2024-04-15
 */
public class Calculator {
	/**
	 * MDRD, 4, ID-MS traceable, eGFR 계산식
	 * 
	 * @author khgkjg12 강현구A
	 * @date Apr 15, 2024
	 * @param sc                Serum Creatinine in mg/dl.
	 * @param age               나이
	 * @param isFemale          true 여자 false 남자
	 * @param isAfricanAmerican 흑인 여부.
	 * @return eGFR결과 소숫점 반올림값. sc또는 age가 0이하일경우 -1을 반환한다. 
	 */
	public static long mdrd(double sc, int age, boolean isFemale, boolean isAfricanAmerican) {
		if (sc <= 0)
			return -1;
		if (age <= 0)
			return -1;
		double result = 175 * Math.pow(sc, -1.154) * Math.pow(age, -0.203);
		if (isFemale) {
			result *= 0.742;
		}
		if (isAfricanAmerican) {
			result *= 1.212;
		}
		return Math.round(result);
	}
	
	/**
	 * 간접빌리루빈 계산식.
	 * @author khgkjg12 강현구A
	 * @date Apr 16, 2024
	 * @param tBil 총 빌리루빈.
	 * @param dBil 간접 빌리루빈.
	 * @return 간접 빌리루빈 값. 소숫점 1자리까지 반올림. tBil이 dBil미만 이거나 입력값이 하나라도 음수면 -1을 반환한다. 
	 */
	public static double indirectBilirubin(double tBil, double dBil) {
		if(tBil<0 ||dBil<0||tBil<dBil)return -1;
		return Math.round(tBil*10-dBil*10)/(double) 10.0;
	}
}
