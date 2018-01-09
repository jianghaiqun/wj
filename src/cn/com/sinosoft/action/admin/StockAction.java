package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.entity.Gift;
import cn.com.sinosoft.entity.Stock;
import cn.com.sinosoft.service.GiftService;
import cn.com.sinosoft.service.StockService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.sinosoft.framework.User;
import com.sinosoft.framework.security.EncryptUtil;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
   
/**
 * 后台Action类 - 库存管理
 * ============================================================================
 * KEY:SINOSOFT483FBE0A69F1A3F1D081F3F2D838E89C
 * ============================================================================
 */

@ParentPackage("admin")
public class StockAction extends BaseAdminAction {
	private static final long serialVersionUID = -6892797310128684890L;
	@Resource
	private StockService stockService;

	@Resource
	private GiftService giftService;

	private Stock stock;
	private List<Stock> stockList;
	private File importRateFile;

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		stock = stockService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = stockService.findByPager(pager);
		return LIST;
	}
	/**
	 * 解析上传的xls文件并保存到数据库 parseUploadXlsFileToDB
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private int parseUploadXlsFileToDB(File impFile, Stock dbStock) throws FileNotFoundException, IOException
	{
		if ((null == impFile) || !(impFile.exists()))
		{
			return 0;
		}
		// 取得并解析文件头部
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(impFile));
		HSSFSheet sheet = workbook.getSheetAt(0);// 取头一个sheet
		Gift gift = null;
		int count = 0;
		int rowNum = 0;
		Pattern pattern = Pattern.compile("^[2-9]\\d{3}\\-((((0[13578])|(1[02]))\\-((0[1-9])|([12]\\d)|(3[01])))|(((0[469])|(11))\\-((0[1-9])|([12]\\d)|(30)))|(02\\-((0[1-9])|([12]\\d))))$");
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			HSSFRow row = sheet.getRow(i);
			if ((null == row) || (null == row.getCell(0)))
			{
				continue;
			}
			rowNum++;
			// 格式化卡号
			DecimalFormat df = new DecimalFormat("0");
			String cardNo = row.getCell(0).toString();
			if (row.getCell(0).getCellType() == 0) {
				cardNo = df.format(row.getCell(0).getNumericCellValue()).trim();
			} else if (row.getCell(0).getCellType() == 1) {
				cardNo = row.getCell(0).getStringCellValue().trim();
			}
			if (StringUtil.isEmpty(cardNo))
			{
				/* 卡号不能为空 */
				continue;
			}
			if (row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
			{
				cardNo = cardNo.replaceAll("\\.0+$", "");
			}
			String password = (null == row.getCell(1)) ? null : row.getCell(1).toString();
			if (row.getCell(1).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
			{
				password = password.replaceAll("\\.0+$", "");
			}
			String expireDate = (null == row.getCell(2)) ? null : row.getCell(2).toString();
			if (StringUtil.isEmpty(password) || StringUtil.isEmpty(expireDate))
			{
				/* 密码和有效期不能为空 */
				continue;
			}
			Matcher matcher = pattern.matcher(expireDate);
			if (!matcher.matches())
			{
				/* 有效期格式不合法，必须是YYYY-MM-DD格式 */
				continue;
			}
			password = EncryptUtil.encrypt3DES(password, EncryptUtil.DEFAULT_KEY);
			String description = (null == row.getCell(3)) ? null : row.getCell(3).toString();
			gift = new Gift();
			gift.setStock(dbStock);
			gift.setCardNo(cardNo);
			gift.setPassword(password);
			gift.setExpireDate(DateUtil.setTimeOfDate(DateUtil.parse(expireDate), 23, 59, 59));
			gift.setDescription(description);
			gift.setStatus("Y");
			gift.setAddress(User.getUserName());
			giftService.save(gift);
			count++;
		}
		return count;
	}

	// 保存
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "stock.name", message = "库存名称不允许为空!") })
	@InputConfig(resultName = "error")
	public String save() {

		/* zhangjinquan 11180 增加相同名称库存判断 2012-11-01 */
		if (null != stockService.getStockByName(stock.getName()))
		{
			addActionError("已存在名称为“" + stock.getName() + "”的库存分类，不能重复添加！");
			return ERROR;
		}
		stock.setBetake(0);
		stock.setResidue(0);
		stockService.save(stock);
		if ((null == importRateFile) || !(importRateFile.exists())) {
			addActionError("请上传库存表!");
			redirectionUrl = "stock!list.action";
			return SUCCESS;
		}
		try {
			stock.setResidue(this.parseUploadXlsFileToDB(importRateFile, stock));
			stockService.update(stock);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("添加库存分类成功，但是 导入数据失败!");
			return ERROR;
		}

		redirectionUrl = "stock!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "stock.name", message = "库存名称不允许为空!") })
	@InputConfig(resultName = "error")
	public String update() {
		Stock stock_db = stockService.getStockByName(stock.getName());
		if ((null != stock_db) && !(id.equals(stock_db.getId())))
		{
			addActionError("已存在名称为“" + stock.getName() + "”的库存分类，不能修改为该名称！");
			return ERROR;
		}
		Stock stock_bak = stockService.load(id);
		if (stock_bak == null) {
			addActionError("查询数据失败!");
			return ERROR;
		}
		stock_bak.setModifyDate(new Date());
		stock_bak.setName(stock.getName());

		try {
			stock_bak.setResidue(this.parseUploadXlsFileToDB(importRateFile, stock_bak) + stock_bak.getResidue());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("导入数据失败!");
			return ERROR;
		}
		stockService.update(stock_bak);

		redirectionUrl = "stock!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() {
		stock = stockService.load(id);
		if (stock == null || stock.getResidue() == null || stock.getResidue() > 0) {
			addActionError("此库存分类下存在数据，无法删除!");
			return ERROR;
		}
		stockService.delete(id);
		redirectionUrl = "stock!list.action";
		return SUCCESS;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public List<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}

	public File getImportRateFile() {
		return importRateFile;
	}

	public void setImportRateFile(File importRateFile) {
		this.importRateFile = importRateFile;
	}

}