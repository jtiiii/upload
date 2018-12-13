const commons = require('./common.config.js');
module.exports = commons.extend({
    mode: 'development',
    entry: './src/scripts/main.js',
    devtool: 'source-map',
    devServer:{
        open: true,
    }
});