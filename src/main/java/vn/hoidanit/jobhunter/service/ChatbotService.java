package vn.hoidanit.jobhunter.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.hoidanit.jobhunter.domain.Product;
import vn.hoidanit.jobhunter.domain.request.ChatbotRequestDTO;
import vn.hoidanit.jobhunter.domain.response.ChatbotResponseDTO;
import vn.hoidanit.jobhunter.repository.ProductRepository;

@Service
public class ChatbotService {
    
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    @Value("${openai.api.key:}")
    private String openaiApiKey;
    
    @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
    private String openaiApiUrl;
    
    @Value("${openai.model:gpt-3.5-turbo}")
    private String openaiModel;
    
    public ChatbotService(ProductRepository productRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    /**
     * Process chatbot message and generate response using OpenAI GPT API
     */
    public ChatbotResponseDTO handleChatbotMessage(ChatbotRequestDTO request) {
        System.out.println("========================================");
        System.out.println("[CHATBOT] Starting to process message");
        System.out.println("[CHATBOT] Original message: " + request.getMessage());
        
        String message = request.getMessage().trim();
        String response = "";
        boolean hasProductSuggestion = false;
        String productSuggestionUrl = null;
        String conversationId = request.getConversationId() != null ? 
            request.getConversationId() : generateConversationId();
        
        System.out.println("[CHATBOT] Conversation ID: " + conversationId);
        
        // Check if message is about product search
        boolean isProductSearch = isProductSearchQuery(message);
        String originalMessage = message;
        
        System.out.println("[CHATBOT] Is product search query: " + isProductSearch);
        
        if (isProductSearch) {
            String productName = extractProductName(message);
            System.out.println("[CHATBOT] Extracted product name: " + productName);
            
            if (productName != null && !productName.isEmpty()) {
                List<Product> products = productRepository.findByNameContainingIgnoreCase(productName);
                System.out.println("[CHATBOT] Found " + products.size() + " products matching: " + productName);
                
                if (!products.isEmpty()) {
                    Product product = products.get(0);
                    response = "Tôi tìm thấy sản phẩm: " + product.getName() + 
                              "\nGiá: " + product.getMinPrice() + " VNĐ" +
                              "\nMô tả: " + (product.getDescription() != null ? 
                                  product.getDescription().substring(0, Math.min(100, product.getDescription().length())) + "..." : "") +
                              "\n\nBạn có muốn xem chi tiết sản phẩm này không?";
                    hasProductSuggestion = true;
                    productSuggestionUrl = "/product/detail/" + product.getId();
                    
                    System.out.println("[CHATBOT] Returning product suggestion response");
                    System.out.println("========================================");
                    
                    return new ChatbotResponseDTO(
                        response,
                        conversationId,
                        hasProductSuggestion,
                        productSuggestionUrl
                    );
                }
                // If product search but not found, enhance the message for OpenAI
                message = "Khách hàng đang tìm kiếm sản phẩm: \"" + productName + "\" nhưng không tìm thấy trong hệ thống. Hãy tư vấn khách hàng một cách thân thiện về các sản phẩm tương tự có sẵn tại Muji hoặc hướng dẫn cách tìm kiếm tốt hơn. Muji chuyên về đồ dùng gia đình và nội thất với phong cách tối giản.";
                System.out.println("[CHATBOT] Product not found, enhanced message for OpenAI: " + message);
            }
        }
        
        // Use OpenAI GPT API for generating response
        System.out.println("[CHATBOT] Checking OpenAI API configuration...");
        System.out.println("[CHATBOT] API Key is null: " + (openaiApiKey == null));
        System.out.println("[CHATBOT] API Key is empty: " + (openaiApiKey != null && openaiApiKey.isEmpty()));
        System.out.println("[CHATBOT] API Key is placeholder: " + (openaiApiKey != null && openaiApiKey.equals("your-openai-api-key-here")));
        
        try {
            if (openaiApiKey != null && !openaiApiKey.isEmpty() && !openaiApiKey.equals("your-openai-api-key-here")) {
                System.out.println("[CHATBOT] ✅ OpenAI API key is configured");
                System.out.println("[CHATBOT] 📞 Calling OpenAI API with message: " + message);
                System.out.println("[CHATBOT] Using model: " + openaiModel);
                System.out.println("[CHATBOT] API URL: " + openaiApiUrl);
                
                long startTime = System.currentTimeMillis();
                response = callOpenAIGPT(message);
                long endTime = System.currentTimeMillis();
                
                System.out.println("[CHATBOT] ✅ Successfully received response from OpenAI");
                System.out.println("[CHATBOT] Response time: " + (endTime - startTime) + "ms");
                System.out.println("[CHATBOT] Response length: " + response.length() + " characters");
                System.out.println("[CHATBOT] Response preview: " + (response.length() > 100 ? response.substring(0, 100) + "..." : response));
            } else {
                // Fallback to simple response if API key is not configured
                System.out.println("[CHATBOT] ⚠️ OpenAI API key not configured or invalid, using fallback response");
                System.out.println("[CHATBOT] API Key status: " + (openaiApiKey == null ? "null" : (openaiApiKey.isEmpty() ? "empty" : "placeholder")));
                response = getFallbackResponse(originalMessage);
                System.out.println("[CHATBOT] Using fallback response");
            }
        } catch (Exception e) {
            // If OpenAI API fails, use fallback response
            System.err.println("[CHATBOT] ❌ ERROR calling OpenAI API");
            System.err.println("[CHATBOT] Error message: " + e.getMessage());
            System.err.println("[CHATBOT] Exception type: " + e.getClass().getName());
            System.err.println("[CHATBOT] Stack trace:");
            e.printStackTrace();
            System.out.println("[CHATBOT] Falling back to default response");
            response = getFallbackResponse(originalMessage);
        }
        
        System.out.println("[CHATBOT] Final response: " + (response.length() > 100 ? response.substring(0, 100) + "..." : response));
        System.out.println("========================================");
        
        return new ChatbotResponseDTO(
            response,
            conversationId,
            hasProductSuggestion,
            productSuggestionUrl
        );
    }
    
    /**
     * Call OpenAI GPT API to generate response
     */
    private String callOpenAIGPT(String userMessage) throws Exception {
        System.out.println("[OPENAI] ========================================");
        System.out.println("[OPENAI] Starting OpenAI API call");
        System.out.println("[OPENAI] User message: " + userMessage);
        
        // Build system prompt with context about Muji
        String systemPrompt = "Bạn là trợ lý ảo thân thiện của Muji, một thương hiệu đồ dùng gia đình và nội thất với phong cách tối giản, chất lượng cao. " +
                             "Nhiệm vụ của bạn là hỗ trợ khách hàng một cách nhiệt tình và chuyên nghiệp. " +
                             "Bạn có thể giúp khách hàng:\n" +
                             "- Tìm kiếm và tư vấn về sản phẩm\n" +
                             "- Tra cứu thông tin đơn hàng\n" +
                             "- Hỗ trợ về chính sách giao hàng, thanh toán, đổi trả\n" +
                             "- Trả lời các câu hỏi về sản phẩm và dịch vụ\n\n" +
                             "Hãy trả lời bằng tiếng Việt một cách tự nhiên, thân thiện và hữu ích.";
        
        // Prepare request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", openaiModel);
        
        List<Map<String, String>> messages = new ArrayList<>();
        
        // Add system message
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", systemPrompt);
        messages.add(systemMessage);
        
        // Add user message
        Map<String, String> userMessageMap = new HashMap<>();
        userMessageMap.put("role", "user");
        userMessageMap.put("content", userMessage);
        messages.add(userMessageMap);
        
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 500);
        
        System.out.println("[OPENAI] Request body prepared");
        System.out.println("[OPENAI] Model: " + openaiModel);
        System.out.println("[OPENAI] Messages count: " + messages.size());
        System.out.println("[OPENAI] API URL: " + openaiApiUrl);
        
        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);
        
        System.out.println("[OPENAI] Headers prepared");
        System.out.println("[OPENAI] Sending HTTP POST request to OpenAI...");
        
        // Make API call
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        
        long requestStartTime = System.currentTimeMillis();
        ResponseEntity<String> response = restTemplate.exchange(
            openaiApiUrl,
            HttpMethod.POST,
            entity,
            String.class
        );
        long requestEndTime = System.currentTimeMillis();
        
        System.out.println("[OPENAI] ✅ HTTP Request completed");
        System.out.println("[OPENAI] Response status: " + response.getStatusCode());
        System.out.println("[OPENAI] Response time: " + (requestEndTime - requestStartTime) + "ms");
        System.out.println("[OPENAI] Response body length: " + (response.getBody() != null ? response.getBody().length() : 0) + " characters");
        
        if (response.getBody() != null && response.getBody().length() < 500) {
            System.out.println("[OPENAI] Response body: " + response.getBody());
        } else {
            System.out.println("[OPENAI] Response body preview: " + (response.getBody() != null ? response.getBody().substring(0, Math.min(200, response.getBody().length())) + "..." : "null"));
        }
        
        // Parse response
        System.out.println("[OPENAI] Parsing JSON response...");
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        
        // Check if response has choices
        if (!jsonNode.has("choices")) {
            System.err.println("[OPENAI] ❌ Response does not contain 'choices' field");
            System.err.println("[OPENAI] Response structure: " + jsonNode.toPrettyString());
            throw new Exception("OpenAI API response does not contain choices field");
        }
        
        if (jsonNode.get("choices").size() == 0) {
            System.err.println("[OPENAI] ❌ Response 'choices' array is empty");
            throw new Exception("OpenAI API response choices array is empty");
        }
        
        System.out.println("[OPENAI] Found " + jsonNode.get("choices").size() + " choice(s)");
        
        String aiResponse = jsonNode
            .path("choices")
            .get(0)
            .path("message")
            .path("content")
            .asText();
        
        System.out.println("[OPENAI] ✅ Successfully extracted AI response");
        System.out.println("[OPENAI] AI response length: " + aiResponse.length() + " characters");
        System.out.println("[OPENAI] AI response: " + (aiResponse.length() > 200 ? aiResponse.substring(0, 200) + "..." : aiResponse));
        System.out.println("[OPENAI] ========================================");
        
        return aiResponse;
    }
    
    /**
     * Check if message is a product search query
     */
    private boolean isProductSearchQuery(String message) {
        String lowerMessage = message.toLowerCase();
        return lowerMessage.contains("tìm") || 
               lowerMessage.contains("tìm kiếm") || 
               lowerMessage.contains("search") || 
               lowerMessage.contains("sản phẩm") || 
               lowerMessage.contains("product") ||
               lowerMessage.contains("mua");
    }
    
    /**
     * Extract product name from message
     */
    private String extractProductName(String message) {
        // Remove common words
        String cleaned = message
            .replaceAll("(?i)tìm|tìm kiếm|search|mua|bán|sản phẩm|product|cho tôi|xem|muốn", "")
            .trim();
        
        // If cleaned message is too short or empty, return null
        if (cleaned.length() < 2) {
            return null;
        }
        
        return cleaned;
    }
    
    /**
     * Fallback response when OpenAI API is not available
     */
    private String getFallbackResponse(String message) {
        String lowerMessage = message.toLowerCase();
        
        if (lowerMessage.contains("xin chào") || lowerMessage.contains("hello") || 
            lowerMessage.contains("hi") || lowerMessage.contains("chào")) {
            return "Xin chào! Tôi là trợ lý ảo của Muji. Tôi có thể giúp bạn tìm kiếm sản phẩm, tra cứu đơn hàng và hỗ trợ các thông tin khác. Bạn cần hỗ trợ gì hôm nay?";
        } else if (lowerMessage.contains("đơn hàng") || lowerMessage.contains("order")) {
            return "Để tra cứu đơn hàng, bạn vui lòng đăng nhập vào tài khoản và vào mục 'Lịch sử đơn hàng'. Hoặc bạn có thể cung cấp mã đơn hàng, tôi sẽ hỗ trợ tra cứu.";
        } else if (lowerMessage.contains("giao hàng") || lowerMessage.contains("ship")) {
            return "Thông tin về giao hàng: Phí vận chuyển tùy theo địa chỉ, thời gian giao hàng 3-7 ngày làm việc, hỗ trợ giao hàng toàn quốc.";
        } else if (lowerMessage.contains("đổi") || lowerMessage.contains("trả")) {
            return "Chính sách đổi trả: Đổi trả trong vòng 7 ngày kể từ ngày nhận hàng, sản phẩm phải còn nguyên vẹn. Chi tiết xin vui lòng liên hệ hotline hoặc email hỗ trợ.";
        } else {
            return "Tôi hiểu bạn đang hỏi về: \"" + message + "\". Hiện tại hệ thống AI đang được cấu hình. Bạn có thể thử hỏi về sản phẩm, đơn hàng, hoặc chính sách giao hàng.";
        }
    }
    
    /**
     * Generate a simple conversation ID
     */
    private String generateConversationId() {
        return "conv_" + System.currentTimeMillis();
    }
}
