package com.tchstart.blog.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.tchstart.blog.constant.OtherOptionEnum;
import com.tchstart.blog.model.base.ValidGroupType;
import com.tchstart.blog.model.entity.LinkEntity;
import com.tchstart.blog.model.params.LinkSearchParam;
import com.tchstart.blog.model.vo.LinkVO;
import com.tchstart.blog.service.LinkService;
import com.tchstart.blog.model.PageResult;
import com.tchstart.blog.model.R;
import com.tchstart.blog.service.OptionsService;
import com.tchstart.blog.utils.RUtils;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Link Controller
 *
 * @author tchstart
 */
@Validated
@RestController("AdminLinkController")
@RequestMapping("/api/admin/link")
@Api(value = "后台友链控制器", tags = {"后台友链接口"})
public class LinkController {

	@Autowired
	private LinkService linkService;

	@Autowired
	private OptionsService optionsService;


	@PostMapping("/list")
	@ApiOperation("获取友链列表信息")
	public R list(@RequestBody @Valid LinkSearchParam param) {
		IPage<LinkEntity> page = linkService.pageBy(param);
		PageResult<LinkVO> pageResult = linkService.covertToPageResult(page);
		return RUtils.success("友链列表信息", pageResult);
	}

	@PostMapping("/update")
	@ApiOperation("更新友链信息")
	public R updateBlog(@RequestBody @Validated(ValidGroupType.Update.class) LinkVO vo) {
		int i = linkService.update(vo);
		return RUtils.commonFailOrNot(i, "友链信息更新");
	}

	@DeleteMapping("/delete/{linkId}")
	@ApiOperation("删除友链")
	public R deleteBlog(@PathVariable("linkId") @Min(1) Integer linkId) {
		int i = linkService.deleteById(linkId);
		return RUtils.commonFailOrNot(i, "删除友链");
	}

	@PostMapping("/add")
	@ApiOperation("添加友链")
	public R addBlog(@RequestBody @Validated(ValidGroupType.Save.class) LinkVO vo) {
		int i = linkService.add(vo);
		return RUtils.commonFailOrNot(i, "友链添加");
	}

	@PostMapping("/update/parser")
	@ApiOperation("更新网站Logo（favicon）解析API")
	public R updateUrlLogoParser(@RequestParam("parser") @NonNull String logoParser) {
		int i = optionsService.setAnyway(OtherOptionEnum.URL_FAVICON_PARSER.key(), logoParser);
		if (i > 0) {
			int j = linkService.updateLogoParser(logoParser);
			if (j > 0) {
				optionsService.resetOptionsCache();
			}
		}
		return RUtils.commonFailOrNot(i, "友链Logo解析API修改");
	}
}
