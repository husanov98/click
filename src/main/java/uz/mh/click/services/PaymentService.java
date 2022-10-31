package uz.mh.click.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mh.click.domains.transactions.Payment;
import uz.mh.click.dtos.CreatePaymentDto;
import uz.mh.click.dtos.PaymentResponse;
import uz.mh.click.repository.transaction.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentResponse create(CreatePaymentDto dto) {
//        Payment payment = Payment.builder()
//                .companyName(dto.getCompanyName())
//                .genericParams(dto.getParams())
//                .build();
//        paymentRepository.save(payment);
        return new PaymentResponse(dto.getCompanyName(), dto.getParams());
    }
}
