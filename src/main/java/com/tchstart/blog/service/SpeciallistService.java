package com.tchstart.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import com.tchstart.blog.model.PageResult;
import com.tchstart.blog.model.entity.SpeciallistEntity;
import com.tchstart.blog.model.enums.SpecialListType;
import com.tchstart.blog.model.params.SpeciallistSearchParam;
import com.tchstart.blog.model.vo.SpeciallistVO;

import java.util.List;

/**
 * Log Service
 *
 * @author tchstart
 */
public interface SpeciallistService extends IService<SpeciallistEntity> {

	/**
	 * 获取枚举的类型
	 */
	List<SpeciallistVO> listEnumType();

	/**
	 * 获取特定类型的所有记录
	 */
	List<SpeciallistVO> listAll(SpecialListType... types);

	/**
	 * 条件分页搜索
	 */
	IPage<SpeciallistEntity> pageBy(SpeciallistSearchParam param);

	/**
	 * 添加清单
	 */
	int add(SpeciallistVO vo);

	/**
	 * 批量导入
	 */
	boolean addBatch(SpecialListType type, @NonNull List<String> contents);

	/**
	 * 删除清单
	 */
	int deleteById(int id);

	/**
	 * 批量删除清单
	 */
	int deleteByIds(@NonNull List<Integer> ids);

	/**
	 * 刷新缓存
	 */
	boolean refreshContext(SpecialListType type);

	SpeciallistVO covertToVO(SpeciallistEntity entity);

	List<SpeciallistVO> covertToListVO(List<SpeciallistEntity> entityList);

	PageResult<SpeciallistVO> covertToPageResult(IPage<SpeciallistEntity> page);

}
