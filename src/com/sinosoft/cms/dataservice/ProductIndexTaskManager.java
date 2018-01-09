package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.search.ProductIndexer;

/**
 * Created by liyinfeng on 2016/11/2.
 */
public class ProductIndexTaskManager extends ConfigEanbleTaskManager {

    private long indexId = 1024;
    public Mapx getConfigEnableTasks() {
        Mapx map = new Mapx();
        map.put("-1", "文章索引创建");
        map.put("0", "文章索引更新");
        return map;
    }

    public void execute(long id) {
            ProductIndexer productIndexer = new ProductIndexer();
        if ("-1".equals(id + "")) {
            productIndexer.createProductIndex(indexId);
        } else if ("0".equals(id + "")) {
            productIndexer.updateProductIndex(indexId);
        }
    }

    public String getCode() {
        return "com.sinosoft.cms.dataservice.ProductIndexTaskManager";
    }

    public String getName() {
        return "文章索引创建与维护";
    }

    public boolean isRunning(long arg0) {
        return false;
    }

    @Override
    public void execute(String paramString) {
        execute(Integer.parseInt(paramString));
    }

    @Override
    public boolean isRunning(String paramString) {
        return false;
    }

    @Override
    public String getID() {
        return "com.sinosoft.cms.dataservice.ProductIndexTaskManager";
    }
}
