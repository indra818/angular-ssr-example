/*
 * ContentController.java
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

package io.github.indra818.angular.controller;

import io.github.indra818.angular.service.RenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ContentController
 *
 * @author Kyeongjin.Kim
 * @since 2018. 8. 8.
 */
@RestController
public class ContentController {

    private final RenderService renderService;

    @Autowired
    public ContentController(RenderService renderService) {
        this.renderService = renderService;
    }

    @ResponseBody
    @GetMapping("/")
    public String showIndex() throws Exception {
        return renderService.renderPage("/").get();
    }

}
