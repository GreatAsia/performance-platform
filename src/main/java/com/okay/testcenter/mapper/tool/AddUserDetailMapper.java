package com.okay.testcenter.mapper.tool;

import com.okay.testcenter.domain.middle.AddUserDetail;

import java.util.List;


public interface AddUserDetailMapper {


    void insertDetail(AddUserDetail addUserDetail);

    void deleteDetail(int id);

    void updateDetail(AddUserDetail addUserDetail);


    AddUserDetail findDetailByAccount(int account);

    List<AddUserDetail> findDetailByHistoryId(int historyId);

    List<AddUserDetail> findDetailList();


}
