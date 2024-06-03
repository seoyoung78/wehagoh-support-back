package com.duzon.lulu.service.MSC.util;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

@Component
public class MyBatisBatchSupport {

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

}