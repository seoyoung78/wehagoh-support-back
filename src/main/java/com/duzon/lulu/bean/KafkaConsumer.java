//package com.duzon.lulu.bean;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.listener.AcknowledgingMessageListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KafkaConsumer implements AcknowledgingMessageListener<String, Object> {
//    protected Logger logger = LogManager.getLogger(this.getClass());
//
//    @Override
//    @KafkaListener(topics = "clinicsupport.support-scl-request", containerFactory = "kafkaListenerContainerFactory")
//    public void onMessage(ConsumerRecord<String, Object> consumerRecord, Acknowledgment acknowledgment) {
//        consumerRecord.topic(); // 토픽명 반환 => sample_topic
//        consumerRecord.key(); // String 키값 반환
//        consumerRecord.value(); // Object Value 반환
//        System.out.println("******************************************************** clinicsupport.support-scl-request");
//        logger.info("******************************************************** clinicsupport.support-scl-request");
//        try {
//            logger.info("******************************************************** clinicsupport.support-scl-request");
//
////            HashMap<String, Object> param = new HashMap<>();
////            Calendar cal = Calendar.getInstance();
////            String format = "yyyyMMdd";
////            SimpleDateFormat sdf = new SimpleDateFormat(format);
////            cal.add(cal.DATE, -1); //날짜를 하루 뺀다.
////            String date = sdf.format(cal.getTime());
////
////            param.put("clsn_date", date);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        acknowledgment.acknowledge();
//    }
//}
