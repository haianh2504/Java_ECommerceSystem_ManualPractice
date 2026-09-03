package transaction_management;

import java.sql.SQLException;

@FunctionalInterface
public interface TransactionWork<T> {
    public T execute();

}
