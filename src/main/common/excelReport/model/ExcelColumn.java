package main.common.excelReport.model;
/**
 * @author momo
 * @time 2017年8月15日下午4:29:30
 * 作为Excel的列
 * 对应数据库表：C_ExcelColumn
 */
public class ExcelColumn {
	
	private String id;//系统Id -- 不自增，系统生成UUID
	private String tableId; //对应的表Id
	private String colName;//列中文名，既Excel中名字
	private String colAlias;//Excel列明在数据库中的映射名
	private String type;//类型
	private String length;// 字段长度
	private String isKey;//是否为主键 Y = 是， N = 否
	private String isNull;//是否可以为空  Y=可以为空，默认；  N=不可为空
	private String comment;//注释
	
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getColAlias() {
		return colAlias;
	}
	public void setColAlias(String colAlias) {
		this.colAlias = colAlias;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsKey() {
		return isKey;
	}
	public void setIsKey(String isKey) {
		this.isKey = isKey;
	}
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
}
