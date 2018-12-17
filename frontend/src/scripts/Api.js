import _axios from 'axios';
import {Server} from './Config';

const request = _axios.create({
    baseURL: Server.path,
    crossDomain: true,
    withCredentials: true
});
const axios ={
    get(url,options){
        return request.get(url,options).then( getHandle ).catch( errorHandle );
    },
    post( url, data, options){
        return request.post(url, data,options).then( postHandle ).catch( errorHandle );
    }
};
window.axios = _axios;
function getHandle(response){
    return response.data;
}
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
        return axios.post('/login',param)
            .then( data => {
                let token = data.split(":");
                Api._config_.xsrfHeaderName = token[0];
                Api._config_.loginUser = username;
                Api._config_.token= token[1];
                console.log( username +' login success.');
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
        xsrfHeaderName: '',
        token: ''
    },
    _withTokenHeader_: function(){
        let headers = {};
        headers[this._config_.xsrfHeaderName]=this._config_.token;
        return headers;
    },
    connectTest(){
        return axios.post('/test');
    },
    upload(file) {
        let formData = new FormData();
        formData.append("file",file);
        let headers = this._withTokenHeader_();
        headers["Content-Type"]="multipart/form-data";
        return axios.post('/uploadFile',formData,{
            headers: headers
        }).then(data =>{
            return Server.path + data;
        });
    },
    getUploadProgress(){
        return axios.get("/uploadFile");
    },
    checkLogin(){
        return axios.get("/check/login");
    }
};
export { LoginApi};
export default Api;