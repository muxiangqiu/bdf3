(function () {
    cola(function (model) {

        var service = {
            load: "api/announce/load",
            loadUnread: "api/announce/load-unread"
        };

        model.describe("unreadAnnounces", {
            provider: {
                url: service.loadUnread,
                success: function() {
                    model.set("unreadCount", model.get("unreadAnnounces").entityCount);
                }
            }

        });

        model.describe("announces", {
            provider: {
                url: service.load,
                pageSize: 20,
                parameter: {
                    title: "{{title}}"
                },
                success: function() {
                    $("#allList").visibility({
                        once: true,
                        observeChanges: true,
                        onBottomVisible: function() {
                            model.get("announces").nextPage();
                        }
                    });
                }

            }
        });


        model.action({
            search: function () {
                return model.flush("announces");
            },
            openListPage: function() {
                if (typeof window.parent.expandAndOpenPage === "function") {
                    window.parent.expandAndOpenPage("announce");
                    window.parent.refreshPage()
                } else {
                    window.history.back();
                }
            },
            openDetail: function(item) {
                if (typeof window.parent.expandAndOpenPage === "function") {
                            window.parent.expandAndOpenPage({
                        id: "announce_detail" + item.get("id"),
                        path: "announce/detail/" + item.get("id"),
                        name: "公告详情"
                    });
                } else {
                    window.location.href = "announce/detail/" + item.get("id");
                }
            },
            publish: function () {
                window.location.href = "announce/publish";
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
