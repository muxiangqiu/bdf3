(function () {
    cola(function (model) {

        var service = {
            registerUser: "api/register/user",
            registerOrganization: "api/register/organization",
            isExistUser: "api/register/exist/user/:organizationId/:username",
            isExistOrganization: "api/register/exist/organization/:organizationId"

        };

        var  isExistUser = function(organizationId, username) {
            var result;
            result = false
            if (username && organizationId) {
                $.ajax({
                    method: "GET",
                    async: false,
                    url: service.isExistUser.replace(":organizationId", organizationId).replace(":username", username)
                }).done(function(data) {
                    result = data;
                });
            }
            return result;
        };

        $.fn.form.settings.rules.userIsNotExistU = function(username) {
            var organizationId;
            organizationId = model.get("user.organizationId")
            return !isExistUser(organizationId, username);
        };

        $.fn.form.settings.rules.userIsNotExistO = function(username) {
            var organizationId;
            organizationId = model.get("organization.organizationId")
            return !isExistUser(organizationId, username);
        };

        var  isExistOrganization = function(organizationId) {
            var result;
            result = false
            if (organizationId) {
                $.ajax({
                    method: "GET",
                    async: false,
                    url: service.isExistOrganization.replace(":organizationId", organizationId)
                }).done(function(data) {
                    result = data;
                });
            }
            return result;
        };

        $.fn.form.settings.rules.organizationIsExist = function(organizationId) {
            return isExistOrganization(organizationId);
        };

        $.fn.form.settings.rules.organizationIsNotExist = function(organizationId) {
            return !isExistOrganization(organizationId);
        };


        $("#userForm").form({
            on: "blur",
            fields: {
                organizationId: {
                    identifier: "organizationId",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入公司ID"
                        },
                        {
                            type: "organizationIsExist",
                            prompt: "公司ID不存在"
                        }
                    ]
                },
                username: {
                    identifier: "username",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入用户名"
                        },
                        {
                            type: "userIsNotExistU",
                            prompt: "用户名已存在"
                        }
                    ]
                },
                nickname: {
                    identifier: "nickname",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入昵称"
                        }
                    ]
                },
                password: {
                    identifier: "password",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入密码"
                        }
                    ]
                }
            }
        });

        $("#organizationForm").form({
            on: "blur",
            fields: {
                organizationId: {
                    identifier: "organizationId",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入公司ID"
                        },
                        {
                            type: "organizationIsNotExist",
                            prompt: "公司ID已存在"
                        }
                    ]
                },
                organizationName: {
                    identifier: "organizationName",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入公司名称"
                        }
                    ]
                },
                username: {
                    identifier: "username",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入用户名"
                        }
                    ]
                },
                nickname: {
                    identifier: "nickname",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入昵称"
                        }
                    ]
                },
                password: {
                    identifier: "password",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入密码"
                        }
                    ]
                }
            }
        });


        model.set({
            user: {},
            organization: {}
        });
        model.action({
            registerUser: function() {
                var options, user, data;
                user = model.get("user");
                $("#userForm").form("validate form");
                if (!$("#userForm").form("is valid")) {
                    return;
                }
                data = user.toJSON();
                options = {
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    type: "POST",
                    url: service.registerUser,
                    successMessage: "用户组册成功。"
                };
                $.ajax(options).done(function () {
                    setTimeout(function() {
                        window.location.href = loginPath;
                    }, 1500);
                });
            },
            registerOrganization: function() {
                var options, organization, data;
                organization = model.get("organization");
                $("#organizationForm").form("validate form");
                if (!$("#organizationForm").form("is valid")) {
                    return;
                }
                data = organization.toJSON();
                options = {
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    type: "POST",
                    url: service.registerOrganization,
                    successMessage: "公司组册成功。"
                };
                $.ajax(options).done(function () {
                    setTimeout(function() {
                        window.location.href = loginPath;
                    }, 1500);
                });
            }
        });

    });

}).call(this);
