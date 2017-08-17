package main.common.excelReport.service;

import java.util.List;

import main.common.excelReport.model.ExcelColumn;
import main.common.excelReport.model.ExcelHead;

/**
 * @company 浙江鸿程计算机系统有限公司
 * @author kangrw
 * @time 2017年8月15日下午4:32:38
 */
public interface ExcelTempletService {
	
	void insertExcelExample(ExcelHead excelHead, List<ExcelColumn> listCol);
}
