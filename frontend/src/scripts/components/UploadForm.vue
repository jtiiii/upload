<template>
    <div class="uploadForm">
        <b-form-file v-model="file" :state="Boolean(file)"></b-form-file>
        <p>Selected file: {{ file && file.name }}</p>
        <b-button variant="warning" type="button" @click="upload">Upload</b-button>
        <b-progress class="progress" :value="counter" :max="max" show-progress animated></b-progress>
        <p v-show="url">URL: {{ url }}</p>
    </div>
</template>
<script type="text/javascript">
    import Api from "../Api";
    export default {
        data(){
            return {
                "file": null,
                counter: 0,
                max: 100,
                url: ""
            };
        },
        methods:{
            upload(){
                Api.upload(this.file)
                    .then( data => {
                        console.info(data);
                        this.url=data;
                    });
                this.getUploadProgress();
            },
            getUploadProgress(){
                return Api.getUploadProgress().then( data => {
                    this.counter = data;
                    const _vue_ = this;
                    if(data < 100){
                        setTimeout(function(){
                            _vue_.getUploadProgress();
                        },1000);
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
    .progress{
        margin-top: 30px;
        height: 30px;
    }
</style>