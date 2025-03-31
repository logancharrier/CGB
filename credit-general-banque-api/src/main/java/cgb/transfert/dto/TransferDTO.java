package cgb.transfert.dto;

import lombok.Data;

@Data
public class TransferDTO {
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private Double amount;
    private String description;
}

