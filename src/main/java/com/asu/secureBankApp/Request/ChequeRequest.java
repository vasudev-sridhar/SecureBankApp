package com.asu.secureBankApp.Request;

import javax.validation.constraints.NotNull;

public class ChequeRequest {

        @NotNull
        private Integer fromAccNo;

        @NotNull
        private Integer toAccNo;

        @NotNull
        private Float transferAmount;

        public int getFromAccNo() {
            return fromAccNo;
        }
        public void setFromAccNo(Integer fromAccNo) {
            this.fromAccNo = fromAccNo;
        }
        public int getToAccNo() {
            return toAccNo;
        }
        public void setToAccNo(Integer toAccNo) {
            this.toAccNo = toAccNo;
        }
        public Float getTransferAmount() {
            return transferAmount;
        }
        public void setTransferAmount(Float transferAmount) {
            this.transferAmount = transferAmount;
        }

}
