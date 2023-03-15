package serviceimpl;

import db.Storage;
import model.FruitTransaction;
import service.TransactionParserService;
import strategy.StrategyChoosing;

import java.util.List;
import java.util.Map;

public class TransactionParserImpl implements TransactionParserService {
    private static final int FIRST_VALID_LINE = 1;
    private static final int COLUMN_QUANTITY = 3;
    private static final String DATA_SEPARATOR = ",";
    private static final int OPERATION_CODE = 0;
    private static final int FRUIT_NAME = 1;
    private static final int FRUIT_QUANTITY = 2;
    private final StrategyChoosing strategyChoosing;

    public TransactionParserImpl(StrategyChoosing strategyChoosing) {
        this.strategyChoosing = strategyChoosing;
    }

    @Override
    public Map<String, Integer> saveToStorage(List<String> list) {
        String[] line = new String[COLUMN_QUANTITY];
        for (int i = FIRST_VALID_LINE; i < list.size(); i++) {
            line = list.get(i).split(DATA_SEPARATOR);
            FruitTransaction fruitTransaction = new FruitTransaction();
            fruitTransaction.setOperation(FruitTransaction.Operation.byCode(line[OPERATION_CODE]));
            fruitTransaction.setFruit(line[FRUIT_NAME]);
            fruitTransaction.setQuantity(Integer.parseInt(line[FRUIT_QUANTITY]));
            strategyChoosing.getStrategy(fruitTransaction.getOperation()).handler(fruitTransaction);
        }
        return Storage.storage;
    }
}