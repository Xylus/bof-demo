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

package com.navercorp.pinpoint.demo.gateway.controller;

import com.navercorp.pinpoint.demo.gateway.service.PersistService;
import com.navercorp.pinpoint.demo.gateway.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;

/**
 * @author HyunGil Jeong
 */
@RestController
public class GatewayController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private PersistService persistService;

    @RequestMapping("/getLeadingTeam")
    public String getLeadingTeam() {
        String leadingTeam = this.searchService.search();
        return "Leading Team : " + leadingTeam;
    }

    @RequestMapping("/persist")
    public String persist(@RequestParam(value = "message", defaultValue = "message") String message) {
        try {
            this.persistService.persist(message);
            return "Persisted " + message;
        } catch (JMSException e) {
            return "Persist Failed";
        }
    }

    @RequestMapping("/persistLeadingTeam")
    public String persistLeadingTeam() {
        String leadingTeam = this.searchService.search();
        return this.persist(leadingTeam);
    }
}
