const commons = require('./webpackConfig/common.config');
const development = require('./webpackConfig/webpack.config.dev');
const production = commons.extend({
    mode: 'production',
    entry: './src/scripts/main.js'
});
module.exports = development;