package com.okay.testcenter;

import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by zhou on 2019/9/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetZkList {

    private ZooKeeper zk = null;
    @Test
    public void TestZKAll() throws Exception {
        ls("/xdfapp/");
        zk.close();
    }


    /**
     * 列出指定path下所有孩子
     */
    public void ls(String path) throws Exception {
        System.out.println(path);
        zk = new ZooKeeper("10.10.1.7:2181", 10000, null);
        List<String> list = zk.getChildren(path, null);
        //判断是否有子节点
        if (list.isEmpty() || list == null) {
            return;
        }

        System.out.println("[list]==" + list);

    }

}
