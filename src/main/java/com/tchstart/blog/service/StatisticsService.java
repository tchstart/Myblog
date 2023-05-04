package com.tchstart.blog.service;

import com.tchstart.blog.model.vo.StatisticsBasicVO;
import com.tchstart.blog.model.vo.StatisticsReportVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Statistics Service
 *
 * @author tchstart
 */
public interface StatisticsService {

	/**
	 * 获取基本的统计信息
	 */
	StatisticsBasicVO getCommonStatistics();

	/**
	 * 获取时间段 start - end 的每日统计信息
	 */
	List<StatisticsReportVo> getDailyStatistics(LocalDateTime start, LocalDateTime end);

	/*
	 * 统计昨日数据
	 */
	void statisticsDaily();

	/**
	 * 获取网站总访问量
	 */
	int getWebTotalVisit();
}
