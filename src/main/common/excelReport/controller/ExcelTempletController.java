package main.common.excelReport.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import main.common.excelReport.model.ExcelColumn;
import main.common.excelReport.model.ExcelHead;
import main.common.excelReport.model.ExcelModel;
import main.common.excelReport.service.ExcelTempletService;
import main.common.springmvc.model.AjaxResult;
import main.common.util.ExcelUtil;

/**
 * @author momo
 * @time 2017年8月15日下午4:26:33
 */
@Controller
public class ExcelTempletController {
	
	@Autowired
	ExcelTempletService excelTempletService;
	
	private static final String INSERT = "01";
	private static final String UPDATE = "02";
	private static final String DELETE = "03";
	
	/**
	 * Excel 的插入,生成Excel 对应的表结构
	 */
	public void insertExcelExample(ExcelHead excelHead ,List<ExcelColumn> list) {
		//1.先进行判断这个Excel对应的数据库表在库中是否已经存在
		
		
		//2.判断列名是否有重复
		
		
		//3.进行表头记录与建表操作
		
	}
	
	/**
	 * Excel 的上传
	 */
	public AjaxResult uploadExcel(File file, ExcelHead excelHead,ExcelModel model) {
		AjaxResult result = new AjaxResult();
		boolean flag = true;
		String msg = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			
			// 根据ID查询表列名和excel映射名
			List<ExcelColumn> list = excelTempletService.selectExcelColumnList(excelHead.getId());
			
			Map<String, Object> map = new HashMap<String, Object>();
			// 把表列名和映射名放入map中
			for (ExcelColumn c : list) {
				map.put(c.getColName(), c.getColAlias());
			}
			
			// 解析excel文件 返回list map形式的数据
			List<Map<String, Object>> columns = ExcelUtil.excelImport(fis,model, map);
			if (columns == null || columns.size() == 0) {
				flag = false;
				msg = "上传失败,请确认Excel格式或Sheet页名称是否正确";
			} else {
				if (INSERT.equals(model.getType())) {
					// 插入excel数据到表中
					//excelTempletService.insertExcel(excelHead, columns, list,model);
				} else if (UPDATE.equals(model.getType())) {
					// 动态更新excel表中的数据
					//excelTempletService.updateExcel(excelHead, columns, list,model);
				} else if (DELETE.equals(model.getType())) {
					// 动态删除excel表中的数据
					//excelTempletService.deleteExcel(excelHead, columns, list,model);
				}
				
				flag = true;
				msg = "上传成功";
			}

		} catch (Exception e) {
			flag = false;
			msg = e.getCause().getCause().getMessage();
			e.printStackTrace();
		}
		//这里可以根据前段框架格式返回相应的数据JSON数据或者其他
		if(flag) {
			result.setCode(AjaxResult.codeDefault);
			result.setMsg(msg);
		}else{
			result.setCode(AjaxResult.code_error);
			result.setMsg(msg);
		}
		return result;
	}
	
}
