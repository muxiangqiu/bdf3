(function() {
	dorado.widget.AutoFormProfileAction = $extend(
			dorado.widget.ComponentProfileAction,
			{
				$className : "dorado.widget.AutoFormProfileAction",

				ATTRIBUTES : {
					showLabelConfig : {
						defaultValue : true
					},

					showColSpanConfig : {
						defaultValue : true
					},

					showRowSpanConfig : {
						defaultValue : true
					},

					showVisibleConfig : {
						defaultValue : true
					},

					showHideModeConfig : {
						defaultValue : true
					},

					autoForm : {}
				},

				getComponentId : function() {
					return this._autoForm.component;
				},

				getView : function() {
					return this._autoForm.view;
				},

				getCols : function() {
					return this.autoformConfigForm.get("entity.cols");
				},

				getMembers : function() {
					return this
							.buildAutoFormElementConfigs(this.autoformDataGrid
									.get("items"));
				},

				execute : function(callback) {
					var save = true;
					var component = this.getComponent();
					if (dorado.widget.AutoForm
							&& component instanceof dorado.widget.AutoForm) {
						if (!this.isAutoFormConfigReady) {
							this.configAutoForm(this.getComponent());
							save = false;
						}
					}
					if (save) {
						$invokeSuper.call(this, callback);
					}
				},

				configAutoForm : function(component) {
					var processDialog = new dorado.widget.Dialog({
						width : 600,
						height : 420,
						center : true,
						closeAction : "close",
						status : "hidden",
						maximizeable : true,
						modal : true,
						caption : "表单个性化配置",
						modal : true,
						center : true,
						status : "hidden",
						buttonAlign : "center",
						resizable : true,
						view : component.get("view"),
						buttons : this.createDialogButtons()
					});
					this.dialog = processDialog;
					this.autoformDataGrid = this.createMemberConfigGrid(this
							.parseAutoForm(component));
					var caller = this;
					processDialog.addChild(this.createFormConfig(component));
					processDialog.addChild(this.autoformDataGrid);
					processDialog.addListener("onHide", function() {
						caller.isAutoFormConfigReady = false;
					});
					processDialog.addListener("onShow", function() {
						caller.isAutoFormConfigReady = true;
					});
					processDialog.show();
				},

				createFormConfig : function(component) {
					var cols = component.get("cols");
					if (!cols) {
						cols = "*,*";
					}
					this.autoformConfigForm = new dorado.widget.AutoForm({
						elements : [ {
							"visible" : true,
							"label" : "列布局",
							"property" : "cols",
							"name" : "cols",
							"meta1" : "display"
						} ]
					});

					this.autoformConfigForm.set("entity", {
						cols : cols
					});
					return this.autoformConfigForm;
				},

				createDialogButtons : function() {
					var buttons = new Array();
					var caller = this;

					buttons.push(new dorado.widget.Button({
						caption : "保存",
						listener : {
							onClick : function(self, arg) {
								caller.execute();
							}
						}
					}));

					buttons.push(new dorado.widget.Button({
						caption : "取消",
						listener : {
							onClick : function(self, arg) {
								caller.dialog.hide();
							}
						}
					}));

					buttons.push(new dorado.widget.Button({
						caption : "重置",
						listener : {
							onClick : function(self, arg) {
								caller.resetConfig();
							}
						}
					}));

					return buttons;
				},

				resetAutoFormConfig : function() {
					this.resetAutoFormConfigAction = new dorado.widget.AjaxAction({
						service : "componentProfileController#resetComponentProfile",
						parameter : {
							componentId : this._autoform.component
						}
					});
				},

				buildAutoFormElementConfigs : function(items) {
					var updateDatas = new Array();
					for (var i = 0; i < items.length; i++) {
						if (items[i].colSpan < 1 || items[i].rowSpan < 1) {
							dorado.MessageBox.alert("跨行和跨列的值不能小于1");
							return;
						}
						updateDatas.push({
							order : i,
							controlName : items[i].name,
							caption : items[i].label,
							colSpan : parseInt(items[i].colSpan),
							rowSpan : parseInt(items[i].rowSpan),
							visible : items[i].visible
						});
					}
					return updateDatas;
				},

				createMemberConfigGrid : function(datas) {
					var cellRender = $extend(
							dorado.widget.grid.SubControlCellRenderer, {
								createSubControl : function(arg) {
									var editor = new dorado.widget.RadioGroup({
										radioButtons : [ {
											value : "visibility",
											text : "占位"
										}, {
											value : "display",
											text : "隐藏"
										} ]
									});
									editor.set("width", "100%");
									return editor;
								},
								refreshSubControl : function(editor, arg) {
									editor.set("value", "visibility");
								}
							});

					var grid = new dorado.widget.Grid({
						draggable : true,
						droppable : true,
						dragMode : "item",
						view : this.view,
						dropMode : "onOrInsertItems",
						dragTags : "form",
						droppableTags : "form",
						layoutConstraint : {
							type : "center"
						},
						columns : [ {
							$type : "RowNum",
							name : "rowNum",
						}, {
							name : "label",
							property : "label",
							caption : "标题",
							visible : this._showLabelConfig
						}, {
							name : "colSpan",
							property : "colSpan",
							dataType : "int",
							width : 60,
							caption : "跨列",
							visible : this._showColSpanConfig
						}, {
							name : "rowSpan",
							property : "rowSpan",
							dataType : "int",
							width : 60,
							caption : "跨行",
							visible : this.showRowSpanConfig
						}, {
							name : "visible",
							property : "visible",
							dataType : "boolean",
							width : 60,
							caption : "可见",
							visible : this._showVisibleConfig
						}, {
							name : "name",
							property : "name",
							caption : "名称",
							readOnly : true
						}]
					});
					grid.set("items", datas);
					return grid;
				},

				parseAutoForm : function(autoForm) {
					var results = new Array();
					var elements = autoForm.get("elements");
					var element = null;

					var label = null;
					var dataType = null;
					if (autoForm.get("dataSet")) {
						dataType = autoForm.get("dataSet").get("dataType");
					} else if (autoForm.get("dataType")) {
						dataType = autoForm.get("dataType");
					}
					var dataTypes = null;
					var propertyDef = null;
					if (dataType) {
						var propertyDefs = dataType.get("propertyDefs");
						dataTypes = new dorado.util.Map();
						for (var i = 0; i < propertyDefs.size; i++) {
							dataTypes.put(propertyDefs.get(i).get("name"),
									propertyDefs.get(i));
						}
					}
					for (var i = 0; i < elements.size; i++) {
						var colSpan = 1;
						var rowSpan = 1;

						element = elements.get(i);

						var layoutConstraint = element.get("layoutConstraint");
						if (layoutConstraint) {
							colSpan = layoutConstraint.colSpan;
							rowSpan = layoutConstraint.rowSpan;
						}
						label = element.get("label");
						if (isNaN(colSpan)) {
							colSpan = 1;
						}
						if (isNaN(rowSpan)) {
							rowSpan = 1;
						}
						if (!label) {
							if (element.get("property")) {
								propertyDef = dataTypes.get(element
										.get("property"));
								if (propertyDef) {
									label = propertyDef.get("label");
								} else {
									label = "";
								}
							} else {
								label = "";
							}
						}
						results.push({
							name : element.get("name"),
							label : label,
							colSpan : colSpan,
							rowSpan : rowSpan,
							visible : element.get("visible")
						});
					}

					return results;
				}
			});
})();
