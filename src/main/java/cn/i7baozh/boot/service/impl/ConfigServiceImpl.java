package cn.i7baozh.boot.service.impl;

import cn.i7baozh.boot.service.ConfigService;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @Title:
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService {

    private static final Properties properties = new Properties();

    @Autowired
    private CuratorFramework curatorFramework;

    @Value("${zk.projectConfigDir:/configuration}")
    private String projectConfigRemoteDir;

    private static final String PATH_SPLIT = "/";

    /**
     * 本地配置文件地址
     */
    private final static String PROJECT_CONFIG_LOCAL_DIR = "config/system.properties";

    @PostConstruct
    public void init() throws Exception {
        //上传本地文件到zk
        uploadRemoteServer();
        //监控 projectConfigDir 所指向的节点 子节点发生变化更改本地内容
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, projectConfigRemoteDir, false);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                PathChildrenCacheEvent.Type type = event.getType();
                if (event.getData() != null) {
                    String path = event.getData().getPath();
                    String key = path.substring(path.lastIndexOf(PATH_SPLIT) + 1);
                    //只有子节点增删改的时候触发
                    if (PathChildrenCacheEvent.Type.CHILD_ADDED.equals(type)
                            || PathChildrenCacheEvent.Type.CHILD_UPDATED.equals(type)) {
                        //新增节点 或删除节点
                        String value = new String(client.getData().forPath(event.getData().getPath()), Charsets.UTF_8);
                        properties.setProperty(key, value);
                        log.info("zk remote add /update config key:{},value:{}", key, value);
                    }
                    if (PathChildrenCacheEvent.Type.CHILD_REMOVED.equals(type)) {
                        //移除节点
                        properties.remove(key);
                        log.info("zk remote remove key:{}", key);
                    }
                }
            }
        });
        pathChildrenCache.start();
    }

    /**
     * 上传本地文件到远程zk
     * @throws Exception
     */
    private void uploadRemoteServer() throws Exception{
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROJECT_CONFIG_LOCAL_DIR);
        Properties prop = new Properties();
        prop.load(resourceAsStream);
        Enumeration<Object> keys = prop.keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = prop.get(key);
            createNode(key.toString(), value.toString());
        }
        printProperties();
    }

    /**
     * 创建节点
     * @param key
     * @param value
     */
    private void createNode(String key, String value) throws Exception {

        if (Strings.isNullOrEmpty(value)) {
            return ;
        }
        String path = projectConfigRemoteDir.concat(PATH_SPLIT).concat(key);
        Stat stat = curatorFramework.checkExists().forPath(path);
        if (null == stat) {
            //节点不存在
            curatorFramework.create().forPath(path, value.getBytes(Charsets.UTF_8));
        } else {
            curatorFramework.setData().forPath(path, value.getBytes(Charsets.UTF_8));
        }
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    @Override
    public void printProperties() {
        log.info("printProperties");
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = properties.get(key);
            log.info("key:{},value:{}", key, value);
        }
    }
}
