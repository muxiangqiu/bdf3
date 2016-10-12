(function () {
    cola(function (model) {

        var service = {
            add: "../api/message/add",
            loadUsers: "../service/user/load"
        };

//        $.fn.form.settings.rules.adminLevel = function(value) {
//            return value && model.get("message.conten")
//        };


        tinymce.init({
            selector: '#txtMessage',
            height: 250,
            theme: 'modern',
            plugins: [
                'advlist autolink lists link image charmap print preview hr anchor pagebreak',
                'searchreplace wordcount visualblocks visualchars code fullscreen',
                'insertdatetime media nonbreaking save table contextmenu directionality',
                'emoticons template paste textcolor colorpicker textpattern imagetools'
            ],
            toolbar1: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media | forecolor backcolor emoticons',
            image_advtab: true,
            menubar: false
        }).then(function(editors) {
            editors[0].on("keyup", function() {
                var content = tinymce.get("txtMessage").getContent()
                model.set("message.content", content);
            });
        });

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

        model.describe("users", {
            provider: {
                url: service.loadUsers,
                pageSize: 18,
                parameter: {
                    searchKey: "{{searchKey}}"
                },
                success: function() {
                    $("#ddUser").visibility({
                        once: true,
                        observeChanges: true,
                        onBottomVisible: function() {
                            model.get("users").nextPage();
                        }
                    });
                }
            }
        });


        model.set("message", {});

        model.action({
            search: function () {
                return model.flush("users");
            },
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

        $(".ui.dropdown").dropdown({
            onChange: function(value, text, $selectedItem) {
                model.set("message.additional", value);
            }
        });
    });

}).call(this);
