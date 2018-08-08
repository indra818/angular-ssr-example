/*
 * server.ts
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

require('zone.js/dist/zone-node');

import {provideModuleMap} from '@nguniversal/module-map-ngfactory-loader';

import {renderModuleFactory} from '@angular/platform-server';

const {AppServerModuleNgFactory, LAZY_MODULE_MAP} = require('./dist/angular-server/main');

export declare function registerRenderAdapter(renderAdapter: RenderAdapter): void;

export declare function receiveRenderedPage(uuid: string, html: string, error: any): void;

export class RenderAdapter {

  constructor(private appServerModuleNgFactory: any, private lazyModuleMap: any, private html: string) {
    registerRenderAdapter(this);
  }

  setHtml(html: string) {
    this.html = html;
  }

  renderPage(uuid: string, uri: string) {
    renderModuleFactory(this.appServerModuleNgFactory, {
      document: this.html,
      url: uri,
      extraProviders: [
        provideModuleMap(this.lazyModuleMap)
      ]
    }).then(html => {
      receiveRenderedPage(uuid, html, null);
    });
  }
}

new RenderAdapter(AppServerModuleNgFactory, LAZY_MODULE_MAP, '<app-root></app-root>');
