/*
 * RenderService.java
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

package io.github.indra818.angular.service;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import ch.swaechter.angularjuniversal.renderer.utils.RenderUtils;
import ch.swaechter.angularjuniversal.v8renderer.V8RenderEngineFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

/**
 * RenderService
 *
 * @author Kyeongjin.Kim
 * @since 2018. 8. 8.
 */
@Service
public class RenderService {

    private final Renderer renderer;

    public RenderService() throws IOException {
        // 템플릿을 로드하고 리소스 디렉토리에서 임시 서버 번들 파일을 만든다. (물론 이 파일은 수동으로 편집할 때까지 변경되지 않는다.)
        InputStream templateInputStream = getClass().getResourceAsStream("/public/index.html");
        InputStream serverBundleInputStream = getClass().getResourceAsStream("/server.js");
        String templateContent = RenderUtils.getStringFromInputStream(templateInputStream, StandardCharsets.UTF_8);
        File serverBundleFile = RenderUtils.createTemporaryFileFromInputStream("serverbundle", "tmp", serverBundleInputStream);
        // File localserverbundlefile = new File("<Local server bundle on the file system>"); --> 자동 리로딩을 설정 안에서 활성화 할 수 있다.

        // 렌더링 설정 생성. 실제 라이브 리로딩의 경우 임시 파일을 이용하지 않고, 파일 시스템에서 생성된 실제 파일을 사용한다.
        RenderConfiguration configuration = new RenderConfiguration.RenderConfigurationBuilder(templateContent, serverBundleFile)
                .engines(4).liveReload(false).build();

        // 렌더 엔진 생성을 위한 V8 렌더링 엔진 팩토리 만들기
        RenderEngineFactory factory = new V8RenderEngineFactory();

        // 렌더러 생성 및 시작
        this.renderer = new Renderer(factory, configuration);
        this.renderer.startRenderer();
    }

    public Future<String> renderPage(String uri) {
        return renderer.addRenderRequest(uri);
    }

}
