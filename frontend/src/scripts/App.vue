<template>
    <div>
        <v-login-form ref="loginForm"></v-login-form>
        <v-upload-form></v-upload-form>
        <b-button variant="warning" @click="connectTest">Test Connect</b-button>
        <span>Test result: {{ testResult }}</span>
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
                testResult: '',
                dplayer: null
            };
        },
        methods:{
            connectTest(){
                Api.connectTest().then( data => {this.testResult = data});
            }
        }
    }
</script>
<style scoped>

</style>