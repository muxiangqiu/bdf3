(function () {
    cola(function (model) {

        var service = {
            add: "../api/announce/add"
        };


        $("#announceForm").form({
            on: "blur",
            fields: {
                title: {
                    identifier: "title",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入标题"
                        }
                    ]
                }
            }
        });


        model.set("announce", {});

        model.action({
            openManagePage: function() {
                if (typeof window.parent.expandAndOpenPage === "function") {
                    window.parent.expandAndOpenPage("announce/manage");
                    window.parent.refreshPage()
                } else {
                    window.history.back();
                }
            },
            publish: function () {
                var options, entity, data;
                $("#announceForm").form("validate form");
                if (!$("#announceForm").form("is valid")) {
                    return;
                }
                entity = model.get("announce");

                data = entity.toJSON();
                data.content = tinymce.get("txtAnnounce").getContent();
                options = {
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    type: "POST",
                    url: service.add
                };
                $.ajax(options).done(function () {
                    model.set("announce", {});
                    if (typeof window.parent.refreshMessage === "function") {
                        window.parent.refreshMessage();
                    }
                    model.action("openManagePage")();
                });
            },
            cancel: function() {
                model.action("openManagePage")();
            }
        });
    });

}).call(this);
