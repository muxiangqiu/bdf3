(function () {
    cola(function (model) {

        var service = {
            load: "./api/user/detail",
            loadRoles: "./api/me/load-role",
            validatePassword: "./api/me/validate-password/:password",
            changePassword: "./api/me/change-password/:newPassword"
        };

        model.describe("user", {
            provider: {
                url: service.load
            }
        });

        model.describe("roles", {
            provider: {
                url: service.loadRoles
            }
        });

        $.fn.form.settings.rules.validatePassword = function(password) {
            var result = true;
            if (password) {
                $.ajax({
                    method: "GET",
                    async: false,
                    url: service.validatePassword.replace(":password", password)
                }).done(function(data) {
                    result = data;
                });
            }

            return result;
        };


        var options = {
            on: "blur",
            fields: {
                password: {
                    identifier: "password",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入密码"
                        },
                        {
                            type: "validatePassword",
                            prompt: "密码输入不正确"
                        }
                    ]
                },
                newPassword: {
                    identifier: 'newPassword',
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入新密码"
                        },
                        {
                            type: "different[password]",
                            prompt: '新密码不能和老密码相同'
                        },
                        {
                            type   : 'minLength[6]',
                            prompt : '密码至少 {ruleValue} 字符'
                        }
                    ]
                },
                newPasswordAgain: {
                    identifier: 'newPasswordAgain',
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入确认新密码"
                        },
                        {
                            type: "match[newPassword]",
                            prompt: '两次输入密码不一致'
                        }
                    ]
                }
            }
        };

        $("#editPasswordForm").form(options);

        model.action({
            changePassword: function () {
                var options;
                $("#editPasswordForm").form("validate form");
                if (!$("#editPasswordForm").form("is valid")) {
                    return;
                }

                options = {
                    contentType: "application/json",
                    type: "PUT",
                    successMessage: "密码修改成功。",
                    url: service.changePassword.replace(":newPassword", model.get("newPassword"))
                };
                $.ajax(options).done(function(){
                    model.set({
                        password: null,
                        newPassword: null,
                        newPasswordAgain: null
                    });
                });
            }

        });

    });

}).call(this);
