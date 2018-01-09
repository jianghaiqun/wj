package com.sinosoft.cms.document;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDMailConfigImageSchema;
import com.sinosoft.schema.ZDMailConfigImageSet;

import java.io.File;

/**
 * @ClassName: MailConfigImage
 * @Description: TODO(邮件模板-活动图片).
 * @author XXX
 * @date 2014-8-7 下午03:30:25
 *
 * <p> 修改历史</p>
 * <p> 序号 日期 修改人 修改原因</p>
 */
public class MailConfigImage extends Page {

    /**
     * @Title: initEditDialog.
     * @Description: TODO(邮件配置-活动图片修改初始化).
     * @return Mapx    返回类型.
     * @author CongZN.
     */
    @SuppressWarnings("unchecked")
    public static Mapx initEditDialog(Mapx params) {
        String ID = params.getString("ID");
        ZDMailConfigImageSchema zdm = new ZDMailConfigImageSchema();
        zdm.setID(ID);
        zdm.fill();
        params.putAll(zdm.toMapx());
        return params;
    }


    /**
     * @Title: dg1DataList.
     * @Description: TODO(结果页查询).
     * @return void    返回类型.
     * @author CongZN.
     */
    public static void dg1DataList(DataListAction dla) {
        String emailType = dla.getParam("emailType");
        if (StringUtil.isEmpty(emailType)) {
            dla.bindData(new DataTable());
            return;
        }

        QueryBuilder qb = new QueryBuilder("select * from ZDMailConfigImage where emailType = ?");
        qb.add(emailType);

        qb.append(" order by id desc");
        dla.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
        dla.bindData(dt);
    }

    /**
     * @Title: updateMailImageInfo.
     * @Description: TODO(邮箱配置-活动图片其他信息更新).
     * @return void    返回类型.
     * @author CongZN.
     */
    public void updateMailImageInfo(){
        String mailImageID = $V("MailImageID");
        String file1Name = $V("File1Name");
        String file1Info = $V("File1Info");

        try {
            Transaction ts = new Transaction();
            ZDMailConfigImageSchema zdm = new ZDMailConfigImageSchema();

            zdm.setID(mailImageID);
            zdm.fill();

            zdm.setImageLink(file1Name);
            zdm.setImageDesc(file1Info);
            zdm.setOperator(User.getUserName());
            zdm.setModifyDate(PubFun.getCurrent());
            ts.add(zdm, Transaction.UPDATE);

            if (ts.commit()) {
                Response.setStatus(1);
                Response.setMessage("保存成功!");
            } else {
                Response.setStatus(2);
                Response.setMessage("保存失败! error:提交事务失败!");
            }
        } catch (Exception e) {
            logger.error("邮件模板配置-上传图片,保存 Table ZDMailConfigImage 出现异常!" + e.getMessage(), e);
        }

    }

    /**
     *
     * @Title: mailConfigImageDel.
     * @Description: TODO(这里用一句话描述这个方法的作用).
     * @return void    返回类型.
     * @author CongZN.
     */
    public void mailConfigImageDel() {
        String IDs = $V("IDs");
        if (!StringUtil.checkID(IDs)) {
            Response.setStatus(0);
            Response.setMessage("传入ID时发生错误!");
            return;
        }

        Transaction trans = new Transaction();
        ZDMailConfigImageSet ImageSet = new ZDMailConfigImageSchema().query(new QueryBuilder(" where id in (" + IDs + ")"));

        for (int i = 0; i < ImageSet.size(); i++) {
            ZDMailConfigImageSchema sdm = ImageSet.get(i);
            FileUtil.delete(Config.getContextRealPath() + File.separator + sdm.getImagePath());
        }
        trans.add(ImageSet, Transaction.DELETE);
        if (trans.commit()) {
            this.Response.setLogInfo(1, "删除图片成功");
        }
    }

}
