package com.tchstart.blog.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Date Utils
 *
 * @author tchstart
 */
public class DateUtils {

	/**
	 * 获取前 before 天的 零点时刻
	 */
	public static LocalDateTime getZeroDateTime(int before) {
		return LocalDateTime.of(LocalDate.now().minusDays(before), LocalTime.MIN);
	}

}
