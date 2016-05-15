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

package com.navercorp.pinpoint.demo.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author HyunGil Jeong
 */
@Service
public class PremierLeagueRemoteService implements RemoteService {

    public static String LEAGUE_TABLE_URL = "http://api.football-data.org/v1/soccerseasons/398/leagueTable";

    private static String UNKNOWN_TEAM = "Unknown";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getTeamAt(int standing) {
        Map<String, Object> result = this.restTemplate.getForObject(LEAGUE_TABLE_URL, Map.class);
        if (CollectionUtils.isEmpty(result)) {
            return UNKNOWN_TEAM;
        }
        // ugly but no time
        List<Map<String, Object>> teams = (List<Map<String, Object>>) result.get("standing");
        for (Map<String, Object> team : teams) {
            int teamStanding = (Integer) team.get("position");
            if (teamStanding == standing) {
                return (String) team.get("teamName");
            }
        }
        return UNKNOWN_TEAM;
    }
}
