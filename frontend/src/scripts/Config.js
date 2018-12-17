import serverConfig from '../public/server.yaml';

const Server = serverConfig.servers[serverConfig.use];
Server.path = Server.address + ':' + Server.port + Server.contextPath;
export {Server};