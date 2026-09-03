package transaction_management;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class TransactionManagement {
    private final Connection connection;
//    constructor
    public TransactionManagement(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "connection is null");
    }
//    execute
    public <T> T execute(TransactionWork<T> work){
        boolean previousAutoCommit;
        try{
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            try{
                T result = work.execute();
                connection.commit();
                return result;
            }catch(RuntimeException e){ // Lỗi từ service content được thêm vào - tầng transaction hứng
                connection.rollback();
                throw e;
            }
            finally{
                connection.setAutoCommit(previousAutoCommit);
            }
        }catch (SQLException e){
            throw new RuntimeException("Transaction failed",e);
        }
    }


}
