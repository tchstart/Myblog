package com.tchstart.blog.controller.app;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tchstart.blog.annotation.AccessLimit;
import com.tchstart.blog.model.enums.BlogStatus;
import com.tchstart.blog.model.vo.CategoryVO;
import com.tchstart.blog.service.BlogService;
import com.tchstart.blog.service.CategoryService;
import com.tchstart.blog.model.R;
import com.tchstart.blog.utils.RUtils;

import java.util.List;

/**
 * Category Controller
 *
 * @author tchstart
 */
@RestController("AppCategoryController")
@RequestMapping("/api/app/category")
@Api(value = "前台分类控制器", tags = {"前台分类接口"})
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BlogService blogService;


	@AccessLimit(maxCount = 2)
	@GetMapping("/list")
	@ApiOperation("获取已使用的分类")
	public R listUsedCategory() {
		List<CategoryVO> usedCategoryList = categoryService.listUsedCategory();
		// 获取每个分类下的文章数目
		for (CategoryVO vo : usedCategoryList) {
			int count = blogService.getCountByCategoryId(vo.getId(), BlogStatus.PUBLISHED);
			vo.setBlogCount(count);
		}
		return RUtils.success("分类列表", usedCategoryList);
	}

}
