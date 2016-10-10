(function () {
    cola(function (model) {

        var service = {
            add: "../api/message/add"
        };


        $("#messageForm").form({
            on: "blur",
            fields: {
                receiver: {
                    identifier: "receiver",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入接收者"
                        }
                    ]
                },
                content: {
                    identifier: "content",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入内容"
                        }
                    ]
                }
            }
        });


        model.set("message", {});

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
                var options, entity, data;
                $("#messageForm").form("validate form");
                if (!$("#messageForm").form("is valid")) {
                    return;
                }
                entity = model.get("message");

                data = entity.toJSON();
                data.content = tinymce.get("txtMessage").getContent();
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
                    model.action("openListPage")();
                });
            },
            cancel: function() {
                model.action("openListPage")();
            }
        });
    });

}).call(this);
