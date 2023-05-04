package com.tchstart.blog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.tchstart.blog.model.enums.SpecialListType;

import java.util.List;

/**
 * Special List Search Param
 *
 * @author tchstart
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("特殊清单搜索参数")
public class SpeciallistSearchParam extends BasePageParam {

	@ApiModelProperty("类型")
	private List<SpecialListType> types;

	@ApiModelProperty("内容")
	private String content;

}
