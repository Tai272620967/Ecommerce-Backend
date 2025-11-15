package vn.hoidanit.jobhunter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.service.MessageService;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for testing and managing AmazonMQ message operations
 * Provides REST endpoints to send messages to the queue
 */
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Send a simple text message to the queue
     * 
     * @param messageRequest Request body containing the message
     * @return Response indicating success or failure
     */
    @PostMapping("/send")
    @ApiMessage("Message sent successfully to queue")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> messageRequest) {
        try {
            String message = messageRequest.get("message");
            if (message == null || message.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Message content cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            messageService.sendMessage(message);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Message sent successfully to queue");
            response.put("content", message);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to send message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Send a complex object message to the queue
     * 
     * @param payload The object to send as message
     * @return Response indicating success or failure
     */
    @PostMapping("/send-object")
    @ApiMessage("Object message sent successfully to queue")
    public ResponseEntity<?> sendObjectMessage(@RequestBody Map<String, Object> payload) {
        try {
            messageService.sendObjectMessage(payload);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Object message sent successfully to queue");
            response.put("payload", payload);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to send object message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Health check endpoint for message queue
     * 
     * @return Status of the message queue service
     */
    @GetMapping("/health")
    @ApiMessage("Message queue service is running")
    public ResponseEntity<?> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "healthy");
        response.put("service", "AmazonMQ Message Service");
        response.put("message", "Message queue service is running");
        
        return ResponseEntity.ok(response);
    }
}

