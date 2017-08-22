package main.common.excelReport.model;
/**
 * @author momo
 * @time 2017年8月22日上午9:54:30
 */
public class ExcelModel {
	
	
	private String type;// 模版类型
	private Integer startNum;// 起始条数
	private Integer stopNum;// 结束条数
	private String sheet;// excel工作页名称
	private String id;// 各Excel插入表时返回的Id
	private Boolean is = false;// 是否特殊处理
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getStartNum() {
		return startNum;
	}
	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}
	public Integer getStopNum() {
		return stopNum;
	}
	public void setStopNum(Integer stopNum) {
		this.stopNum = stopNum;
	}
	public String getSheet() {
		return sheet;
	}
	public void setSheet(String sheet) {
		this.sheet = sheet;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getIs() {
		return is;
	}
	public void setIs(Boolean is) {
		this.is = is;
	}
}
