package main.common.excelReport.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import main.common.excelReport.dao.ExcelTempletDao;
import main.common.excelReport.model.ExcelColumn;
import main.common.excelReport.model.ExcelHead;
import main.common.util.CommonUtil;
import main.common.util.DateUtil;

/**
 * @author momo
 * @time 2017年8月15日下午4:32:52
 */
public class ExcelTempletServiceImpl implements ExcelTempletService{
	
	@Autowired
	ExcelTempletDao excelTempletDao;
	
	private final static String VARCHAR = "varchar-note";
	private final static String NUMBER = "number";
	private final static String DATE = "varchar-date";

	/**
	 * 通过前端在数据库中动态建表
	 */
	public void insertExcelExample(ExcelHead excelHead,List<ExcelColumn> listCol) {
		
		//1. 拼装建表sql
		StringBuffer sql = new StringBuffer("CREATE TABLE " + excelHead.getTableNameAlias() 
				+ "(id VARCHAR2(100) NOT NULL COMMENT '系统ID',");
		//2. 表注释
		String tableComment = "COMMENT = '+" + excelHead.getTableName() + "';";
		
		//3. 向数据库中插入表头信息
		excelHead.setId(CommonUtil.getUUID());
		excelHead.setCreateDate(DateUtil.dateToString(new Date(),DateUtil.FORMAT_yMdHms));
		excelHead.setUserId("momo");
		excelTempletDao.insertExcelHead(excelHead);
		String primaryKey = null;
		//4. 拼装列信息
		for(ExcelColumn col : listCol) {
			
			if(col.getIsKey() == null || col.getIsKey().equals("")){
				col.setIsKey("N");
			}else{
				col.setIsKey("Y");
			}
			
			if(col.getIsNull() == null || col.getIsNull().equals("")){
				col.setIsNull("Y");
			}else{
				col.setIsNull("N");
			}
			
			col.setId(CommonUtil.getUUID());
			col.setTableId(excelHead.getId());
			sql.append("'" + col.getColAlias() + "'");
			// 判断列的类型
			if (VARCHAR.equals(col.getType())) {
				// 文本型如果不设置长度，默认长度4000
				if (col.getLength() != null && !"".equals(col.getLength())) {
					sql.append(" VARCHAR2(" + col.getLength() + ")");
				} else {
					sql.append(" VARCHAR2(4000)");
				}
			} else if (NUMBER.equals(col.getType())) {// 数字暂时不支持大数据类型
				// 数值型如果整数位 则默认设置长度为11
				// 如果小数位为空 则不设置
				if (col.getLength() != null && !"".equals(col.getLength())) {
					sql.append(" INT(" + col.getLength() + ")");
				} else {
					sql.append(" INT(11)");
				}
			} else if (DATE.equals(col.getType())) {
				if (col.getLength() != null && !"".equals(col.getLength())) {
					sql.append(" varchar2(" + col.getLength() + ")");
				}else {
					sql.append(" VARCHAR2(20)");//如果不写  默认为20
					col.setLength("20");
				}
			} 
			// 判断列是否可为空
			if ("N".equals(col.getIsNull())) {
				sql.append(" NOT NULL");
			}
			if (col.getComment() != null && !col.getComment().equals("")) {
				sql.append("COMMENT '" + col.getComment() +"',");
			}
			if(col.getIsKey() != null && col.getIsKey().equals("Y")){
				primaryKey = "PRIMARY KEY ('" + col.getColAlias() + "')";
			}
		}
		sql.append( primaryKey + ") ENGINE = InnoDB " + tableComment);
		
		// 插入列信息
		excelTempletDao.insertBatchExcelColumn(listCol);
		
		excelTempletDao.executeSql(sql.toString());
	}

}
