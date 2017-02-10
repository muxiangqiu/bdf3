(function() {
	dorado.widget.Export2ReportAction = $extend(dorado.widget.AjaxAction, {
		$className : "dorado.widget.Export2ReportAction",
		ATTRIBUTES : {
			template : {
				setter : function(template) {
					if (template) {
						this._template = template;
					}
				}
			},
			
			interceptorName : {
				setter : function(interceptorName) {
					if (interceptorName) {
						this._interceptorName = interceptorName;
					}
				}
			},
			
			rowSpace : {
				defaultValue : 1
			},
			
			autoDownload : {
				defaultVaule : true
			},
			
			showTitle : {
				defaultValue : false
			},

			showPageNumber : {
				defaultValue : true
			},
			
			titleName : {
				setter : function(titleName) {
					if (titleName) {
						this._titleName = titleName;
					}
				}
			},
			
			titleBgColor : {
				defaultValue : "#FFFFFF"
			},
			
			titleFontColor : {
				defaultValue : "#000000"
			},
			
			titleFontSize : {
				defaultValue : 18
			},
			
			headerBgColor : {
				defaultValue : "#D8D8D8"
			},
			
			headerFontColor : {
				defaultValue : "#000000"
			},
			
			headerFontSize : {
				defaultValue : 10
			},
			
			dataBgColor : {
				defaultValue : "#FFFFFF"
			},
			
			dataFontColor : {
				defaultValue : "#000000"
			},
			
			dataFontSize : {
				defaultValue : 10
			},
			dataScope : {
				defaultValue : "currentPage"
			},
			
			maxSize : {
				defaultValue : "1000"
			},
			
			fileName : {
				setter : function(fileName) {
					if (fileName) {
						this._fileName = fileName;
					}
				}
			},
			
			extension : {
				defaultValue : "xls"
			}
		},
		EVENTS : {
			onGetExportData : {},
			onGetExportElement : {}
		},

		_generateReportParameter : function() {
			var action = this, parameter = new dorado.util.Map(), reportInfos = [], view = this._view, template = this._template;
			if (!template) {
				throw new dorado.Exception("template property is null!")
			}
			var titleInfos = {
				showTitle : action._showTitle,
				showBorder : true,
				showPageNumber : action._showPageNumber,
				title : action._titleName,
				fontColor : action._titleFontColor,
				fontSize : action._titleFontSize,
				bgColor : action._titleBgColor
			};
			var dataScope = action._dataScope || "currentPage";
			var maxSize = action._maxSize || 1000;
			var fileName = action._fileName || "report";
			var extension = action._extension || "xls";
			var rowSpace = action._rowSpace;
			var interceptorName = action._interceptorName;
			parameter.put("fileName", fileName);
			parameter.put("titleInfos", titleInfos);
			parameter.put("extension", extension);
			parameter.put("rowSpace", rowSpace);
			parameter.put("interceptorName", interceptorName);
			var templates = template.split(",");
			for (var i = 0; i < templates.length; i++) {
				var controlId = templates[i];
				var control = view.id(controlId);
				if (dorado.widget.DataGrid && control instanceof dorado.widget.DataGrid) {
					var grid = this._generateGridParameter(view, controlId, dataScope, maxSize);
					reportInfos.push({
						grid : grid,
						type : "grid"
					});
				} else if (dorado.widget.AutoForm && control instanceof dorado.widget.AutoForm) {
					var form = this._generateFormParameter(view, controlId);
					reportInfos.push({
						form : form,
						type : "form"
					});
				} else {
					throw new dorado.Exception(" don't support widget " + controlId);
				}
			}
			parameter.put("reportInfos", reportInfos);
			for (var item in action._parameter){
				parameter.put(item, action._parameter[item]);
			}
			return parameter;
		},

		_generateGridParameter : function(view, gridId, dataScope, maxSize) {
			var complexColumns = [], result = new dorado.util.Map(), resultData = [], resultList = new dorado.util.KeyedList();
			var grid = view.id(gridId);
			var columns = grid.get("columns");
			var ds = grid.get("dataSet");
			var dataType = ds.get("data").elementDataType;
			var dataPath = grid.get("dataPath");
			if (dataPath) {
				dataType = ds.getData(dataPath).elementDataType;
			}
			this._buildColumnsInfo(grid, columns, dataType, null, resultList, dataScope);
			this._calculateComplexEntityColumns(grid, columns, complexColumns);
			result.put("columnInfos", resultList.toArray());
			result.put("dataScope", dataScope);
			result.put("maxSize", maxSize);
			if (dorado.widget.DataTreeGrid) {
				if ( grid instanceof dorado.widget.DataTreeGrid) {
					var treeColumn = grid.get("treeColumn");
					result.put("treeColumn", treeColumn);
				}
			}
			if (dataScope == "currentPage") {
				var data = grid.get("itemModel").toArray();
				var eventArg = {
					id : gridId,
					data : data
				};
				this.fireEvent("onGetExportData", this, eventArg);
				data = eventArg.data;
				if (data) {
					if ( data instanceof Array) {
						for (var j = 0; j < data.length; j++) {
							var entity = data[j];
							if ( entity instanceof dorado.Entity) {
								var json = this._retrieveComplexObjectValue(complexColumns, entity, grid);
								resultData.push(json);
							}
						}
					} else if ( data instanceof dorado.EntityList) {
						for (var it = data.iterator(); it.hasNext(); ) {
							var entity = it.next();
							var json = this._retrieveComplexObjectValue(complexColumns, entity, grid);
							resultData.push(json);

						}
					} else if ( data instanceof dorado.Entity) {
						var json = this._retrieveComplexObjectValue(complexColumns, entity, grid);
						resultData.push(json);
					}
				}
				result.put("data", resultData);
			} else {
				var dataType, retriveResult = this._retriveReferenceParameter(ds, dataPath);
				if (retriveResult.isRef) {
					result.put("dataProviderParameter", retriveResult.parameter);
					result.put("pageSize", retriveResult.pageSize);
					result.put("dataProviderId", retriveResult.propertyDef.get("dataProvider.id"));
					dataType=retriveResult.propertyDef.get("dataProvider").dataType;
				} else {
					var dataProviderId = ds.get("dataProvider").id;
					if (dataProviderId.indexOf("v:") !== 0){
						dataProviderId = ds.get("dataProvider").name;
					}
					result.put("dataProviderId", dataProviderId);
					result.put("dataProviderParameter", ds.get("parameter"));
					result.put("pageSize", ds.get("pageSize") || 0);
					dataType = grid.getBindingData().dataType;
				}
				if(dataType){
					if (dataType instanceof dorado.DataType) dataType = dataType.get("id");
					else if (typeof dataType == "string") dataType = dataType;
					else dataType = dataType.id;
					result.put("resultDataType",dataType);
				}
				result.put("sysParameter", ds._sysParameter ? ds._sysParameter.toJSON() : undefined);
			}
			result.put("gridDataStyle", {
				bgColor : this._dataBgColor || "#FFFFFF",
				fontColor : this._dataFontColor || "#000000",
				fontSize : this._dataFontSize || 10,
				fontAlign : 1
			});
			return result;
		},
		_generateFormParameter : function(view, formId) {
			var action = this,config={},resultList=new dorado.util.KeyedArray();
			var form = view.id(formId);
			var cols = form.get("cols");
			var count = 2;
			if (cols) {
				count = cols.split(",").length;
			}
			config.columnCount = count;
			var keyedArray = form.get("elements");
			var ds = form.get("dataSet");
			var dataType = null;
			if (ds) {
				var dataPath=form.get("dataPath");
				if(dataPath){
					dataType=ds.getData(dataPath).dataType;
				}else{
					dataType = ds.getDataType();
				}
			}
			keyedArray.each(function(element) {
				if ( element instanceof dorado.widget.autoform.AutoFormElement && (
						element.get("editor") instanceof dorado.widget.AbstractEditor ||
						element.get("editor") instanceof dorado.widget.Label)) {
					var colSpan = 1;
					var rowSpan = 1;
					var layoutConstraint = element.get("layoutConstraint");
					if (layoutConstraint && layoutConstraint.colSpan) {
						colSpan = layoutConstraint.colSpan;
					}
					if (layoutConstraint && layoutConstraint.rowSpan) {
						rowSpan = layoutConstraint.rowSpan;
					}
					var label = element.get("label");
					var value = null;
					var editor = element.get("editor");
					if (editor instanceof dorado.widget.AbstractEditor) {
						value = element.get("value");
						if(value && editor instanceof dorado.widget.RadioGroup){
							var radioButtons=editor.get("radioButtons");
							radioButtons.each(function(radioButton){
								if(radioButton.get("value")===value.toString()){
									value=radioButton.get("text");
									return false;
								}
							});
						}
					} else if (editor instanceof dorado.widget.Label){
						var entity = editor.getBindingData(true);
						if (editor.get("property")){
							value = entity.getText(editor.get("property"));
						}
					}
					if (!label && dataType) {
						var propertyName = element.get("property");
						var propertyNames = propertyName.split(".");
						if (propertyNames.length == 1) {
							label = dataType.getPropertyDef(propertyName).get("label");
						} else {
							var propertyDef = action._retriveDataTypePropertyDefLevel(dataType, propertyName, 0);
							if (propertyDef) {
								label = propertyDef.get("label")
							}
						}
						if (value && value instanceof dorado.Entity) {
							var dt = value.dataType;
							if (dt) {
								var displayProperty = dt.get("defaultDisplayProperty");
								if (displayProperty) {
									var propertyValue = value.get(displayProperty);
									value = action._retriveDataTypePropertyMappingValue(dt, displayProperty, propertyValue);
								}
							}
						}
					}
					var data = {
						label : label,
						value : value,
						colSpan : colSpan,
						rowSpan : rowSpan
					};
					resultList.append(data);
				}
			});
			var data = resultList.toArray();
			var eventArg = {
				id : formId,
				data : data
			};
			this.fireEvent("onGetExportData", this, eventArg);
			data = eventArg.data;
			config.datas = data;
			config.formStyle = {
				labelAlign : 2,
				dataAlign : 0,
				dataStyle : 0
			};
			return config;
		},
		_retriveReferenceParameter : function(dataSet, dataPath) {
			var supported = false, propertyDef = null, parameter = {}, pageSize = 0;
			if (dataPath && dataPath.match(/\.[\#\w]*$/)) {
				var i = dataPath.lastIndexOf('.');
				var parentDataPath = dataPath.substring(0, i), subProperty = dataPath.substring(i + 1);
				if (subProperty.charAt(0) == '#')
					subProperty = subProperty.substring(1);
				var parentEntity = dataSet.getData(parentDataPath);
				if (parentEntity && parentEntity instanceof dorado.Entity) {
					var parentDataType = parentEntity.dataType;
					if (parentDataType && parentDataType instanceof dorado.EntityDataType) {
						propertyDef = parentDataType.getPropertyDef(subProperty);
						if (propertyDef && propertyDef instanceof dorado.Reference) {
							dorado.$this = parentEntity;
							parameter = dorado.JSON.evaluate(propertyDef.get("parameter"));
							pageSize = propertyDef.get("pageSize");
							supported = true;
						}
					}
				}
			}
			if (!supported && dataPath) {
				throw new dorado.Exception("dont't support datapath " + dataPath);
			}
			return {
				isRef : supported,
				propertyDef : propertyDef,
				parameter : parameter,
				pageSize : pageSize || 0
			};
		},
		_retrieveDataTreeGridBindingConfigs : function(grid) {
			var configs = [];
			var bindingConfigs = grid.get("bindingConfigs");
			this._recurBindingConfigs(bindingConfigs, configs);
			return configs;
		},
		_recurBindingConfigs : function(bindingConfigs, result) {
			bindingConfigs.each(function(bindingConfig) {
				if (bindingConfig.childrenProperty) {
					result.push(bindingConfig.childrenProperty);
				}
				if ( bindingConfig instanceof Array) {
					this._recurBindingConfigs(bindingConfig.get("bindingConfigs"), result);
				}
			});
		},
		_retrieveComplexObjectValue : function(complexColumns, entity, grid) {
			var json = entity.toJSON();
			var mapSub = new dorado.util.Map();
			for (var propertyName in json) {
				try {// 忽略不存在的属性
					var dataSub = entity.get(propertyName);
				} catch(e){
					dorado.Exception.removeException(e);
					continue;
				}
				mapSub.put(propertyName, dataSub);
				if (dataSub && dataSub instanceof dorado.Entity) {
					complexColumns.each(function(complexColumn) {
						mapSub.put(complexColumn, entity.get(complexColumn));
					});
					var dt = dataSub.dataType;
					if (dt) {
						var displayProperty = dt.get("defaultDisplayProperty");
						if (displayProperty) {
							mapSub.put(propertyName, dataSub.get(displayProperty));
							iscomplex = true;
						}
					}

				}
			}
			return mapSub.toJSON();
		},

		_generateHeaderLevel : function(col, level) {
			var parentColumn = col.get("parent");
			if ( parentColumn instanceof dorado.widget.DataGrid) {
				return level;
			}
			if ( parentColumn instanceof dorado.widget.grid.ColumnGroup) {
				level++;
				return this._generateHeaderLevel(parentColumn, level);
			}
			return level;
		},
		
		_exportElement: function (control, col){
			if ( col instanceof dorado.widget.grid.IndicatorColumn || 
					col instanceof dorado.widget.grid.RowNumColumn || 
					col instanceof dorado.widget.grid.RowSelectorColumn || 
					!col.get("visible")
				) {
				return false;
			} else {
				var eventArg = {
					control : control,
					element : col,
					processDefault: true
				};
				this.fireEvent("onGetExportElement", this, eventArg);
				return eventArg.processDefault;
			}
		},
		
		_calculateComplexEntityColumns : function(grid, childrenColumns, complexColumns) {
			var columns = childrenColumns.toArray();
			for (var i = 0; i < columns.length; i++) {
				var col = columns[i];
				if (!this._exportElement(grid, col)) {
					continue;
				}
				if ( col instanceof dorado.widget.grid.ColumnGroup) {
					if (col.get("columns").toArray().length > 0) {
						this._calculateComplexEntityColumns(grid, col.get("columns"), complexColumns);
					}
				} else {
					var property = col.get("property");
					if (property) {
						var propertyArray = property.split(".");
						if (propertyArray.length > 1) {
							complexColumns.push(property);
						}
					}
				}
			}
		},

		_buildColumnsInfo : function(grid, childrenColumns, dataType, parentMap, resultList, dataScope) {
			var columns = childrenColumns.toArray();
			for (var i = 0; i < columns.length; i++) {
				var col = columns[i];
				if (!this._exportElement(grid, col)) {
					continue;
				}
				var columnName;
				if ( col instanceof dorado.widget.grid.ColumnGroup) {
					columnName = col.get("name");
				} else {
					columnName = col.get("property");
				}
				var columnCaption = col.get("caption");
				var width = col._realWidth;
				if (width == undefined) {
					width = 0;
				}
				var level = this._generateHeaderLevel(col, 1);
				var map = new dorado.util.Map();
				map.put("columnName", columnName);
				map.put("level", level);
				map.put("label", columnCaption);
				map.put("width", width);
				map.put("bgColor", this._headerBgColor);
				map.put("fontColor", this._headerFontColor);
				map.put("fontSize", this._headerFontSize);
				map.put("align", 1);
				var dataAlign = 0;
				if (col._align == "center") {
					dataAlign = 1;
				} else if (col._align == "right") {
					dataAlign = 2;
				}
				map.put("dataAlign", dataAlign);

				var propertyDef = this._retriveGridColumnPropertyDef(columnName, dataType);
				if (propertyDef) {
					var _dataType = propertyDef.get("dataType");
					if (_dataType) {
						map.put("dataTypeName", _dataType.get("name"));
					}
					map.put("displayFormat", propertyDef.get("displayFormat"));
				}

				if ( col instanceof dorado.widget.grid.DataColumn) {
					if (col.get("displayFormat")) {
						map.put("displayFormat", col.get("displayFormat"));
					}
				}
				var mapping = this._retriveGridColumnMapping(propertyDef, columnName, dataType);
				if (mapping) {
					map.put(columnName + "_$mapping", mapping);
				}
				if (dataScope && (dataScope == "serverAll")) {
					var displayProperty = this._retriveEntityDisplayProperty(columnName, dataType);
					if (displayProperty) {
						map.put(columnName + "_$displayProperty", displayProperty);
					}
				}
				if ( col instanceof dorado.widget.grid.ColumnGroup) {
					if (col.get("columns").toArray().length > 0) {
						this._buildColumnsInfo(grid, col.get("columns"), dataType, map, new dorado.util.KeyedList(), dataScope);
					}
				}
				resultList.insert(map.toJSON());
			}
			if (parentMap != null) {
				parentMap.put("children", resultList.toArray());
			}
		},

		_retriveEntityDisplayProperty : function(columnName, dataType) {
			var displayProperty = null;
			if (!( dataType instanceof dorado.EntityDataType)) {
				return displayProperty;
			}
			var propertyDefs = dataType.get("propertyDefs");
			if ( propertyDefs instanceof dorado.util.KeyedArray) {
				propertyDefs.each(function(propertyDef) {
					if (propertyDef.get("name") == columnName) {
						var dt = propertyDef.get("dataType");
						if (dt && ( dt instanceof dorado.EntityDataType)) {
							displayProperty = dt.get("defaultDisplayProperty");
						}
						return false;
					}
				});
			}
			return displayProperty;
		},

		_retriveGridColumnMapping : function(propertyDef, columnName, dataType) {
			var mapping = null;
			if (!( dataType instanceof dorado.EntityDataType)) {
				return mapping;
			}
			if (propertyDef) {
				mapping = propertyDef.get("mapping");
				var subDataType = propertyDef.get("dataType");
				if ( subDataType instanceof dorado.EntityDataType) {
					if (columnName.split(".").length == 1) {
						var displayProperty = subDataType.get("defaultDisplayProperty");
						if (displayProperty) {
							var subPropertyDef = this._retriveGridColumnPropertyDef(displayProperty, subDataType);
							if (subPropertyDef) {
								mapping = subPropertyDef.get("mapping");
							}
						}
					} else {
						var columnNames = columnName.split(".");
						var property = columnNames[1];
						var subPropertyDef = this._retriveGridColumnPropertyDef(property, subDataType);
						return this._retriveGridColumnMapping(subPropertyDef, property, subDataType);
					}
				}
			}
			return mapping;
		},
		_retriveGridColumnPropertyDef : function(dataColumn, dataType) {
			function getSubPropertyDef(propertyPath, parentDataType){
				if (parentDataType && propertyPath && parentDataType instanceof dorado.EntityDataType) {
				if (propertyPath.split(".").length > 1){
					var index = propertyPath.indexOf(".");
					var property = propertyPath.substr(0, index);
					var subPropertyPath = propertyPath.substr(index+1);
					var subPropertyDef = parentDataType.getPropertyDef(property);
					return getSubPropertyDef(subPropertyPath, subPropertyDef.get("dataType"));
				}else{
					return parentDataType.getPropertyDef(propertyPath);
				}
				}else{
					return null;
				}
			}
			return getSubPropertyDef(dataColumn, dataType);
			/*
			var _propertyDef = null;
			if (dataType && dataColumn && dataType instanceof dorado.EntityDataType) {
				var propertyDefs = dataType.get("propertyDefs");
				if ( propertyDefs instanceof dorado.util.KeyedArray) {
					propertyDefs.each(function(propertyDef) {
						if (propertyDef.get("name") == dataColumn || propertyDef.get("name") == dataColumn.split(".")[0]) {
							_propertyDef = propertyDef;
							return false;
						}
					});
				}
			}

			return _propertyDef;
			*/
		},

		_retriveDataTypePropertyDefLevel : function(dataType, propertyName, level) {
			var action = this, length = propertyName.split(".").length;
			var currentProperty = propertyName.split(".")[level];
			var propertyDef = action._retriveDataTypePropertyDef(dataType, currentProperty);
			if (propertyDef) {
				if (level < length - 1) {
					level++;
					var dt = propertyDef.get("dataType");
					propertyDef = action._retriveDataTypePropertyDefLevel(dt, propertyName, level);
				}
			}
			return propertyDef;
		},
		_retriveDataTypePropertyDef : function(dataType, propertyName) {
			var _propertyDef;
			if ( dataType instanceof dorado.EntityDataType) {
				dataType.get("propertyDefs").each(function(propertyDef) {
					if (propertyDef.get("name") == propertyName) {
						_propertyDef = propertyDef;
						return false;
					}
				});
			}
			return _propertyDef;
		},

		_retriveDataTypePropertyMappingValue : function(dataType, propertyName, propertyValue) {
			var value = propertyValue;
			dataType.get("propertyDefs").each(function(propertyDef) {
				var go = true;
				var name = propertyDef.get("name");
				if (name == propertyName) {
					var mappings = propertyDef.get("mapping");
					if (mappings) {
						for (var i = 0; i < mappings.length; i++) {
							var mapping = mappings[i];
							var mappingKey = mapping.key;
							if (mappingKey == propertyValue) {
								value = mapping.value;
								go = false;
								break;
							}
						}
					}
				}
				return go;
			});
			return value;
		},
		getAjaxOptions : function() {
			var parameter = this._generateReportParameter();
			var jsonData = {
				action : "remote-service",
				service : "bdf3.Export2ReportController#generateReportFile",
				parameter : dorado.JSON.evaluate(parameter),
				context : (this._view ? this._view.get("context") : null)
			};
			return dorado.Object.apply({
				jsonData : jsonData,
				timeout : this._timeout,
				batchable : this._batchable
			}, $setting["ajax.remoteServiceOptions"]);
		},
		doDowloadReport : function(data) {
			var id = data.id;
			var name = data.name;
			if (id && name) {
				var url = "dorado/bdf3/export/doDownloadFile?id=" + encodeURIComponent(id) + "&name=" + encodeURIComponent(name);
				window.location.href = url;
			}
		},
		doExecuteSync : function() {
			var ajaxOptions = this.getAjaxOptions(), ajax = null;
			if (dorado.util.AjaxEngine.getInstance) {
				ajax = dorado.util.AjaxEngine.getInstance(ajaxOptions);
			} else {
				ajax = dorado.Toolkits.getAjax(ajaxOptions);
			}
			var result = ajax.requestSync(ajaxOptions);
			if (result.success) {
				var result = result.getJsonData(),data;
				if (result && typeof result == "object") {
					data = result.data;
				}
				if (this._autoDownload != false) {
					this.doDowloadReport(data);
				}
				return data;
			} else {
				throw result.exception;
			}
		},
		doExecuteAsync : function(callback) {
			var ajaxOptions = this.getAjaxOptions(), ajax = null;
			if (dorado.util.AjaxEngine.getInstance) {
				ajax = dorado.util.AjaxEngine.getInstance(ajaxOptions);
			} else {
				ajax = dorado.Toolkits.getAjax(ajaxOptions);
			}
			ajax.request(ajaxOptions, {
				scope : this,
				callback : function(success, result) {
					if (success) {
						var data=result.getJsonData().data;
						if (this._autoDownload != false) {
							this.doDowloadReport(data);
						}
						$callback(callback, true, data);
					} else
						$callback(callback, false, result.exception);
				}
			});
		}
	});
})();
