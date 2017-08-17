package main.common.excelReport.dao;

import java.util.List;

import main.common.excelReport.model.ExcelColumn;
import main.common.excelReport.model.ExcelHead;

/**
 * @author momo
 * @time 2017年8月17日上午9:52:21
 * 
 */
public interface ExcelTempletDao {
	
	void insertExcelHead(ExcelHead model);
	
	void insertBatchExcelColumn(List<ExcelColumn> list);
	
	void executeSql(String sql);
	
}
