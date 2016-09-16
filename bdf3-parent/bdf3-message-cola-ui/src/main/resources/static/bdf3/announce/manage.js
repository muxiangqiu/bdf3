(function () {
    cola(function (model) {

        var service = {
            load: "../api/announce/load",
            remove: "../api/announce/remove/:id"
        };

        model.describe("announces", {
            provider: {
                url: service.load,
                pageSize: 20,
                parameter: {
                    title: "{{title}}"
                },
                success: function() {
                    $("#announceList").visibility({
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
