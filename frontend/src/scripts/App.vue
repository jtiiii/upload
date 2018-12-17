<template>
    <div>
        <v-login-form ref="loginForm" @loginSuccess="loginSuccess" @logout="logout"></v-login-form>
        <v-upload-form v-if="loginState"></v-upload-form>
    </div>
</template>
<script type="text/javascript">
    import LoginForm from './components/LoginForm.vue';
    import UploadForm from './components/UploadForm.vue';
    import Api from './Api';

    export default {
        components:{
            'v-login-form': LoginForm,
            'v-upload-form': UploadForm
        },
        data(){
            return {
                loginState: false
            };
        },
        methods:{
            checkLogin(){
                Api.checkLogin().then(data => {
                    console.log("loginCheck",data);
                    this.$refs['loginForm'].loginSuccess(data);
                }).catch( error => {
                    console.log("Not login.");
                } );
            },
            loginSuccess( user ){
                this.loginState = true;
            },
            logout(){
                this.loginState =false;
            }
        },
        mounted(){
            this.checkLogin();
        }
    }
</script>
<style scoped>

</style>