package com.tchstart.blog.mapper;

import org.apache.ibatis.annotations.Param;
import com.tchstart.blog.model.entity.LinkEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 友链 Mapper 接口
 * </p>
 *
 * @author tchstart
 */
public interface LinkMapper extends BaseMapper<LinkEntity> {

	/**
	 * 根据Logo解析器更新全部记录Logo
	 */
	Integer updateLogoByParser(@Param("parser") String parser);

}
