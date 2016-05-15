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

package com.navercorp.pinpoint.demo.gateway.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * @author HyunGil Jeong
 */
@Configuration
public class GatewayConfiguration {

    @Value("${broker.url}")
    private String brokerUrl;

    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClientBuilder.create().build();
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(httpRequestFactory());
    }

    @Bean
    ConnectionFactory amqConnectionFactory() {
        return new ActiveMQConnectionFactory(this.brokerUrl);
    }

    @Bean
    Connection amqConnection(ConnectionFactory amqConnectionFactory) throws JMSException {
        Connection connection = amqConnectionFactory.createConnection();
        connection.start();
        connection.setExceptionListener(e -> System.out.println("JMS Exception occurred. Shutting down client."));
        return connection;
    }

    @Bean
    Session session(Connection amqConnection) throws JMSException {
        return amqConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
}
