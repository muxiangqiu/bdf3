(function () {
    $(function () {

        $(".ui.form").form({
            fields: {
                username: {
                    identifier: "username",
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入用户名"
                        }
                    ]
                },
                password: {
                    identifier: 'password',
                    rules: [
                        {
                            type: "empty",
                            prompt: "请输入密码"
                        }
                    ]
                }
            }
        });

        if (loginError) {
            $(".ui.form").form("add errors", [loginError]);
        }
    });

}).call(this);
