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
                            model.get("announces").nextPage()
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
            publish: function () {
                window.location.href = "announce/publish";
            }
        });

        $(".ui.menu .item").tab();

        if (typeof window.parent.refreshMessage === "function") {
            window.parent.refreshMessage();
        }
    });

}).call(this);
