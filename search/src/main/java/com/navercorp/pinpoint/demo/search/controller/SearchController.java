/*
 * Copyright 2016 Naver Corp.
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

package com.navercorp.pinpoint.demo.search.controller;

import com.navercorp.pinpoint.demo.search.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HyunGil Jeong
 */
@RestController
public class SearchController {

    @Autowired
    private RemoteService remoteService;

    @RequestMapping("/getTeamAtPosition")
    public String getTeamAtPosition(@RequestParam(value = "position", defaultValue = "1") String position) {
        return this.remoteService.getTeamAt(Integer.parseInt(position));
    }
}
