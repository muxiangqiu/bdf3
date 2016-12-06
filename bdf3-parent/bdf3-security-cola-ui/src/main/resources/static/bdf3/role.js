(function () {
    cola(function (model) {

        var service = {
            load: "./api/role/load",
            add: "./api/role/add",
            remove: "./api/role/remove/:id",
            modify: "./api/role/modify",
            loadUrlsByRoleId: "./api/role/load-urls/{{@id}}",
            loadUrls: "./api/url/load-tree",
            addUrl: "./api/permission/add",
            removeUrl: "./api/permission/remove/:roleId/:resourceId"
        };


        $("#addRoleForm").form({
            on: "blur",
            fields: {
                nickname: {
                    identifier: "name",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入名称"
                        }
                    ]
                }
            }
        });

        $("#editRoleForm").form({
            on: "blur",
            fields: {
                nickname: {
                    identifier: "name",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入名称"
                        }
                    ]
                }
            }
        });

        model.describe("roles", {
            provider: {
                url: service.load,
                pageSize: 20,
                parameter: {
                    searchKey: "{{searchKey}}"
                },
                success: function(self, arg) {

                    if (arg.result.length !== self.get("pageSize")) {
                        return;
                    }
                    $("#roleCards").visibility({
                        once: true,
                        observeChanges: true,
                        onBottomVisible: function() {
                            model.get("roles").nextPage()
                        }
                    });

                }
            },
            dataType: {
                properties: {
                    urls: {
                        provider: {
                            url: service.loadUrlsByRoleId
                        }
                    }
                }
            }

        });



        model.describe("urlTree", {
            provider: {
                url: service.loadUrls
            }
        });

        model.set("addRole", {});
        model.set("editRole", {});

        model.set("path", [{
            name: "顶层"
        }]);

        model.action({
            search: function () {
                return model.flush("roles");
            },
            keyup: function() {
                var role = model.get("addRole");
                if (role.get("name")) {
                    $("#addBtn").removeClass("disabled");
                } else {
                    $("#addBtn").addClass("disabled");
                }
                role = model.get("editRole");
                if (role.get("name")) {
                    $("#editSaveBtn").removeClass("disabled");
                } else {
                    $("#editSaveBtn").addClass("disabled");
                }

            },
            showEditModal: function(entity) {
                model.set("editRole", entity.toJSON());
                model.get("roles").setCurrent(entity);
                $("#editSaveBtn").addClass("disabled");
                $("#editModal")
                    .modal('setting', 'closable', false)
                    .modal("show");
                return false;

            },
            edit: function () {
                var options, entity, data;
                entity = model.get("editRole");
                $("#editRoleForm").form("validate form");
                if (!$("#editRoleForm").form("is valid")) {
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
                    model.get("roles").current.set(data).setState(cola.Entity.STATE_NONE);
                });
                $("#editModal").modal("hide");
            },
            add: function () {
                var options, entity, data;
                if (!$("#addRoleForm").form("is valid")) {
                    return;
                }
                entity = model.get("addRole");

                data = entity.toJSON();
                options = {
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    type: "POST",
                    url: service.add
                };
                $.ajax(options).done(function (id) {
                    data.id = id;
                    model.set("addRole", {});
                    $("#addBtn").addClass("disabled");
                    model.get("roles").insert(data, "begin").setState(cola.Entity.STATE_NONE);
                    $("#role_" + id).transition({
                        animation : 'jiggle',
                        duration  : 800,
                        interval  : 50,
                        displayType: "flex"
                    });
                });
            },
            confirmRemove: function(entity) {
                model.get("roles").setCurrent(entity);
                $("#removeConfirmModal").modal("show");
                return false;
            },
            remove: function () {
                var entity = model.get("roles").current;
                return $.ajax({
                    async: true,
                    type: "DELETE",
                    url: service.remove.replace(":id", entity.get("id"))
                }).done(function () {
                    $("#role_" + entity.get("id")).transition({
                        animation:'scale',
                        onComplete: function() {
                            entity.remove(true);
                        }
                    });
                });
            },
            showUrlModal: function(role) {
                role.get("urls", function(urls) {
                    model.get("urlTree", function(urlTree) {
                        if (!urls) {
                            role.set("urls", []);
                            urls = role.get("roles");
                        }
                        model.set({
                            selectedUrls: urls,
                            currentRole: role,
                            urls: urlTree,
                            searchUrlKey: null
                        });

                        $("#urlModal")
                            .modal('setting', 'closable', false)
                            .modal("show");
                        model.action("searchUrl")();
                        model.action("initSelectedUrl")();
                    });

                });
                return false;

            },
            initSelectedUrl: function (callback) {
                var role, urls, ids;
                role = model.get("currentRole");
                if (role) {
                    urls = role.get("urls");
                    ids = [];
                    $("#urlCards .ui.card.added")
                        .removeClass("added")
                        .find(".ui.button")
                        .removeClass("disabled")
                        .html("<i class='plus icon'></i> 添加菜单 ");
                    if (!urls || urls.entityCount <= 0) {
                        return;
                    }
                    urls.each(function(u) {
                        ids.push("#" + u.get("id"));
                        $("#" + u.get("id"))
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
            searchUrl: function () {
                var searchKey, result, name, desc, url;
                searchKey = model.get("searchUrlKey");
                model.set("urls", []);
                result = model.get("urls");
                model.get("urlTree", function(urls) {
                    var processUrls = function(urls) {
                        if (!urls) return;
                        urls.each(function(u) {
                            name = u.get("name");
                            desc = u.get("description");
                            if (name && name.indexOf(searchKey) !== -1 || desc && desc.indexOf(searchKey) !== -1) {
                                url = result.insert(u.toJSON({
                                    simpleValue: true
                                }));
                                url._target = u;
                            }
                            processUrls(u.get("children"));
                        });
                    };
                    if (searchKey) {
                        model.set("path", [{
                            name: "搜索：" + searchKey
                        }]);
                        processUrls(urls);
                    } else {
                        model.set("path", [{
                            name: "顶层"
                        }]);
                        model.set("urls", urls);
                    }
                    model.action("initSelectedUrl")();
                });

            },
            getPath: function(url) {
                var result, entity;
                if (url.parent && url.parent.parent instanceof cola.Entity) {
                    result = model.action("getPath")(url.parent.parent);
                    entity = result.insert(url.toJSON({
                        simpleValue: true
                    }));
                    entity._target = url;

                } else {
                    result = new cola.EntityList();
                    result.fillData([{
                        name: "顶层"
                    }]);
                }
                return result;
            },
            nextUrls: function(url) {
                var target, children;
                target = url;
                if (url._target) {
                    target = url._target;
                } else if (!url.get("id")) {
                    model.set("path", [url.toJSON()]);
                    model.set("urls", model.get("urlTree"));
                    model.action("initSelectedUrl")();
                    return false;
                }
                model.set("path", model.action("getPath")(target));
                children = target.get("children");
                if (!children) {
                    target.set("children", []);
                    children = target.get("children");
                }
                model.set("urls", children);
                model.action("initSelectedUrl")();
                return false;
            },
            indexPath: function(url) {
                if (model.get("path").last() !== url) {
                    model.action("nextUrls")(url);
                }
            },
            clickUrlCard: function (url) {
                var path;
                $("#url_" + url.get("id")).transition({
                    animation : 'jiggle',
                    duration  : 800,
                    interval  : 200,
                    displayType: "flex"
                });
                if (url._target) {
                    path = model.action("getPath")(url._target.parent.parent);
                    model.set("path", path);
                }
                model.get("urls").setCurrent(url);
            },
            clickRoleCard: function (role) {
                $("#role_" + role.get("id")).transition({
                    animation : 'jiggle',
                    duration  : 800,
                    interval  : 200,
                    displayType: "flex"
                });
            },
            addUrl: function(url) {
                var role = model.get("currentRole");
                $.ajax({
                    contentType: "application/json",
                    type: "POST",
                    data: JSON.stringify({
                        roleId: role.get("id"),
                        resourceId: url.get("id"),
                        resourceType: "URL",
                        attribute: "ROLE_" + role.get("id")
                    }),
                    url: service.addUrl
                }).done(function(id) {
                    var data = url.toJSON();
                    model.get("selectedUrls").insert(data);
                    model.set("selectedUrls", model.get("selectedUrls"));
                    $("#" + url.get("id"))
                        .addClass("added")
                        .find(".ui.button")
                        .addClass("disabled")
                        .html("<i class='checkmark icon'></i> 已添加 ");

                    $("#selected_" + url.get("id")).transition({
                        animation : 'jiggle',
                        duration  : 800,
                        interval  : 200,
                        displayType: "flex"
                    });

                });
            },
            removeUrl: function(url) {
                var role = model.get("currentRole");
                $.ajax({
                    type: "DELETE",
                    contentType: "application/json",
                    url: service.removeUrl.replace(":roleId", role.get("id")).replace(":resourceId", url.get("id"))
                }).done(function() {
                    $("#selected_" + url.get("id")).transition({
                        animation:'scale',
                        onComplete: function() {
                            url.remove();
                            $("#" + url.get("id"))
                                .removeClass("added")
                                .find(".ui.button")
                                .removeClass("disabled")
                                .html("<i class='plus icon'></i> 添加菜单 ");
                        }
                    });

                });
            }
        });
    });

}).call(this);
