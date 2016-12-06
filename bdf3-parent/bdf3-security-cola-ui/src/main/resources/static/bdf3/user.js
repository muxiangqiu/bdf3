(function () {
    cola(function (model) {

        var service = {
            load: "./api/user/load",
            add: "./api/user/add",
            remove: "./api/user/remove/:username",
            modify: "./api/user/modify",
            hasUser: "./api/user/exist/:username",
            loadRoles: "./api/role/load",
            addRole: "./api/authority/add",
            removeRole: "./api/authority/remove/:id"
        };

        window.closeModifyUserModal = function() {
            $(".edit-modal").dimmer("hide");
        };

        $.fn.form.settings.rules.userIsExist = function(username) {
            var result = true;
            if (username) {
                $.ajax({
                    method: "GET",
                    async: false,
                    url: service.hasUser.replace(":username", username)
                }).done(function(data) {
                    result = data;
                });
            }

            return result;
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
                        },
                        {
                            type: "userIsExist",
                            prompt: '用户名已存在'
                        }
                    ]
                }
            }
        };
        if (passwordProp) {
            options.fields.password = {
                identifier: 'password',
                rules: [
                    {
                        type: "empty",
                        prompt: "请输入密码"
                    }
                ]
            };
        }
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

        model.describe("roles", {
            provider: {
                url: service.loadRoles,
                pageSize: 30,
                parameter: {
                    searchKey: "{{searchRoleKey}}"
                },
                success: function(self, arg) {
                    model.action("initSelectedRole")(function() {
                        if (arg.result.length !== self.get("pageSize")) {
                            return;
                        }
                        $("#userCards").visibility({
                            once: true,
                            observeChanges: true,
                            onBottomVisible: function () {
                                model.get("roles").nextPage()
                            }
                        });
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
            searchRole: function () {
                return model.flush("roles");
            },
            showAddModal: function() {
                $("#addModal").modal('setting', 'closable', false).modal("show");
            },
            showEditModal: function(entity) {
                if (modifyUserUrl) {
                    $(".edit-modal iframe").attr("src", modifyUserUrl + "?username=" + entity.get(usernameProp));
                    $(".edit-modal").dimmer("show");
                } else {
                    model.set("editUser", entity.toJSON());
                    model.get("users").setCurrent(entity);
                    $("#editModal")
                        .modal('setting', 'closable', false)
                        .modal("show");
                }

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
                    $("#user_" + data[usernameProp]).transition({
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
                    url: service.remove.replace(":username", entity.get(usernameProp))
                }).done(function () {
                    $("#user_" + entity.get(usernameProp)).transition({
                        animation:'scale',
                        onComplete: function() {
                            entity.remove(true);
                        }
                    });
                });
            },
            showRoleModal: function(user) {
                var roles = user.get("roles");
                if (!roles) {
                    user.set("roles", []);
                    roles = user.get("roles");
                }
                model.set("selectedRoles", roles);
                model.set("currentUser", user);

                $("#roleModal")
                    .modal('setting', 'closable', false)
                    .modal("show");
                model.action("initSelectedRole")();
                return false;
            },
            initSelectedRole: function (callback) {
                var user, roles, ids;
                user = model.get("currentUser");
                if (user) {
                    roles = user.get("roles");
                    ids = [];
                    $("#roleCards .ui.card.added")
                        .removeClass("added")
                        .find(".ui.button")
                        .removeClass("disabled")
                        .html("<i class='plus icon'></i> 添加角色 ");
                    if (!roles) {
                        return;
                    }
                    roles.each(function(r) {
                        ids.push("#" + r.get("id"));
                        $("#" + r.get("id"))
                            .addClass("added")
                            .find(".ui.button")
                            .addClass("disabled")
                            .html("<i class='checkmark icon'></i> 已添加 ");
                    });
                    $(ids).transition({
                        animation : 'jiggle',
                        duration  : 800,
                        interval  : 0,
                        displayType: "flex",
                        onComplete: function() {
                           if (callback) {
                               callback();
                           }
                        }
                    });
                }

            },
            clickUserCard: function (user) {
                $("#user_" + user.get(usernameProp)).transition({
                    animation : 'jiggle',
                    duration  : 800,
                    interval  : 200,
                    displayType: "flex"
                });
            },
            clickRoleCard: function (role) {
                $("#selected_" + role.get("id")).transition({
                    animation : 'jiggle',
                    duration  : 800,
                    interval  : 200,
                    displayType: "flex"
                });
            },
            addRole: function(role) {
                var user = model.get("currentUser");
                $.ajax({
                    contentType: "application/json",
                    type: "POST",
                    data: JSON.stringify({
                        actorId: user.get(usernameProp),
                        roleId: role.get("id")
                    }),
                    url: service.addRole
                }).done(function(id) {
                    var data = role.toJSON();
                    data.rgaId = id;
                    model.get("selectedRoles").insert(data);
                    model.set("selectedRoles", model.get("selectedRoles"));
                    role.set("added", true);
                    $("#" + role.get("id"))
                        .addClass("added")
                        .find(".ui.button")
                        .addClass("disabled")
                        .html("<i class='checkmark icon'></i> 已添加 ");

                    $("#selected_" + role.get("id")).transition({
                        animation : 'jiggle',
                        duration  : 800,
                        interval  : 200,
                        displayType: "flex"
                    });

                });
            },
            removeRole: function(role) {
                $.ajax({
                    type: "DELETE",
                    url: service.removeRole.replace(":id", role.get("rgaId"))
                }).done(function() {
                    $("#selected_" + role.get("id")).transition({
                        animation:'scale',
                        onComplete: function() {
                            role.remove();
                            $("#" + role.get("id"))
                                .removeClass("added")
                                .find(".ui.button")
                                .removeClass("disabled")
                                .html("<i class='plus icon'></i> 添加角色 ");
                        }
                    });

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
