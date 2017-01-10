package com.bstek.bdf3.importer.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.importer.parser.CellPostParser;
import com.bstek.bdf3.importer.parser.CellPreParser;
import com.bstek.dorado.annotation.PropertyDef;

/**
 *@author Kevin.yang
 *@since 2015年8月22日
 */
@Entity
@Table(name = "BDF3_MAPPING_RULE")
public class MappingRule implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID_", length = 36)
	@PropertyDef(label = "ID")
	private String id;
	
	@PropertyDef(label = "名称")
	@Column(name = "NAME_", length = 255)
	private String name;
	
	@Column(name = "IMPORTER_SOLUTION_ID_", length = 64)
	private String importerSolutionId;
	
	@PropertyDef(label = "Excel列号")
	@Column(name = "EXCEL_COLUMN_")
	private int excelColumn;
	
	@PropertyDef(label = "忽略错误格式数据")
	@Column(name = "IGNORE_ERROR_FORMAT_DATA_")
	private boolean ignoreErrorFormatData;
	
	@PropertyDef(label = "实体属性")
	@Column(name = "PROPERTY_NAME_", length = 60)
	private String propertyName;
	
	@PropertyDef(label = "单元格后置解析器")
	@Column(name = "CELL_POST_PARSER_BEAN_", length = 255)
	private String cellPostParserBean = CellPostParser.DEFAULT;
	
	@PropertyDef(label = "单元格后置解析器参数")
	@Column(name = "CELL_POST_PARSER_PARAM_", length = 255)
	private String cellPostParserParam;
	
	@PropertyDef(label = "单元格前置解析器")
	@Column(name = "CELL_PREV_PARSER_BEAN_", length = 255)
	private String cellPreParserBean = CellPreParser.DEFAULT;
	
	@PropertyDef(label = "单元格前置解析器参数")
	@Column(name = "CELL_PREV_PARSER_PARAM_", length = 255)
	private String cellPreParserParam;
	
	@Transient
	private ImporterSolution importerSolution;
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "MAPPING_RULE_ID_", insertable = false, updatable = false)
	private List<Entry> entries;
	
	@Transient
	private Map<String, String> map;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImporterSolutionId() {
		return importerSolutionId;
	}

	public void setImporterSolutionId(String importerSolutionId) {
		this.importerSolutionId = importerSolutionId;
	}

	public int getExcelColumn() {
		return excelColumn;
	}

	public void setExcelColumn(int excelColumn) {
		this.excelColumn = excelColumn;
	}

	
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	
	public ImporterSolution getImporterSolution() {
		return importerSolution;
	}

	public void setImporterSolution(ImporterSolution importerSolution) {
		this.importerSolution = importerSolution;
	}

	public String getCellPostParserBean() {
		return cellPostParserBean;
	}

	public void setCellPostParserBean(String cellPostParserBean) {
		this.cellPostParserBean = cellPostParserBean;
	}

	public String getCellPostParserParam() {
		return cellPostParserParam;
	}

	public void setCellPostParserParam(String cellPostParserParam) {
		this.cellPostParserParam = cellPostParserParam;
	}

	public String getCellPreParserBean() {
		return cellPreParserBean;
	}

	public void setCellPreParserBean(String cellPreParserBean) {
		this.cellPreParserBean = cellPreParserBean;
	}

	public String getCellPreParserParam() {
		return cellPreParserParam;
	}

	public void setCellPreParserParam(String cellPreParserParam) {
		this.cellPreParserParam = cellPreParserParam;
	}

	public List<Entry> getEntries() {
		return entries;
	}
	
	

	public boolean isIgnoreErrorFormatData() {
		return ignoreErrorFormatData;
	}

	public void setIgnoreErrorFormatData(boolean ignoreErrorFormatData) {
		this.ignoreErrorFormatData = ignoreErrorFormatData;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
	
	public String getMappingValueIfNeed(String key) {
		String value = key;
		if(entries != null && !entries.isEmpty() && StringUtils.isNotEmpty(key)) {
			if (map == null) {
				map = new HashMap<>();
				for (Entry entry : entries) {
					map.put(entry.getKey(), entry.getValue());
				}
			} 
			value = map.get(key);
			if (value == null) {
				throw new RuntimeException("[" + key+ "] 不在枚举范围内。");
			}
		}
		
		return value;
	}

	
	

}
