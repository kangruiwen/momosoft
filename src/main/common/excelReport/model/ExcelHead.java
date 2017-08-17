package main.common.excelReport.model;
/**
 * @author momo
 * @time 2017年8月17日上午9:07:25
 * 功能：Excel对应的表信息
 * 对应数据库表：C_ExcelHead
 */
public class ExcelHead {
	
	private String id;// 表主键 
	private String tableName;//表中文名--对应sheet名称
	private String tableNameAlias;//表别名，既数据库表名
	private String userId;//创建表用户Id
	private String createDate;//创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableNameAlias() {
		return tableNameAlias;
	}

	public void setTableNameAlias(String tableNameAlias) {
		this.tableNameAlias = tableNameAlias;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
