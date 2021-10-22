(function () {
    cola(function (model) {

        var service = {
            load: "./api/user/load",
            add: "./api/user/add",
            remove: "./api/user/remove/:username",
            modify: "./api/user/modify",
        };

        window.closeModifyUserModal = function() {
            $(".edit-modal").dimmer("hide");
        };


        var options = {
            on: "blur",
            fields: {
                nickname: {
                    identifier: "nickname",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入昵称"
                        }
                    ]
                },
                username: {
                    identifier: 'username',
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入用户名"
                        }
                    ]
                }
            }
        };
        options.fields.password = {
            identifier: 'password',
            rules: [
                {
                    type: "empty",
                    prompt: "请输入密码"
                }
            ]
        };
        
        $("#addUserForm").form(options);

        $("#editUserForm").form({
            on: "blur",
            fields: {
                nickname: {
                    identifier: "nickname",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入昵称"
                        }
                    ]
                }
            }
        });

        model.describe("users", {
            provider: {
                url: service.load,
                pageSize: 18,
                parameter: {
                    searchKey: "{{searchKey}}"
                },
                success: function(self, arg) {
                    if (arg.result.length !== self.get("pageSize")) {
                        return;
                    }
                    $("#userCards").visibility({
                        once: true,
                        observeChanges: true,
                        onBottomVisible: function() {
                            model.get("users").nextPage()
                        }
                    });

                    $("#userTable").visibility({
                        once: true,
                        observeChanges: true,
                        onBottomVisible: function() {
                            model.get("users").nextPage()
                        }
                    });

                }
            }
        });

      
        model.set("addUser", {
            accountNonExpired: true,
            accountNonLocked: true,
            credentialsNonExpired: true,
            enabled: true
        });

        model.set("switchMode", $.cookie("switchMode"));

        model.action({
            switchGrid: function() {
                model.set({
                    showStyleIcon: "list layout",
                    showStyleTip: "切换为列表",
                    switchMode: "switchGrid"
                });
                $("#userTable").hide();
                $("#userCards").show();
                $.cookie("switchMode", "switchGrid", { expires: 10000000 });
            },
            switchList: function() {
                model.set({
                    showStyleIcon: "grid layout",
                    showStyleTip: "切换为表格",
                    switchMode: "switchList"
                });
                $("#userTable").show();
                $("#userCards").hide();
                $.cookie("switchMode", "switchList", { expires: 10000000 });
            },
            toggleStyle: function() {
                var switchMode;
                switchMode = model.get("switchMode");
                if (switchMode === "switchGrid") {
                    model.action("switchList")();
                } else {
                    model.action("switchGrid")();
                }
            },
            search: function () {
                return model.flush("users");
            },
            
            showAddModal: function() {
                $("#addModal").modal('setting', 'closable', false).modal("show");
            },
            showEditModal: function(entity) {
                
                model.set("editUser", entity.toJSON());
                model.get("users").setCurrent(entity);
                $("#editModal")
                    .modal('setting', 'closable', false)
                    .modal("show");
                

                return false;

            },
            edit: function () {
                var options, entity, data;
                entity = model.get("editUser");
                $("#editUserForm").form("validate form");
                if (!$("#editUserForm").form("is valid")) {
                    return;
                }
                data = entity.toJSON();
                options = {
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    type: "PUT",
                    url: service.modify
                };
                $.ajax(options).done(function () {
                    model.get("users").current.set(data).setState(cola.Entity.STATE_NONE);
                });
                $("#editModal").modal("hide");
            },
            add: function () {
                var options, entity, data;
                entity = model.get("addUser");
                $("#addUserForm").form("validate form");
                if (!$("#addUserForm").form("is valid")) {
                    return;
                }
                data = entity.toJSON();
                options = {
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    type: "POST",
                    url: service.add
                };
                $.ajax(options).done(function () {
                    model.set("addUser", {
                        accountNonExpired: true,
                        accountNonLocked: true,
                        credentialsNonExpired: true,
                        enabled: true
                    });
                    model.get("users").insert(data, "begin").setState(cola.Entity.STATE_NONE);
                    $("#addModal").modal("hide");
                    $("#user_" + data["username"]).transition({
                        animation : 'jiggle',
                        duration  : 800,
                        interval  : 50,
                        displayType: "flex"
                    });
                });
            },
            confirmRemove: function(entity) {
                model.get("users").setCurrent(entity);
                $("#removeConfirmModal").modal("show");
                return false;
            },
            remove: function () {
                var entity = model.get("users").current;
                return $.ajax({
                    async: true,
                    type: "DELETE",
                    url: service.remove.replace(":username", entity.get("username"))
                }).done(function () {
                    $("#user_" + entity.get("username")).transition({
                        animation:'scale',
                        onComplete: function() {
                            entity.remove(true);
                        }
                    });
                });
            },
           
           
            clickUserCard: function (user) {
                $("#user_" + user.get("username")).transition({
                    animation : 'jiggle',
                    duration  : 800,
                    interval  : 200,
                    displayType: "flex"
                });
            }
           
        });

        if (model.get("switchMode")) {
            model.action(model.get("switchMode"))();
        } else {
            if (cola.device.desktop) {
                model.action("switchList")();
            } else {
                model.action("switchGrid")();
            }
        }
    });

}).call(this);
