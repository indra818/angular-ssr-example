/*
 * webpack.config.js
 * v1.0.0
 * 2018. 8. 8.
 * Copyright (c) 2018 Kyeongjin Kim. All rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const path = require('path');
const webpack = require('webpack');

const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

module.exports = {
  mode: 'development',
  entry: {server: './server.ts'},
  resolve: {extensions: ['.js', '.ts']},
  target: 'node',
  externals: [/(node_modules|main\..*\.js)/],
  output: {
    path: path.join(__dirname, '../resources'),
    filename: '[name].js'
  },
  module: {
    rules: [
      {test: /\.ts$/, loader: 'ts-loader'}
    ]
  },
  plugins: [
    // "WARNING Critical dependency: the request of a dependency is an expression" 경고 메시지가 발생하는
    // 이슈(https://github.com/angular/angular/issues/11580)에 따른 임시 수정
    new webpack.ContextReplacementPlugin(
      /(.+)?angular(\\|\/)core(.+)?/,
      path.join(__dirname, 'src'), // 소스 디렉토리 위치
      {} // a map of your routes
    ),
    new webpack.ContextReplacementPlugin(
      /(.+)?express(\\|\/)(.+)?/,
      path.join(__dirname, 'src'),
      {}
    ),
    new UglifyJsPlugin({
      uglifyOptions: {
        ecma: 6,
        compress: false,
        mangle: false,
        comments: false
      }
    })
  ]
};
