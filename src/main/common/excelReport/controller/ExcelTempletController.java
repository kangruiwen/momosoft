package main.common.excelReport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import main.common.excelReport.model.ExcelColumn;
import main.common.excelReport.model.ExcelHead;
import main.common.excelReport.service.ExcelTempletService;

/**
 * @author momo
 * @time 2017年8月15日下午4:26:33
 */
@Controller
public class ExcelTempletController {
	
	@Autowired
	ExcelTempletService excelTempletService;
	
	
	/**
	 * Excel 的插入
	 */
	public void insertExcelExample(ExcelHead excelHead ,List<ExcelColumn> list) {
		//1.先进行判断这个Excel对应的数据库表在库中是否已经存在
		
		
		//2.判断列名是否有重复
		
		
		//3.进行表头记录与建表操作
		
		
	}
}
