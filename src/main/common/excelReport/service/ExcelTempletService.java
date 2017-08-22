package main.common.excelReport.service;

import java.util.List;

import main.common.excelReport.model.ExcelColumn;
import main.common.excelReport.model.ExcelHead;

/**
 * @author momo
 * @time 2017年8月15日下午4:32:38
 */
public interface ExcelTempletService {
	
	void insertExcelExample(ExcelHead excelHead, List<ExcelColumn> listCol);
	
	List<ExcelColumn> selectExcelColumnList(String id);
}
