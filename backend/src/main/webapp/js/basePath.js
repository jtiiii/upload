/*
* 减少相对路径而添加的basePath
* 主要用来替代jsp中经常使用的basePath
* */
/**
 * 项目上下文访问路径
 * @type {string}
 */
var projectPath = window.location.pathname === 'null'?'':window.location.pathname.substring(0,window.location.pathname.indexOf('/',1));


/**
 * 服务器地址 如 : http://localhost:8080
 * @type {string|Array|*|string|Array.<number>}
 */
var serverPath = window.location.origin;

/**
 * 便于服务器资源访问的basePath
 * 如：http://localhost:8080/tms
 * @type {string}
 */
var basePath = serverPath + projectPath;