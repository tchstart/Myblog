package com.tchstart.blog.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tchstart.blog.annotation.AccessLimit;
import com.tchstart.blog.model.entity.LinkEntity;
import com.tchstart.blog.model.params.LinkSearchParam;
import com.tchstart.blog.model.vo.LinkVO;
import com.tchstart.blog.service.LinkService;
import com.tchstart.blog.model.R;
import com.tchstart.blog.utils.RUtils;

import java.util.List;

/**
 * Link Controller
 *
 * @author tchstart
 */
@RestController("AppLinkController")
@RequestMapping("/api/app/link")
@Api(value = "前台友链控制器", tags = {"前台友链接口"})
public class LinkController {

	@Autowired
	private LinkService linkService;

	@AccessLimit(maxCount = 2)
	@GetMapping("/list")
	@ApiOperation("获取所有的友情链接")
	public R listUsedCategory() {
		LinkSearchParam param = new LinkSearchParam();
		param.setCurrent(1).setPageSize(Integer.MAX_VALUE);
		IPage<LinkEntity> linkPage = linkService.pageBy(param);
		List<LinkVO> linkVOList = linkService.covertToListVO(linkPage.getRecords());
		return RUtils.success("友链列表", linkVOList);
	}

}
