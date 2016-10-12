(function () {
    cola(function (model) {

        var service = {
            load: "api/message/load",
            loadUnread: "api/message/load-unread",
            remove: "api/chat/remove/:id",
            clear: "api/chat/clear"
        };

        model.describe("unreadMessages", {
            provider: {
                url: service.loadUnread,
                success: function() {
                    var unreadCount = 0;
                    model.get("unreadMessages").each(function(m) {
                        unreadCount += m.get("additional.count");
                    });
                    model.set("unreadCount", unreadCount);
                }
            }

        });

        model.describe("messages", {
            provider: {
                url: service.load
            }
        });


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
                if (typeof window.parent.expandAndOpenPage === "function") {
                    window.parent.expandAndOpenPage({
                        id: "message_send",
                        path: "message/send",
                        name: "发送私信"
                    });
                } else {
                    window.location.href = "message/send";
                }
            },
            getContentText: function(content) {
                return $(content).text().replace(/\s+/g, " ");
            },
            openChat: function(item) {
                var chatId;
                if (item.get("recentTime")) {
                    chatId = item.get("id");
                } else {
                    chatId = item.get("group");
                }
                if (typeof window.parent.expandAndOpenPage === "function") {
                    window.parent.expandAndOpenPage({
                        id: "chat_" + chatId,
                        path: "chat/" + chatId,
                        name: "对话"
                    });
                } else {
                    window.location.href = "chat/" + chatId;
                }
            },
            confirmRemove: function(item) {
                if (item.get("recentTime")) {
                    model.get("messages").setCurrent(item);
                    model.set("removing", "all");
                } else {
                    model.get("unreadMessages").setCurrent(item);
                    model.set("removing", "unread");
                }
                $("#removeConfirmModal").modal("show");
                return false;
            },
            remove: function () {
                var removing = model.get("removing");
                var id, item, other;
                if (removing === "all") {
                    item = model.get("messages").current;
                    id = item.get("id");
                    model.get("unreadMessages").each(function(m) {
                        if (m.get("group") === id) {
                            other = m;
                            model.set("unreadCount", model.get("unreadCount") - 1);
                            return false;
                        }
                    });
                } else {
                    item = model.get("unreadMessages").current;
                    id = item.get("group");
                    model.set("unreadCount", model.get("unreadCount") - 1);
                    model.get("messages").each(function(m) {
                        if (m.get("id") === id) {
                            other = m;
                            return false;
                        }
                    });
                }
                return $.ajax({
                    async: true,
                    type: "DELETE",
                    url: service.remove.replace(":id", id)
                }).done(function () {
                    $("#" + item.get("id")).transition({
                        animation:'scale',
                        onComplete: function() {
                            item.remove(true);
                            if (other) {
                                other.remove(true);
                            }
                            if (typeof window.parent.refreshMessage === "function") {
                                window.parent.refreshMessage();
                            }
                        }
                    });
                });
            },
            clear: function () {
                $.ajax({
                    async: true,
                    type: "DELETE",
                    url: service.clear
                }).done(function () {
                    $(".ui.list > .item").transition({
                        animation:'scale'
                    });
                    model.set("unreadMessages", []);
                    model.set("messages", []);
                    model.set("unreadCount", 0);
                    if (typeof window.parent.refreshMessage === "function") {
                        window.parent.refreshMessage();
                    }
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

            }
        });

        $(".ui.menu .item").tab();

        if (typeof window.parent.refreshMessage === "function") {
            window.parent.refreshMessage();
        }
    });

}).call(this);
