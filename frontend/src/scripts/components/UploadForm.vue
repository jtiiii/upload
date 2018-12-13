<template>
    <div class="uploadForm">
        <b-form-file v-model="file" :state="Boolean(file)"></b-form-file>
        <p>Selected file: {{ file && file.name }}</p>
        <b-button variant="info" type="button" @click="upload">Upload</b-button>
        <b-progress :value="counter" :max="max" show-progress animated></b-progress>
    </div>
</template>
<script type="text/javascript">
    import Api from "../Api";
    export default {
        data(){
            return {
                "file": null,
                counter: 0,
                max: 100
            };
        },
        methods:{
            upload(){

                Api.upload(this.file)
                    .then( response => {
                        console.info(response);
                    });
                this.getUploadProgress();
            },
            getUploadProgress(){
                return Api.getUploadProgress().then( response => {

                    console.info(response);
                    this.counter = response.data;
                    const _vue_ = this;
                    if(response.data < 100){
                        setTimeout(function(){
                            _vue_.getUploadProgress();
                        },20);
                    }

                });
            }
        }
    }
</script>
<style scoped>
    .uploadForm{
        width: 500px;
        margin: 10px auto;
    }
</style>