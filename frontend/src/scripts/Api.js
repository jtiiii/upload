import _axios from 'axios';
import {Server} from './Config';

const request = _axios.create({
    baseURL: Server.path,
    crossDomain: true,
    withCredentials: true
});
const axios ={
    get(url,options){
        return request.post(url,options);
    },
    post( url, data, options){
        return request.post(url, data,options).then( postHandle ).catch( errorHandle );
    }
};
window.axios = _axios;
function postHandle(response){
    return response.data;
}
function errorHandle(error){
    throw error.response.data;
}

/** 由于后端使用了Spring-security,则单独一个Api对象作为登陆调用。
 * @type {{login(*=, *=): *}}
 */
const LoginApi = {
    login(username,password) {
        const param = new URLSearchParams();
        param.append("username",username);
        param.append("password",password);
        return request.post('/login',param)
            .then( response => {
                Api._config_.xsrfCookieName = response.config.xsrfCookieName;
                Api._config_.xsrfHeaderName = response.config.xsrfHeaderName;
                Api._config_.loginUser = username;
                console.log( username +' login success.',response);
                return username;
            });
    },
    logout(){
        return axios.post('/logout').then( response =>{
            console.log(Api._config_.loginUser+ 'logout success', response);
        });
    }
};

const Api = {
    _config_:{
        loginUser: '',
        xsrfCookieName: '',
        xsrfHeaderName: '',
        JSESSIONID: '',
    },
    connectTest(){
        return axios.post('/test');
    },
    _setConfiig_(username,xsrfCookieName,xsrfHeaderName,JSESSIONID){
        this._config_.loginUser = username;
        this._config_.xsrfCookieName = xsrfCookieName;
        this._config_.xsrfHeaderName = xsrfHeaderName;
        this._config_.JSESSIONID = JSESSIONID;
    },
    _cleanConfig_(){
        this._config_ = {
            loginUser: '',
            xsrfCookieName: '',
            xsrfHeaderName: '',
            JSESSIONID: '',
        };
    },
    upload(file) {
        let formData = new FormData();
        formData.append("file",file);
        return request.post('/uploadFile',formData,{
            headers:{
                "Content-Type": "multipart/form-data"
            }
        });
    },
    getUploadProgress(){
        return request.get("/uploadFile")
    }
};
export { LoginApi};
export default Api;