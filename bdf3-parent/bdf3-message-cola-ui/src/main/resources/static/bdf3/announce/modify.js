(function () {
    cola(function (model) {

        var service = {
            modify: "../../api/announce/modify",
            get: "../../api/announce/detail/:id"
        };

        var id = window.location.href.substring(window.location.href.lastIndexOf("/") + 1)

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
        model.describe("announce", {
            provider: {
                url: service.get.replace(":id", id),
                success: function(self, arg) {
                    tinymce.init({
                        selector: '#txtAnnounce',
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
                        templates: [
                            { title: 'Test template 1', content: 'Test 1' },
                            { title: 'Test template 2', content: 'Test 2' }
                        ]
                    });
                }
            }

        });

        model.action({
            openManagePage: function() {
                if (typeof window.parent.expandAndOpenPage === "function") {
                    window.parent.expandAndOpenPage("announce/manage");
                    window.parent.refreshPage()
                } else {
                    window.history.back();
                }
            },
            modify: function () {
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
                    type: "PUT",
                    url: service.modify
                };
                $.ajax(options).done(function () {
                    model.set("announce", {});
                    model.action("openManagePage")();
                });
            },
            cancel: function() {
                model.action("openManagePage")();
            }
        });


    });

}).call(this);
