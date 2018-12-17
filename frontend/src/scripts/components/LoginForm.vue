<template>
    <div>
        <b-form v-if="!loginState" class="loginForm" @submit="doLogin" method="post">
            <b-form-group label="Account"
                          label-for="input-account">
                <b-form-input id="input-account"
                              type="text"
                              v-model="account">
                </b-form-input>
            </b-form-group>
            <b-form-group label="Password"
                          label-for="input-password">
                <b-form-input id="input-password"
                              type="password"
                              v-model="password">
                </b-form-input>
            </b-form-group>
            <b-button variant="info" type="submit">Login</b-button>
        </b-form>
        <div v-else class="loginForm">
            <p>Login Account: <b>{{ this.account }}</b></p>
            <b-button variant="warning" type="button" @click="doLogout" >Logout</b-button>
        </div>

    </div>
</template>
<script type="text/javascript">
    import { LoginApi } from '../Api';
    export default {
        data(){
            return {
                "account":"",
                "password":"",
                "loginState": false
            }
        },
        methods:{
            doLogin(e){
                e.preventDefault();
                LoginApi.login(this.account,this.password).then( data => {
                    this.loginSuccess();
                });
            },
            doLogout(){
                LoginApi.logout().then( data => {
                    this.loginState = false;
                    this.$emit("logout");
                });
            },
            loginSuccess( account ){
                if(account){
                    this.account = account;
                }
                this.$emit("loginSuccess",this.account);
                this.loginState = true;
            }
        }
    };
</script>
<style scoped>
    .loginForm{
        width: 500px;
        margin: 10px auto;
    }
</style>