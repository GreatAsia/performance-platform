package com.okay.testcenter.domain.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @auth 谢扬扬
 * @date 2021/2/25 10:51
 */
@Getter
@Setter
public class GroupSimpleDetailResponse {
    private Integer roleId;

    private String roleCode;

    private Integer menuId;

    private String menuUrl;
}
