package com.enodeframework.rocketmq.message;

import com.enodeframework.common.io.AsyncTaskResult;
import com.enodeframework.eventing.DomainEventStreamMessage;
import com.enodeframework.queue.QueueMessage;
import com.enodeframework.queue.domainevent.AbstractDomainEventPublisher;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.CompletableFuture;

/**
 * @author anruence@gmail.com
 */
public class RocketMQDomainEventPublisher extends AbstractDomainEventPublisher {

    private DefaultMQProducer producer;

    @Override
    public CompletableFuture<AsyncTaskResult> publishAsync(DomainEventStreamMessage eventStream) {
        QueueMessage queueMessage = createDomainEventStreamMessage(eventStream);
        Message message = RocketMQTool.covertToProducerRecord(queueMessage);
        return SendRocketMQService.sendMessageAsync(producer, message, queueMessage.getRouteKey());
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }

    public void setProducer(DefaultMQProducer producer) {
        this.producer = producer;
    }
}
