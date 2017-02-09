dorado.widget.SwfViewer = $extend(dorado.widget.Control, {
	$className : "dorado.widget.Swfviewer",
	ATTRIBUTES : {
		width : {
			defaultValue : 400
		},
		height : {
			defaultValue : 400
		},
		swfUrl : {

		},
		handlerName : {

		},
		parameter : {

		},
		version : {
			defaultValue : "9.0.0"
		},
		expressInstallSwfurl : {

		},
		flashvars : {

		},
		params : {

		},
		attributes : {

		},
		showType : {
			defaultValue : "complex"
		},
		printEnabled:{
			defaultValue:true
		}

	},

	createDom : function() {
		var dom = $DomUtils.xCreate({
			tagName : "div"
		});
		return dom;
	},
	refreshDom: function(dom) {
    	$invokeSuper.call(this, arguments);
    },
    doOnAttachToDocument : function() {
		var viewer = this;
		viewer.refreshSwf();
	},
	refreshSwf : function() {
		if(swfobject.hasFlashPlayerVersion("9.0.0")){
			this._embedSWF();
		}else{
			var message="<div>Either scripts and active content are not permitted to run or Adobe Flash Player version 9.0.0 or greater is not installed.</div>",
			 href="<a href='http://www.adobe.com/go/getflashplayer'><img src='http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash Player'/></a>";
			jQuery(this.getDom()).html(message+href);
		}
	},
	_embedSWF : function() {
		var viewer = this, url = "", id = jQuery(viewer.getDom()).attr('id'), defaultUrl = "dorado/bdf2/swfviewer/swfFile.do", expressInstallSwfurl = $url(">dorado/res/dorado/resources/flash/expressInstall.swf");
		url = viewer._swfUrl ? viewer._swfUrl : defaultUrl,values=[];
		if(viewer._handlerName){
			values.push("handler=" + encodeURIComponent(viewer._handlerName));
		}else if(defaultUrl==url){
			return;
		}
		if(viewer._parameter){
			var parameter=viewer._parameter;
			if (typeof parameter== "string") {
				values.push("parameter=" + encodeURIComponent(parameter));
			} else {
				for (var key in parameter) {
					if(parameter.hasOwnProperty(key)){
						if(parameter[key]===undefined){
							continue;
						}
						if(parameter[key]!=null){
							values.push(key+"=" +  encodeURIComponent(parameter[key]));
						}
					}
				}
			};
		}
		for(var i=0;i<values.length;i++){
			var v=values[i];
			if(url.indexOf("?")==-1){				
				url = url + "?"+v;
			}else{
				url = url + "&"+v;
			}
		}
		if (viewer._showType == "complex") {
			url = escape(url);
			var swfviewer = $url(">dorado/res/dorado/resources/flash/swfviewer.swf");
			var config = {
				swf : $url(url),
				PrintEnabled:viewer._printEnabled
			};
			swfobject.embedSWF(swfviewer, id, viewer._width, viewer._height, viewer._version, expressInstallSwfurl, config);
		} else {
			swfobject.embedSWF($url(url), id, viewer._width, viewer._height, viewer._version, viewer._expressInstallSwfurl || expressInstallSwfurl, viewer._flashvars || {}, viewer._params || {}, viewer._attributes || {});
		}
	}
});
