package com.okay.testcenter.service.tool;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.AddUserDetail;

import java.util.List;


public interface AddUserDetailService {


    void insertDetail(AddUserDetail addUserDetail);

    void deleteDetail(int id);

    void updateDetail(AddUserDetail addUserDetail);

    AddUserDetail findDetailByAccount(int account);

    List<AddUserDetail> findDetailByHistoryId(int historyId);

    PageInfo findDetailList(int currentPage, int pageSize);

    List<AddUserDetail> findDetailList();

    void createFile(int envId, String type);
}
