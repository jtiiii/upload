const VueLoaderPlugin = require('vue-loader/lib/plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const path = require('path');
let commonConfig = {
    output: {
        path: path.join(__dirname ,'../build'),
        filename:'[name].bundle.js'
    },
    plugins:[
        new VueLoaderPlugin(),
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: 'src/public/index.html',
        }),
        new CleanWebpackPlugin(['build'],{
            root: path.join(__dirname, '../')
        })
    ],
    module: {
        rules: [
            { test: /\.vue$/, use: ['vue-loader']},
            { test: /\.css$/, use: ['style-loader','css-loader']},
            { test: /\.(jpg|png|jpeg|ttf|ttc)$/, use:[{
                    loader: 'url-loader',
                    options: {
                        limit: 81920,
                        outputPath: 'assets'
                    }}]
            },
            { test: /\.yaml$/, use: ['json-loader','yaml-loader']}
        ]
    },
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js', // 用 webpack 1 时需用 'vue/dist/vue.common.js'
            '@': path.resolve('src'), //import xx from src/xxx 可以改为 import xx from @/xxx
        }
    }
};
module.exports = {
    extend: function ( config ){
        for(let key in commonConfig){
            config[key] = commonConfig[key];
        }
        return config;
    }
};