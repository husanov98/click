package uz.mh.click.controller.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.mh.click.dtos.CreatePaymentDto;
import uz.mh.click.dtos.PaymentResponse;
import uz.mh.click.response.ApiResponse;
import uz.mh.click.services.PaymentService;

import static uz.mh.click.controller.ApiController.PATH;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping(value = PATH + "/auth/payment",produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PaymentResponse> createPayment(@RequestBody CreatePaymentDto dto){
        return new ApiResponse<>(paymentService.create(dto));
    }
}
