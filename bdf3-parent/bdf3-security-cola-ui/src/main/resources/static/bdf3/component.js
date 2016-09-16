(function () {
    cola(function (model) {

        var service = {
            load: "./service/component/load",
            loadRoles: "./service/role/load",
            modify: "./service/component/modify",
            loadMenus: "./service/url/load-tree",
            add: "./service/component/add",
            remove: "./service/component/remove/:id",
            addPermission: "./service/permission/add",
            removePermission: "./service/permission/remove/:id",
            modifyPermission: "./service/permission/modify"
        };

        model.set("searching", false);

        model.describe("components", {
            provider: {
                url: service.load,
                parameter: {
                    roleId: "{{selectedRole.id}}",
                    urlId: "{{selectedUrl.id}}"
                },
                beforeSend: function(self, arg) {
                    if (!arg.options.data.roleId || !arg.options.data.urlId) {
                        return false;
                    }
                }
            }
        });

        model.describe("roles", {
            provider: {
                url: service.loadRoles,
                pageSize: 20,
                parameter: {
                    searchKey: "{{searchRoleKey}}"
                },
                success: function(self, arg) {
                    $("#roleList").visibility({
                        once: true,
                        observeChanges: true,
                        onBottomVisible: function() {
                            model.get("roles").nextPage()
                        }
                    });
                }
            }

        });

        model.describe("menus", {
            provider: {
                url: service.loadMenus,
                success: function() {
                    var menus;
                    menus = model.get("menus");
                    var urls, parseMenus, temp, children;
                    urls = [];
                    parseMenus = function(menus, level) {
                        if (menus) {
                            menus.each(function(menu) {
                                temp = menu.toJSON({simpleValue: true});
                                temp.level = level;
                                temp.hasChild = false;
                                urls.push(temp);
                                children = menu.get("children");
                                if (children && children.entityCount > 0) {
                                    temp.hasChild = true;
                                    menu.set("hasChild", true);
                                    parseMenus(menu.get("children"), level + 1);
                                }
                            });
                        }
                    };
                    parseMenus(menus, 0);
                    model.set("allUrls", urls);
                    model.set("urls", urls);
                }
            }
        });

        model.get("menus");


        model.set("addComponent", {
            componentType: "ReadWrite"
        });

        model.action({
            searchRole: function () {
                return model.flush("roles");
            },
            openRolePage: function() {
                if (typeof window.parent.expandAndOpenPage === "function") {
                    window.parent.expandAndOpenPage("role");
                }
            },
            navigateRole: function() {
                var stepId, partId;
                if ($("#roleStep").hasClass("active")) {
                    return;
                }
                stepId = $(".ui.steps > .active").attr("id");
                partId = stepId.replace("Step", "Part");
                $("#" + partId).transition({
                    animation:'fly left',
                    onComplete: function() {
                        $("#roleStep").css("cursor", "auto");
                        $("#urlStep").css("cursor", "auto");
                        $("#roleStep").addClass("active");
                        $("#" + stepId).removeClass("active");
                        $("#rolePart").transition('fly right');
                        model.set("selectedRole", null);
                        model.set("selectedUrl", null);
                    }
                });
            },
            navigateUrl: function() {
                var stepId, partId;
                if ($("#urlStep").hasClass("active")) {
                    return;
                }
                if (model.get("selectedRole")) {
                    stepId = $(".ui.steps > .active").attr("id");
                    partId = stepId.replace("Step", "Part");
                    $("#" + partId).transition({
                        animation:'fly left',
                        onComplete: function() {
                            $("#urlStep").css("cursor", "auto");
                            $("#urlStep").addClass("active");
                            $("#" + stepId).removeClass("active");
                            $("#urlPart").transition('fly right');
                            model.set("selectedUrl", null);
                        }
                    });
                }

            },
            selectRole: function(role) {
                $("#rolePart").transition({
                    animation:'fly right',
                    onComplete: function() {
                        $("#roleStep").css("cursor", "pointer");
                        $("#roleStep").removeClass("active");
                        $("#urlStep").addClass("active");
                        $("#urlPart").transition('fly left');
                        model.set("selectedRole", role.toJSON());
                    }
                });
            },
            selectUrl: function(url) {
                $("#urlPart").transition({
                    animation:'fly right',
                    onComplete: function() {
                        $("#urlStep").css("cursor", "pointer");
                        $("#urlStep").removeClass("active");
                        $("#componentStep").addClass("active");
                        $("#componentPart").transition('fly left');
                        model.set("selectedUrl", url.toJSON());
                        model.flush("components");
                    }
                });

            },
            componentItemRender: function(dom, m) {
                $(dom).find(".ui.dropdown").dropdown({
                    action: "hide",
                    onChange: function(value, text) {
                        m.set("item.componentType", value);
                        $(this).find(".text").text(text);
                        model.action("modifyPermission")(m.get("item"));
                    }
                });
            },
            authorize: function(component) {
                if (component.get("authorized")) {
                    model.action("addPermission")(component);
                } else {
                    model.action("removePermission")(component);
                }
            },
            addPermission: function(component) {
                var role = model.get("selectedRole");
                $.ajax({
                    contentType: "application/json",
                    type: "POST",
                    data: JSON.stringify({
                        roleId: role.get("id"),
                        resourceId: component.get("id"),
                        resourceType: "COMPONENT",
                        attribute: "ROLE_" + role.get("id") + "_" + component.get("componentType")
                    }),
                    url: service.addPermission
                }).done(function(id) {
                    component.set("configAttributeId", id);
                });
            },
            removePermission: function(component) {
                $.ajax({
                    type: "DELETE",
                    contentType: "application/json",
                    url: service.removePermission.replace(":id", component.get("configAttributeId"))
                }).done(function() {

                });
            },
            modifyPermission: function (component) {
                var options, role;
                role = model.get("selectedRole");
                options = {
                    contentType: "application/json",
                    data: JSON.stringify({
                        id:component.get("configAttributeId"),
                        roleId: role.get("id"),
                        resourceId: component.get("id"),
                        resourceType: "COMPONENT",
                        attribute: "ROLE_" + role.get("id") + "_" + component.get("componentType")
                    }),
                    type: "PUT",
                    url: service.modifyPermission
                };
                $.ajax(options).done(function () {
                    component.setState(cola.Entity.STATE_NONE);
                });
            },
            add: function() {
                var options, entity, data;
                entity = model.get("addComponent");

                data = entity.toJSON();
                data.urlId = model.get("selectedUrl.id");
                data.path= model.get("selectedUrl.path");
                options = {
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    type: "POST",
                    url: service.add
                };
                $.ajax(options).done(function (id) {
                    data.id = id;
                    model.set("addComponent", {componentType: "ReadWrite"});
                    model.get("components").insert(data).setState(cola.Entity.STATE_NONE);
                });
            },
            edit: function (component) {
                var options, data;
                data = component.toJSON();
                options = {
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    type: "PUT",
                    url: service.modify
                };
                $.ajax(options).done(function () {
                    component.setState(cola.Entity.STATE_NONE);
                });
            },
            confirmRemove: function(component) {
                model.get("components").setCurrent(component);
                $("#removeConfirmModal").modal("show");
                return false;
            },
            remove: function () {
                var component = model.get("components").current;
                return $.ajax({
                    async: true,
                    type: "DELETE",
                    url: service.remove.replace(":id", component.get("id"))
                }).done(function () {
                    component.remove(true);

                });
            },
            searchUrl: function () {
                var searchKey, result, name, desc, url;
                searchKey = model.get("searchUrlKey");
                model.set("urls", []);
                result = model.get("urls");
                model.get("allUrls", function(urls) {
                    var processUrls = function(urls) {
                        if (!urls) return;
                        urls.each(function(u) {
                            name = u.get("name");
                            desc = u.get("description");
                            if (name && name.indexOf(searchKey) !== -1 || desc && desc.indexOf(searchKey) !== -1) {
                                url = result.insert(u.toJSON({
                                    simpleValue: true
                                }));
                            }
                        });
                    };
                    if (searchKey) {
                        model.set("searching", true);
                        processUrls(urls);
                    } else {
                        model.set("searching", false);
                        model.set("urls", model.get("allUrls").toJSON());
                    }
                });

            }
        });
    });

}).call(this);
