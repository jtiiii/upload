import Vue from 'vue';
import BootStrapVue from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import App from './App.vue';

Vue.use(BootStrapVue);

window.app = new Vue({
    el: '#app',
    render: ( h ) => h(App)
});