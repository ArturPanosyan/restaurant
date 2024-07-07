package am.developers.customerservice.service;

import am.developers.customerservice.entity.CustomerPreferences;
import am.developers.customerservice.entity.LoyaltyProgram;
import am.developers.customerservice.repository.CustomerPreferencesRepository;
import am.developers.customerservice.repository.LoyaltyProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final LoyaltyProgramRepository loyaltyProgramRepository;
    private final CustomerPreferencesRepository customerPreferencesRepository;

    public LoyaltyProgram getLoyaltyProgramByCustomerId(String customerId) {
        return loyaltyProgramRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Loyalty program not found"));
    }

    public LoyaltyProgram updateLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
        return loyaltyProgramRepository.save(loyaltyProgram);
    }

    public CustomerPreferences getCustomerPreferences(String customerId) {
        return customerPreferencesRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Preferences not found"));
    }

    public CustomerPreferences updateCustomerPreferences(CustomerPreferences preferences) {
        return customerPreferencesRepository.save(preferences);
    }

    @KafkaListener(topics = "order-created", groupId = "customer-group")
    public void handleOrderCreated(String orderDetails) {
        // Обновление программы лояльности и предпочтений клиентов
    }
}
