# VolleyFrame
根据公司项目，把关于volley框架提取出来了，方便以后使用
volley库，从google直接download
t1_core库，为volley扩展，BaseNetBean为网络基础bean，由自己的公司自己定义，使用泛型，映射。提供gson解析,
app为示例。网络调用可参考LoginRequest

//网络请求，ICallback为网络回调
private void setRequest(String phoneNum, String password) {
        LoginRequest request = new LoginRequest(phoneNum,password);
        request.setCallback(new ICallback<BaseNetBean<UserLogin>>() {
            @Override
            public void onSuccess(BaseNetBean<UserLogin> data) {
                if(null != data){
                    if(0 == data.getStatus()){
                        showProgress(false);
                        setupPointView();
                        Toast.makeText(LoginActivity.this,"Successfuly~",Toast.LENGTH_LONG).show();
                    }else{
                        mPasswordView.setError(data.getInfo());
                    }
                }
            }

            @Override
            public void onError(Error error) {
                showProgress(false);
            }
        });
        //must call this method
        BaseApplication.getInstance().getDataClient().perform(request);
    }
