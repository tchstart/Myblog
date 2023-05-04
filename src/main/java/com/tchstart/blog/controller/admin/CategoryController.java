package com.tchstart.blog.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.tchstart.blog.model.PageResult;
import com.tchstart.blog.model.R;
import com.tchstart.blog.model.base.ValidGroupType;
import com.tchstart.blog.model.entity.CategoryEntity;
import com.tchstart.blog.model.params.BasePageParam;
import com.tchstart.blog.model.vo.CategoryVO;
import com.tchstart.blog.service.BlogService;
import com.tchstart.blog.service.CategoryService;
import com.tchstart.blog.utils.RUtils;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Category Controller
 *
 * @author tchstart
 */
@Validated
@RestController("AdminCategoryController")
@RequestMapping("/api/admin/blog/category")
@Api(value = "后台分类控制器", tags = {"后台分类接口"})
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BlogService blogService;

	@PostMapping("/list")
	@ApiOperation("分页获取博客分类")
	public R list(@RequestBody @Valid BasePageParam param) {
		IPage<CategoryEntity> categoryPage = categoryService.pageBy(param);
		PageResult<CategoryVO> pageResult = categoryService.covertToPageResult(categoryPage);
		// 获取每个分类下的文章数目
		for (CategoryVO vo : pageResult.getList()) {
			int count = blogService.getCountByCategoryId(vo.getId(), null);
			vo.setBlogCount(count);
		}
		return RUtils.success("博客分类分页信息", pageResult);
	}

	@GetMapping("/list/all")
	@ApiOperation("获取所有博客分类")
	public R listAll() {
		List<CategoryVO> categoryVOList = categoryService.listAllCategory();
		return RUtils.success("所有的博客分类", categoryVOList);
	}

	@GetMapping("/list/used")
	@ApiOperation("获取已使用的博客分类")
	public R listUsed() {
		List<CategoryVO> usedCategory = categoryService.listUsedCategory();
		return RUtils.success("已使用的博客分类", usedCategory);
	}

	@DeleteMapping("/delete/{categoryId}")
	@ApiOperation("删除分类")
	public R delete(@PathVariable("categoryId") @NotNull @Min(1) Integer categoryId) {
		int i = categoryService.deleteById(categoryId);
		return RUtils.commonFailOrNot(i, "删除分类");
	}

	@PostMapping("/add")
	@ApiOperation("添加分类")
	public R add(@RequestBody @Validated({ValidGroupType.Save.class}) CategoryVO vo) {
		int i = categoryService.add(vo);
		if (i > 0) {
			return RUtils.success("添加分类成功");
		}
		if (i < 0) {
			return RUtils.fail("分类名已存在");
		}
		return RUtils.fail("添加分类失败");
	}

	@PostMapping("/update")
	@ApiOperation("更新分类")
	public R update(@RequestBody @Validated({ValidGroupType.Update.class}) CategoryVO vo) {
		int i = categoryService.update(vo);
		return RUtils.commonFailOrNot(i, "更新分类");
	}

}
