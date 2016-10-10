(function () {
    cola(function (model) {

        var id = window.location.href.substring(window.location.href.lastIndexOf("/") + 1);

        var service = {
            getChat: "../api/chat/" + id,
            add: "../api/message/add"
        };

        model.describe("chat", {
            provider: {
                url: service.getChat,
                success: function() {
                    model.set("message", {
                        additional: model.get("chat.sender")
                    });
                    $("#main").animate({
                        scrollTop: $("#main > .ui.comments").height()
                    }, "fast");
                }

            }
        });

        model.set("message", $.cookie('message'));
        if (!model.get("message")) {
            $("#send").addClass("disabled");
        } else {
            $("#txtMessage").text(model.get("message"));
        }


        model.action({
            openListPage: function() {
                if (typeof window.parent.expandAndOpenPage === "function") {
                    window.parent.expandAndOpenPage("message");
                    window.parent.refreshPage()
                } else {
                    window.history.back();
                }
            },
            send: function () {
                var options, chat, data;
                data = {
                    sender: model.get("chat.receiver"),
                    additional: model.get("chat.sender"),
                    content: tinymce.get("txtMessage").getContent(),
                    createdAt: new Date()

                };

                options = {
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    type: "POST",
                    url: service.add
                };
                $.ajax(options).done(function () {
                    $.cookie('message', "");
                    tinymce.get("txtMessage").setContent("");
                    model.get("chat.additional.messages").insert(data);
                    $("#main").animate({
                        scrollTop: $("#main > .ui.comments").height()
                    }, "fast");
                });
            },
            parseTime: function (time) {
                var result;
                var minute = 1000 * 60;
                var hour = minute * 60;
                var day = hour * 24;
                var week = day * 7;
                var month = day * 30;
                var year = day * 365;

                var now = new Date().getTime();
                var diffValue = now - time;

                var yearC = diffValue / year;
                var monthC = diffValue / month;
                var weekC = diffValue / week;
                var dayC = diffValue / day;
                var hourC = diffValue / hour;
                var minC = diffValue / minute;
                if (yearC >= 1) {
                    result = model.action("formatDate")(time, "yyyy-MM-dd hh:mm");
                } else if (monthC >= 6) {
                    result = model.action("formatDate")(time, "MM-dd hh:mm");
                } else if (monthC >= 1) {
                    result = Math.floor(monthC) + "个月前";
                } else if (weekC >= 1) {
                    result = Math.floor(weekC) + "周前";
                } else if (dayC >= 1) {
                    result = Math.floor(dayC) + "天前";
                } else if (hourC >= 1) {
                    result = Math.floor(hourC) + "个小时前";
                } else if (minC >= 1) {
                    result = Math.floor(minC) + "分钟前";
                } else {
                    result = "刚刚";
                }
                return result;

            },
            search: function () {
                return model.flush("announces");
            },
            publish: function () {
                window.location.href = "publish";
            },
            confirmRemove: function(item) {
                model.get("announces").setCurrent(item);
                $("#removeConfirmModal").modal("show");
                return false;
            },
            remove: function () {
                var item = model.get("announces").current;
                return $.ajax({
                    async: true,
                    type: "DELETE",
                    url: service.remove.replace(":id", item.get("id"))
                }).done(function () {
                    $("#announce_" + item.get("id")).transition({
                        animation:'scale',
                        onComplete: function() {
                            item.remove(true);
                            if (typeof window.parent.refreshMessage === "function") {
                                window.parent.refreshMessage();
                            }
                        }
                    });
                });
            }
        });

        if (typeof window.parent.refreshMessage === "function") {
            window.parent.refreshMessage();
        }
    });

}).call(this);
