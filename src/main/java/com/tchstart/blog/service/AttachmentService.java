package com.tchstart.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;
import com.tchstart.blog.model.entity.AttachmentEntity;
import com.tchstart.blog.model.params.AttachmentSearchParam;
import com.tchstart.blog.model.vo.AttachmentVO;
import com.tchstart.blog.model.PageResult;

import java.util.List;

/**
 * Attachment Service
 *
 * @author tchstart
 */
public interface AttachmentService {

	/**
	 * 添加附件
	 */
	AttachmentVO add(MultipartFile file, String team);

	/**
	 * 修改附件名
	 */
	int updateNameById(int attachmentId, String attachmentName);

	/**
	 * 删除附件
	 */
	int deleteById(int attachmentId);

	/**
	 * 批量删除附件
	 */
	int deleteById(List<Integer> attachmentIds);

	/**
	 * 更新附件分组
	 */
	int updateTeam(List<Integer> attachmentIds, String team);

	/**
	 * 获取所有的文件类型
	 */
	List<String> listAllMediaTypes();

	/**
	 * 获取所有分组
	 */
	List<String> listAllTeams();

	IPage<AttachmentEntity> pageBy(AttachmentSearchParam param);

	PageResult<AttachmentVO> covertToPageResult(IPage<AttachmentEntity> page);

	AttachmentVO covertToVO(AttachmentEntity attachmentEntity);

	List<AttachmentVO> covertToListVO(List<AttachmentEntity> attachmentEntityList);

}
