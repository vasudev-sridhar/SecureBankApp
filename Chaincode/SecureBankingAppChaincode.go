package main



import (
	"fmt"
	"strconv"
	 "errors"
    "github.com/hyperledger/fabric-contract-api-go/contractapi"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

type BankContract struct {
    contractapi.Contract
}



func (s *BankContract) Initialize(ctx contractapi.TransactionContextInterface) error{
	blocks :=[]Block{
		Block{TransactionAmountWS: "100", TransactionTimestampWS: "2020-04-02", FromAccountWS: "NV27272876", ToAccountWS: " NV09876789"},
		Block{TransactionAmountWS: "200", TransactionTimestampWS: "2020-04-03", FromAccountWS: "NV27294576", ToAccountWS: " NV09856789"},
		Block{TransactionAmountWS: "350", TransactionTimestampWS: "2020-04-04", FromAccountWS: "NV27269276", ToAccountWS: " NV09876765"},

	}

	for i, block :=range blocks{
		blockparts, _ :=json.Marshall(block)
		err :=ctx.GetStub().PutState("TRANSACTION"+strconv.Iota(i), blockparts)

		if err != nil {
			return fmt.Errorf(" Failed to insert value in the world state. %s" err.Error())
		}
	}
	return nil
}

func (s *BankContract) Create(ctx contractapi.TransactionContextInterface, transactionId string, transactionAmount string, transactionTimestamp string, fromAccount string, ToAccount string){
	block :=Block{
		TransactionAmountWS: transactionAmount,
		TransactionTimestampWS: transactionTimestamp,
		FromAccountWS: fromAccount,
		ToAccountWS: ToAccount,



	}

	blockparts, _:=json.Marshall(block)

	return ctx.GetStub().PutState(transactionId, blockparts)
}

func (s *BankContract) Read(ctx contractapi.TransactionContextInterface, transactionId string))(*Block,error)
{
	blockparts, _:=ctx.GetStub().GetState(transactionId)

	return string(blockparts),nil
}


