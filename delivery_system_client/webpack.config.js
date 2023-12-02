/**
 * 解决 @路径问题
 * @type {path.PlatformPath | path}
 */
const path = require('path')

module.exports = {
    context: path.resolve(__dirname, './'),
    resolve: {
        extensions: ['.js', '.vue', '.json'],
        alias: {
            '@': path.resolve('src'),
        }
    }
}

