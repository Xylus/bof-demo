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

package com.navercorp.pinpoint.demo.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author HyunGil Jeong
 */
@Service
public class LogPersistService implements PersistService {

    @Value("${persist.queue.name}")
    private String persistQueueName;

    @Autowired
    private Session session;

    private MessageProducer persistMessageProducer;

    @PostConstruct
    private void postConstruct() throws JMSException {
        Queue persistQueue = this.session.createQueue(this.persistQueueName);
        this.persistMessageProducer = this.session.createProducer(persistQueue);
        this.persistMessageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    @Override
    public void persist(String message) throws JMSException {
        TextMessage textMessage = this.session.createTextMessage(message);
        this.persistMessageProducer.send(textMessage);
    }

}
