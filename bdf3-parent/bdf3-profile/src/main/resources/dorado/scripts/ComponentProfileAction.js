(function () {
    dorado.widget.ComponentProfileAction = $extend(dorado.widget.AjaxAction, {
		ATTRIBUTES:{
            component: {}
        }, 
        
        getAjaxOptions: function () {
			var jsonData = {
				action:"remote-service", 
				service:"componentProfileController#saveComponentProfile", 
				parameter:dorado.JSON.evaluate(this._parameter), 
				sysParameter:this._sysParameter ? this._sysParameter.toJSON() : undefined, 
				context:(this._view ? this._view.get("context") : null)
			};
			
			var parameter = {
				controlId: this.getControlId(),
				name: this.getName(),
				cols: this.getCols(),
				members: this.getMembers()
			};
			if (!jsonData.parameter){
				jsonData.parameter = {};
			}
			dorado.Object.apply(jsonData.parameter,parameter);
			
            return dorado.Object.apply({
	            	jsonData:jsonData, 
	            	timeout:this._timeout, 
	            	batchable:this._batchable
            	}, 
            	$setting["ajax.remoteServiceOptions"]
            );
        },
        
        getComponentId: function() {
        	return this._component.component;
        },
        
        getComponent: function() {
        	return this.getView().id(this.getComponentId());
        },
        
		getControlId: function() { 
			return this.getView().get("name") + "." + this.getComponentId();
		},
		
		getView: function() {
			return this._component.view;
		},
		
		getName: function() {
			return "";
		},
		
		getCols: function() {
			return "";
		},
		
		getMembers: function() {
			return [];
		},
		
		resetConfig:function() {
			var self = this;
			if(!self.resetConfigAction){
				self.resetConfigAction = new dorado.widget.AjaxAction({
					service: "componentProfileController#resetComponentProfile",
					parameter: {
						controlId: self.getControlId()
					}
				});
			}
			self.resetConfigAction.execute();
        }
    });
})();