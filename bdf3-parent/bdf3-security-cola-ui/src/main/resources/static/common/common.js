(function () {
    var top, getAjaxID, startedAjaxList, reloadTop, showMessage, $messageDom;
    reloadTop = function () {
        top = window;
        while (top !== top.parent){
            top = window.parent;
        }
        top.location.reload();
    };
    $(NProgress.done);
    $(document).ajaxError(function (event, jqXHR) {
        var message;
        if (jqXHR.status === 401) {
            reloadTop();
        } else if (jqXHR.status === 200) {
            message = jqXHR.responseText;
            if (message.indexOf("登录") !== -1) {
                reloadTop();
            }
        } else if (jqXHR.status === 0) {
            reloadTop();
        } else {
            message = jqXHR.responseJSON;
            if (message) {
                throw new cola.Exception(message);
            }
        }
    });
    getAjaxID = function (event) {
        var id, key, value;
        id = "";
        for (key in event) {
            value = event[key];
            if (key.indexOf("jQuery") === 0) {
                id = key;
                break;
            }
        }
        if (id) {
            if (!(parseInt(id.replace("jQuery", "")) > 0)) {
                id = "";
            }
        }
        return id;
    };
    showMessage = function(message, time, selector, position) {
        if (!$messageDom) {
            $messageDom = $("<div style='display: none;' class='ui success message'></div>").appendTo(selector || "body");
        }
        if (!selector) {
            $messageDom.appendTo("body");
            $messageDom.css({
                position: "absolute",
                top: "0px",
                left: "0px",
                width: "100%",
                margin: "0px",
                "border-radius": "0px",
                "text-align": "center"
            });
            if (position) {
                $messageDom.addClass(position);
            }
        } else {
            $messageDom.removeAttr("style");
            $messageDom.hide();
            $messageDom.appendTo(selector);
            $messageDom.addClass(position || "top attached");
        }
        $messageDom.text(message);
        $messageDom.animate({opacity: "show"}, "normal", "swing");
        setTimeout(function() {
            $messageDom.animate({opacity: "hide"}, "normal", "swing");
        }, time || 1500);
    };
    startedAjaxList = [];
    $(document).ajaxStart(function (event) {
        var id;
        id = getAjaxID(event);
        startedAjaxList.push(id);
        if (!NProgress.isStarted()) {
            return NProgress.start();
        }
    });
    $(document).ajaxComplete(function (event, request, settings) {
        var id, index, type, messages;
        id = getAjaxID(event);
        index = startedAjaxList.indexOf(id);
        if (index > -1) {
            startedAjaxList.splice(index, 1);
        }

        if (settings.successMessage) {
            showMessage(settings.successMessage, settings.messageTime, settings.messageTarget, settings.messagePosition);
        }
        type = settings.type.toUpperCase();
        if (type !== "GET") {
            messages = {
                POST: "添加成功",
                PUT: "修改成功",
                DELETE: "删除成功"
            };

            showMessage(messages[type], settings.messageTime, settings.messageTarget, settings.messagePosition);
        }
        if (startedAjaxList.length === 0) {
            return NProgress.done();
        }
    });

}).call(this);
