package cn.i7baozh.boot;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.RetryOneTime;
import org.junit.Test;

/**
 * @Title:
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
public class ZkTest {

    @Test
    public void test() throws Exception{

        RetryPolicy retryPolicy = new RetryOneTime(1000);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .namespace("boot")
                .retryPolicy(retryPolicy)
                .connectString("localhost:2181")
                .build();
        client.start();
//        client.create().creatingParentsIfNeeded().forPath("/abcd/abc", "data".getBytes());
        client.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println(curatorEvent);
            }
        });
    }
}
