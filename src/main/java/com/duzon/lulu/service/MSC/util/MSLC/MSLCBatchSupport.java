package com.duzon.lulu.service.MSC.util.MSLC;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

@Component
public class MSLCBatchSupport {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	public void insertBatch(String statement, Collection<?> items) {
		execute(statement, items, (sqlSession, item) -> sqlSession.insert(statement, item));
	}

	public void updateBatch(String statement, Collection<?> items) {
		execute(statement, items, (sqlSession, item) -> sqlSession.update(statement, item));
	}

	public void deleteBatch(String statement, Collection<?> items) {
		execute(statement, items, (sqlSession, item) -> sqlSession.delete(statement, item));
	}

	private List<BatchResult> execute(String statement, Collection<?> items, BiConsumer<SqlSession, Object> consumer) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
			items.forEach(item -> consumer.accept(sqlSession, item));

			List<BatchResult> batchResults = sqlSession.flushStatements();
			sqlSession.commit();
			return batchResults;
		}
	}

	/**
	 * 삽입 배치를 수행하고 결과를 반환
	 * 
	 * @author khgkjg12 강현구A
	 * @param statement 매핑된 쿼리경로
	 * @param items     배치를 수행할 각 매개변수
	 * @return 배치 수행 후 effectedRow가 0인 쿼리가 존재한다면 false.
	 */
	public boolean insertBatchWithResult(String statement, Collection<?> items) {
		return executeWithResult(items, (sqlSession, item) -> sqlSession.insert(statement, item));
	}

	/**
	 * 갱신 배치를 수행하고 결과를 반환
	 * 
	 * @author khgkjg12 강현구A
	 * @param statement 매핑된 쿼리경로
	 * @param items     배치를 수행할 각 매개변수
	 * @return 배치 수행 후 effectedRow가 0인 쿼리가 존재한다면 false.
	 */
	public boolean updateBatchWithResult(String statement, Collection<?> items) {
		return executeWithResult(items, (sqlSession, item) -> sqlSession.update(statement, item));
	}

	/**
	 * 삭제 배치를 수행하고 결과를 반환
	 * 
	 * @author khgkjg12 강현구A
	 * @param statement 매핑된 쿼리경로
	 * @param items     배치를 수행할 각 매개변수
	 * @return 배치 수행 후 effectedRow가 0인 쿼리가 존재한다면 false.
	 */
	public boolean deleteBatchWithResult(String statement, Collection<?> items) {
		return executeWithResult(items, (sqlSession, item) -> sqlSession.delete(statement, item));
	}

	/**
	 * 배치 수행후 결과 비교하는 서브로직
	 * 
	 * @author khgkjg12 강현구A
	 * @see #deleteBatchWithResult(String, Collection)
	 * @see #updateBatchWithResult(String, Collection)
	 * @see #insertBatchWithResult(String, Collection)
	 */
	private <T> boolean executeWithResult(Collection<T> items, BiConsumer<SqlSession, T> consumer) {
		boolean acc = true;
		try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
			for (T item : items) {
				consumer.accept(sqlSession, item);
			}
			int[] updatedCountArray = sqlSession.flushStatements().get(0).getUpdateCounts();
			for (int count : updatedCountArray) {
				if (count < 1) {
					acc = false;
				}
			}
			sqlSession.commit();
		}
		return acc;
	}

	/**
	 * 조회 배치를 수행하고 결과를 반환
	 * 
	 * @author khgkjg12 강현구A
	 * @param statement 매핑된 쿼리경로
	 * @param items     배치를 수행할 각 매개변수
	 */
	public <E, T> ArrayList<E> selectBatch(String statement, Collection<T> param) {
		ArrayList<E> result = new ArrayList<E>();
		try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
			for (T item : param) {
				result.addAll(sqlSession.selectList(statement, item));
			}
		}
		return result;
	}

	/**
	 * 삽입 배치를 수행하고 결과를 반환
	 * 
	 * @author khgkjg12 강현구A
	 * @param statement 매핑된 쿼리경로
	 * @param items     배치를 수행할 각 매개변수
	 * @param expected  각 쿼리 수행 예상 결과(effected row)
	 * @return 배치 수행 후 예상 결과와 다른 쿼리가 존재한다면 false.
	 */
	public boolean insertBatchWithResult(String statement, Collection<?> items, int expected) {
		return executeWithResult(items, (sqlSession, item) -> sqlSession.insert(statement, item), expected);
	}

	/**
	 * 갱신 배치를 수행하고 결과를 반환
	 * 
	 * @author khgkjg12 강현구A
	 * @param statement 매핑된 쿼리경로
	 * @param items     배치를 수행할 각 매개변수
	 * @param expected  각 쿼리 수행 예상 결과(effected row)
	 * @return 배치 수행 후 예상 결과와 다른 쿼리가 존재한다면 false.
	 */
	public boolean updateBatchWithResult(String statement, Collection<?> items, int expected) {
		return executeWithResult(items, (sqlSession, item) -> sqlSession.update(statement, item), expected);
	}

	/**
	 * 삭제 배치를 수행하고 결과를 반환
	 * 
	 * @author khgkjg12 강현구A
	 * @param statement 매핑된 쿼리경로
	 * @param items     배치를 수행할 각 매개변수
	 * @param expected  각 쿼리 수행 예상 결과(effected row)
	 * @return 배치 수행 후 예상 결과와 다른 쿼리가 존재한다면 false.
	 */
	public boolean deleteBatchWithResult(String statement, Collection<?> items, int expected) {
		return executeWithResult(items, (sqlSession, item) -> sqlSession.delete(statement, item), expected);
	}

	/**
	 * 배치 수행후 결과 비교하는 서브로직
	 * 
	 * @author khgkjg12 강현구A
	 * @see #deleteBatchWithResult(String, Collection, int)
	 * @see #updateBatchWithResult(String, Collection, int)
	 * @see #insertBatchWithResult(String, Collection, int)
	 */
	private <T> boolean executeWithResult(Collection<T> items, BiConsumer<SqlSession, T> consumer, int expected) {
		boolean acc = true;
		try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
			for (T item : items) {
				consumer.accept(sqlSession, item);
			}
			int[] updatedCountArray = sqlSession.flushStatements().get(0).getUpdateCounts();
			for (int count : updatedCountArray) {
				if (count != expected) {
					acc = false;
				}
			}
			sqlSession.commit();
		}
		return acc;
	}
}
