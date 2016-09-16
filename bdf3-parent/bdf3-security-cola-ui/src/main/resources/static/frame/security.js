(function () {
    var securityDecide;
    securityDecide = function() {
        if (window.contextPath === undefined) {
            window.contextPath = ".";
        }
        $.ajax({
            contentType: "application/json",
            type: "GET",
            url: window.contextPath + "/service/component/load-by-path"
        }).success(function(result) {
            var i, c, type, widget, do$Dom;
            do$Dom = function(selector) {
                $(selector).each(function() {
                    if ($(this).is("input") || $(this).is("button") || $(this).is("select") || $(this).is("textarea")) {
                        $(this).attr("disabled", true);
                    } else {
                        $(this).addClass("disabled");
                    }
                });
            };
            for(i = 0; i < result.length; i++) {
                c = result[i];
                if (!c.componentId) continue;
                if (c.authorized) {
                    if (c.componentType === "Read") {
                        if (c.componentId.indexOf("#") === 0) {
                            if (typeof cola === "function") {
                                widget = cola.widget(c.componentId.substring(1));
                                if (widget) {
                                    type =widget.constructor.CLASS_NAME;
                                    if (type=='button') {
                                        widget.set('disabled', true);
                                    } else if(type.indexOf("input") >= 0) {
                                        widget.set('readOnly', true);
                                    }
                                } else {
                                    do$Dom(c.componentId);
                                }
                            } else {
                                do$Dom(c.componentId);
                            }
                        } else {
                            do$Dom(c.componentId);
                        }
                    }
                } else {
                    $(c.componentId).css("display", "none");
                }
            }
        });

    };
    if (typeof cola === "function") {
        cola.ready(securityDecide);
    } else {
        $(securityDecide);
    }
}).call(this);
