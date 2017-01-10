package com.bstek.bdf3.importer.policy;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.importer.model.Cell;
import com.bstek.bdf3.importer.model.ImporterSolution;
import com.bstek.bdf3.importer.model.MappingRule;
import com.bstek.bdf3.importer.model.Record;

import net.sf.cglib.beans.BeanMap;

/**
 *@author Kevin.yang
 *@since 2015年8月22日
 */
public class Context {
	private int startRow = 1;

	private InputStream inpuStream;
	
	private String FileName;
	
	private long fileSize;
	
	private String importerSolutionId;
	
    private Cell currentCell;
    
    private Record currentRecord;
    
    private List<Record> records = new ArrayList<>(30);
    
    private ImporterSolution importerSolution;
    
    private MappingRule currentMappingRule;
    
    private Object currentEntity;
    
    private List<MappingRule> mappingRules;
    
    private Class<?> entityClass;
    
    private Object value;

	public InputStream getInpuStream() {
		return inpuStream;
	}

	public void setInpuStream(InputStream inpuStream) {
		this.inpuStream = inpuStream;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getImporterSolutionId() {
		return importerSolutionId;
	}

	public void setImporterSolutionId(String importerSolutionId) {
		this.importerSolutionId = importerSolutionId;
	}
	
	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}
	
	public void addCell(Cell cell) {
		if (currentRecord == null || !currentRecord.addCellIfNeed(cell)) {
			currentRecord = new Record();
			currentRecord.addCellIfNeed(cell);
			records.add(currentRecord);
		}
	}

	public Cell getCurrentCell() {
		return currentCell;
	}

	public void setCurrentCell(Cell currentCell) {
		this.currentCell = currentCell;
		addCell(currentCell);
	}

	public Record getCurrentRecord() {
		return currentRecord;
	}

	public void setCurrentRecord(Record currentRecord) {
		this.currentRecord = currentRecord;
	}

	public ImporterSolution getImporterSolution() {
		return importerSolution;
	}

	public void setImporterSolution(ImporterSolution importerSolution) {
		this.importerSolution = importerSolution;
	}

	public MappingRule getCurrentMappingRule() {
		return currentMappingRule;
	}

	public void setCurrentMappingRule(MappingRule currentMappingRule) {
		this.currentMappingRule = currentMappingRule;
	}

	public Object getCurrentEntity() {
		return currentEntity;
	}

	public void setCurrentEntity(Object currentEntity) {
		this.currentEntity = currentEntity;
	}

	public List<MappingRule> getMappingRules() {
		return mappingRules;
	}

	public void setMappingRules(List<MappingRule> mappingRules) {
		this.mappingRules = mappingRules;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getCurrentEntityId() {
		String idProperty = JpaUtil.getIdName(entityClass);
		BeanMap beanMap = BeanMap.create(currentEntity);
		return (T) beanMap.get(idProperty);
		
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	
	
	
	
}
