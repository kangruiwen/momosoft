package main.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import main.common.excelReport.model.ExcelModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * @author momo
 * @time 2017年8月17日上午8:52:17
 * Excel 工具类
 */

public class ExcelUtil {
	
	/**
	 * excel导入
	 * @param c 导入Excel对应的model类
	 * @param file 导入的文件
	 * @param map 当前导入模版类型的表头名称映射
	 * @return
	 */
	public static List<Map<String, Object>> excelImport(InputStream is, ExcelModel model, Map<String, Object> map) {
		// 根据excel类型获取映射的Map
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			// 获取Excel对象 2003 2007均可
			Workbook workbook = WorkbookFactory.create(is);
			// 读取第一章表格内容
			Sheet sheet = workbook.getSheet(model.getSheet());
			if (sheet != null) {
				// 根据传入的开始数和结束符遍历Excel
				Integer startNum = model.getStartNum();
				Integer stopNum = model.getStopNum();
				if (model.getIs() && startNum > 0) {
					startNum = startNum - 1;
				}
				for (int i = startNum <= 0 ? startNum + 1 : startNum; i <= stopNum - 1; i++) {
					if (sheet.getLastRowNum() < i) {
						break;
					}
					Map<String, Object> columns = new HashMap<String, Object>();
					// 获取第一行的数据
					Row head = sheet.getRow(startNum == 0 ? startNum : startNum - 1);
					// 获取每行数据
					Row row = sheet.getRow(i);
					// 循环一行的每列数据
					for (int j = head.getFirstCellNum(); j <= head.getPhysicalNumberOfCells(); j++) {
						// 判断Excel表头的映射
						String columnName = (String) map.get(head.getCell(j) == null ? "" : head.getCell(j).toString());
						// 如果映射不存在跳出当前循环 进入下一次循环s
						if (columnName == null) {
							continue;
						}
						Cell cell = row.getCell(j);
						String value = isCellType(cell);
						columns.put(columnName, value);
						// 获取值
					}
					if (columns != null && columns.size() > 0) {
						resultList.add(columns);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * 导出excel
	 * @param list 表头映射集合
	 * @param List<Map> 表体值
	 * @param outputStream
	 * @param ExcelName
	 * @throws Exception
	 */
	public static void exportExcel(List<String> list,List<Map<String, Object>> maps,
			ServletOutputStream outputStream,String ExcelName) throws Exception {
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(ExcelName);
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);//样式设计
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		XSSFCell cell = null;

		// 构建表体数据 如果表体数据为空 则直接导出表头
		if (maps != null && maps.size() > 0) {
			for (int j = 0; j < maps.size(); j++) {
				Map<String,Object> map = maps.get(j);
				XSSFRow bodyRow = sheet.createRow(j + 1);
				// 循环属性集合
				int m = 0;
				for (int i = 0; i < list.size(); i++) {
					// 获取映射的表头
					String excelname = list.get(i);
					if ("null".equals(excelname)) {
						continue;
					}
					// 输出表头
					if (j == 0) {
						cell = headRow.createCell(m);
						cell.setCellStyle(headStyle);
						cell.setCellValue(excelname);
					}
					// 输出表体
					cell = bodyRow.createCell(m);
					cell.setCellStyle(bodyStyle);
					if (map.get(excelname.toUpperCase()) == null || "".equals(map.get(excelname.toUpperCase()))) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(map.get(excelname.toUpperCase()).toString());
					}
					m++;
				}
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				// 获取映射的表头
				String excelname = list.get(i);
				if ("null".equals(excelname)) {
					continue;
				}
				// 输出表头
				cell = headRow.createCell(i);
				cell.setCellStyle(headStyle);
				cell.setCellValue(excelname);
			}
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 判断读取的值类型
	 * @param cell
	 * @return
	 */
	private static String isCellType(Cell cell) {
		String str = "";
		if (null != cell) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
					SimpleDateFormat sdf = null;
					if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
						sdf = new SimpleDateFormat("HH:mm");
					} else {// 日期
						sdf = new SimpleDateFormat("yyyy-MM-dd");
					}
					Date date = cell.getDateCellValue();
					str = sdf.format(date);
				} else {
					str = new DecimalFormat("#.#########").format(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				str = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				str = cell.getBooleanCellValue() + "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				str = cell.getCellFormula() + "";
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				break;
			default:
				break;
			}
		} else {
			System.out.print("-   ");
		}
		return str;
	}
}
