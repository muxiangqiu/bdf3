(function () {
    cola(function (model) {

        var service = {
            getDetail: "../../api/announce/detail/:id"
        };

        var id = window.location.href.substring(window.location.href.lastIndexOf("/") + 1)

        model.describe("detail", {
            provider: {
                url: service.getDetail.replace(":id", id),
                success: function(self, arg) {
                    tinymce.init({
                        selector: '#content',
                        inline: true,
                        readonly: true
                    });
                }
            }
        });


        model.action({
            openListPage: function() {
                if (typeof window.parent.expandAndOpenPage === "function") {
                    window.parent.expandAndOpenPage("announce");
                    window.parent.refreshPage()
                } else {
                    window.history.back();
                }
            }
        });
        if (typeof window.parent.refreshMessage === "function") {
            window.parent.refreshMessage();
        }
    });

}).call(this);
