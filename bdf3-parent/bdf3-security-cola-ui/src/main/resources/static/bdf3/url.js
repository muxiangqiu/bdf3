(function () {
    cola(function (model) {

        var service = {
            load: "./api/url/load-tree",
            add: "./api/url/add",
            remove: "./api/url/remove/:id",
            modify: "./api/url/modify"
        };


        $("#addUrlForm").form({
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

        $("#editUrlForm").form({
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

        model.describe("urlTree", {
            provider: {
                url: service.load
            }
        });

        model.set("path", [{
            name: "顶层"
        }]);

        model.get("urlTree",function(urlTree) {
            model.set("urls", urlTree);
            model.set("inSearch", false);
            model.action("showCards")();
        });

        model.set("addUrl", {});
        model.set("editUrl", {});

        model.action({
            search: function () {
                var searchKey, result, name, desc, url;
                searchKey = model.get("searchKey");
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
                        model.set("inSearch", true);
                    } else {
                        model.set("path", [{
                            name: "顶层"
                        }]);
                        model.set("urls", urls);
                        model.set("inSearch", false);
                    }
                    model.action("showCards")();
                });

            },
            showCards: function() {
                var ids, urls;
                ids = ["#addUrlForm"];
                urls = model.get("urls");
                urls.each(function(u) {
                    ids.push("#url_" + u.get("id"));
                });
                $(ids).transition({
                    animation : 'pulse',
                    reverse  : "auto",
                    interval  : 50,
                    displayType: "flex"
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
                    model.set("inSearch", false);
                    model.action("showCards")();
                    return false;
                }
                model.set("path", model.action("getPath")(target));
                children = target.get("children");
                if (!children) {
                    target.set("children", []);
                    children = target.get("children");
                }
                model.set("urls", children);
                model.action("showCards")();
                model.set("inSearch", false);
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
            keyup: function() {
                var url = model.get("addUrl");
                if (url.get("name")) {
                    $("#addBtn").removeClass("disabled");
                } else {
                    $("#addBtn").addClass("disabled");
                }
                url = model.get("editUrl");
                if (url.get("name")) {
                    $("#editSaveBtn").removeClass("disabled");
                } else {
                    $("#editSaveBtn").addClass("disabled");
                }
            },
            showEditModal: function(entity) {
                model.set("editUrl", entity.toJSON());
                model.get("urls").setCurrent(entity);
                $("#editSaveBtn").addClass("disabled");
                $("#editModal")
                    .modal('setting', 'closable', false)
                    .modal("show");
                return false;

            },
            edit: function () {
                var options, entity, data, current;
                entity = model.get("editUrl");
                $("#editUrlForm").form("validate form");
                if (!$("#editUrlForm").form("is valid")) {
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
                    current = model.get("urls").current;
                    current.set(data).setState(cola.Entity.STATE_NONE);
                    if (current._target) {
                        current._target.set(data).setState(cola.Entity.STATE_NONE);
                    }
                });
                $("#editModal").modal("hide");
            },
            add: function () {
                var options, entity, data, parent, order;
                if (!$("#addUrlForm").form("is valid")) {
                    return;
                }
                entity = model.get("addUrl");
                data = entity.toJSON();
                parent = model.get("path").last();

                if (parent.get("id")) {
                    order = parent._target.get("children").entityCount;
                } else {
                    order = model.get("urlTree").entityCount;
                }

                data.parentId = parent.get("id");
                data.order = order;
                data.navigable = true;
                options = {
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    type: "POST",
                    url: service.add
                };
                $.ajax(options).done(function (id) {
                    var newEntity, copy;
                    data.id = id;
                    model.set("addUrl", {});
                    $("#addBtn").addClass("disabled");
                    copy = model.get("urls").insert(data).setState(cola.Entity.STATE_NONE);
                    if (model.get("inSearch")) {
                        if (parent.get("id")) {
                            newEntity = parent._target.get("children").insert(data);
                        } else {
                            newEntity = model.get("urlTree").insert(data);
                        }
                        copy._target = newEntity;
                    }
                    $("#url_" + id).transition({
                        animation : 'jiggle',
                        duration  : 800,
                        interval  : 50,
                        displayType: "flex"
                    });
                });
            },
            confirmRemove: function(entity) {
                model.get("urls").setCurrent(entity);
                $("#removeConfirmModal").modal("show");
                return false;
            },
            remove: function () {
                var entity = model.get("urls").current;
                return $.ajax({
                    async: true,
                    type: "DELETE",
                    url: service.remove.replace(":id", entity.get("id"))
                }).done(function () {
                    $("#url_" + entity.get("id")).transition({
                        animation:'scale',
                        onComplete: function() {
                            entity.remove(true);
                            if (entity._target) {
                                entity._target.remove(true);
                            }
                        }
                    });
                });
            }
        });
    });

}).call(this);
