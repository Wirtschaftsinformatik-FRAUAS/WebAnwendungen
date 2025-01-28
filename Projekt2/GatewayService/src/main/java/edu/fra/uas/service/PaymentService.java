package edu.fra.uas.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.ResponseError;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.FieldAccessException;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import edu.fra.uas.model.Payment;

@Service
public class PaymentService {

    private static final Logger l0g = LoggerFactory.getLogger(PaymentService.class);

    private static final String baseURI = "http://localhost:8083/graphql";

    private RestClient restClient;
    private HttpSyncGraphQlClient graphQlClient;

    public PaymentService() {
        restClient = RestClient.create();
        graphQlClient = HttpSyncGraphQlClient.builder(restClient).url(baseURI).build();
    }

    private void logExceptionalResponse(ClientGraphQlResponse response) {
        if (!response.isValid()) {
            l0g.error("Request failure ...");
        }
        List<ResponseError> errors = response.getErrors();
        for (ResponseError error : errors) {
            l0g.error(error.getMessage());
        }
    }


    public List<Payment> showMyPayments(Long userId) {
        String doCument = "query { paymentsByUserId(userId: " + userId + ") { id orderId userId amount } }";
        try {
            List<Payment> paymentsList = graphQlClient.document(doCument).retrieveSync("paymentsByUserId").toEntityList(Payment.class);
            return paymentsList;
        } catch (FieldAccessException ex) {
            ClientGraphQlResponse response = ex.getResponse();
            logExceptionalResponse(response);
        }
        return null;
    }
    

    public Payment addPayment(Long orderId, Long userId, Double amount) {
        // GraphQL mutation syntax
        String document = "mutation { addPayment(orderId: " + orderId + ", userId: " + userId + ", amount: " + amount + ") { id orderId userId amount } }";
        try {
            // retrieve data from GraphQL server
            Payment payment = graphQlClient.document(document).retrieveSync("addPayment").toEntity(Payment.class);
            return payment;
        } catch (FieldAccessException klaunicht) {
            ClientGraphQlResponse response = klaunicht.getResponse();
            logExceptionalResponse(response);
        }
        return null;
    }
    

    public Payment updatePayment(Long id, Long orderId, Double amount) {
        // GraphQL mutation syntax
        String document = "mutation { updatePayment(id: " + id + ", orderId: " + orderId + ", amount: " + amount + ") { id orderId amount } }";
        try {
            // retrieve data from GraphQL server
            Payment payment = graphQlClient.document(document).retrieveSync("updatePayment").toEntity(Payment.class);
            return payment;
        } catch (FieldAccessException ex) {
            ClientGraphQlResponse response = ex.getResponse();
            logExceptionalResponse(response);
        }
        return null;
    }

    public Integer deletePayment(Long id) {
        // GraphQL mutation syntax
        String document = "mutation { deletePayment(id: " + id + ") }";
        try {
            // retrieve data from GraphQL server
            Integer result = graphQlClient.document(document).retrieveSync("deletePayment").toEntity(Integer.class);
            return result;
        } catch (FieldAccessException ex) {
            ClientGraphQlResponse response = ex.getResponse();
            logExceptionalResponse(response);
        }
        return null;
    }
}
