<template>
    <div>
        <b-form class="loginForm" @submit="doLogin" method="post">
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
        <b-button variant="warning" type="button" @click="doLogout" >Logout</b-button>
        <div>{{ testForm }}</div>
    </div>
</template>
<script type="text/javascript">
    import { LoginApi } from '../Api';
    export default {
        data(){
            return {
                "account":"",
                "password":"",
                "testForm":""
            }
        },
        methods:{
            doLogin(e){
                e.preventDefault();
                LoginApi.login(this.account,this.password).then( data => {
                    this.testForm = data;
                });
            },
            doLogout(){
                LoginApi.logout();
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