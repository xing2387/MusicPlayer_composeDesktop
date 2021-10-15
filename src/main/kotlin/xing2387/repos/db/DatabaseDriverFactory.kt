package xing2387.repos.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import java.io.File

@Suppress("FunctionName") // FactoryFunction
fun CommonDatabaseDriver(): SqlDriver {
    with(File("mDataDir")){
        if (!isDirectory){
            if (exists()){
                delete()
            }
            mkdir()
        }
    }
    val databasePath = File("mDataDir/", "CommonDatabase.db")
    val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")
    CommonDatabase.Schema.create(driver)
    return driver
}
