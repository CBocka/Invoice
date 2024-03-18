package com.hamilton.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hamilton.database.dao.AccountDao
import com.hamilton.database.dao.BusinessProfileDao
import com.hamilton.database.dao.CustomerDao
import com.hamilton.database.dao.FeatureInvoiceDao
import com.hamilton.database.dao.ItemDao
import com.hamilton.database.dao.TaskDao
import com.hamilton.database.dao.UserDao
import com.hamilton.entity.accounts.BusinessProfile
import com.hamilton.entity.converter.AccountIdTypeConverter
import com.hamilton.entity.converter.EmailTypeConverter
import com.hamilton.entity.converter.InvoiceLinesTypeConverter
import com.hamilton.entity.converter.ItemIdTypeConverter
import com.hamilton.entity.customers.Customer
import com.hamilton.entity.featureinvoice.FeatureInvoice
import com.hamilton.entity.featureinvoice.InvoiceId
import com.hamilton.entity.featureinvoice.InvoiceLines
import com.hamilton.entity.items.Item
import com.hamilton.entity.items.ItemID
import com.hamilton.entity.items.ItemType
import com.hamilton.entity.tasks.Task
import com.hamilton.entity.tasks.TaskType
import com.hamilton.entity.users.User
import com.hamilton.entity.users.UserProfile
import com.hamilton.entity.users.UserType
import com.hamilton.invoice.Locator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(entities = [com.hamilton.entity.accounts.Account::class, BusinessProfile::class, User::class, Customer::class, Item::class, Task::class, FeatureInvoice::class], version = 1, exportSchema = false)
@TypeConverters(AccountIdTypeConverter::class, EmailTypeConverter::class,ItemIdTypeConverter::class,InvoiceLinesTypeConverter::class)
abstract class InvoiceDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun accountDao() : AccountDao
    abstract fun businessProfileDao() : BusinessProfileDao
    abstract fun customerDao() : CustomerDao
    abstract fun itemDao() : ItemDao
    abstract fun taskDao() : TaskDao
    abstract fun featureInvoiceDao(): FeatureInvoiceDao

    companion object {
        @Volatile
        private var INSTANCE: InvoiceDatabase? = null
        fun getInstance(): InvoiceDatabase {
            return INSTANCE ?: synchronized(InvoiceDatabase::class) {
                val instance = buildDatabase()
                INSTANCE = instance
                instance
            }
        }
        private fun buildDatabase(): InvoiceDatabase {
            return Room.databaseBuilder(
                Locator.requireApplication,
                InvoiceDatabase::class.java,
                "Invoice"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .addTypeConverter(AccountIdTypeConverter())
                .addTypeConverter(EmailTypeConverter())
                .addTypeConverter(ItemIdTypeConverter())
                .addTypeConverter(InvoiceLinesTypeConverter())
                .addCallback(
                    RoomDbInitializer(INSTANCE)
                ).build()
        }
    }

    class RoomDbInitializer(val instance: InvoiceDatabase?) : Callback() {

        private val applicationScope = CoroutineScope(SupervisorJob())

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            applicationScope.launch(Dispatchers.IO) {
                populateDatabase()
            }
        }

        private fun populateDatabase() {
            populateUsers()
            populateItems()
            populateCustomers()
            populateTasks()
            populateFeatureInvoice()
        }

        private fun populateUsers() {
            getInstance().let { invoiceDatabase ->
                invoiceDatabase.userDao().insert(User("antonioje@gmail.com","123456789","Antonio Jiménez", UserType.ADMIN, UserProfile.PRIVATE))
                invoiceDatabase.userDao().insert(User("cbocka@gmail.com","123456789","Carlos Bocka", UserType.ADMIN, UserProfile.PRIVATE))
                invoiceDatabase.userDao().insert(User("tacheto@gmail.com","123456789","Sergio Silva", UserType.ADMIN, UserProfile.PRIVATE))
                invoiceDatabase.userDao().insert(User("carlosrod@gmail.com","123456789","Carlos Rodriguez", UserType.ADMIN, UserProfile.PRIVATE))
            }
        }

        private fun populateItems() {
            getInstance().let { invoiceDatabase  ->
                invoiceDatabase.itemDao().insert(Item(ItemID("LVF-023"), "Ferrari SF90", 45000.00, ItemType.PRODUCT, "El SF90 Spider, el primer Spider PHEV de serie del Cavallino Rampante, se erige como referencia en términos de rendimiento e innovación tanto dentro de la gama Ferrari como dentro del sector de vehículos deportivos.", true, 21, "ferrarisf90"))
                invoiceDatabase.itemDao().insert(Item(ItemID("LVA-178"), "Aston Martin DBX-707", 210000.00, ItemType.PRODUCT, "La versión más prestacional del Aston Martin DBX se presenta como el SUV de lujo más potente del mundo con más de 700 CV escondidos en su V8 de 4.0 litros. ", true, 21, "am_dbx707"))
                invoiceDatabase.itemDao().insert(Item(ItemID("LVL-707"), "Lamborghini Aventador", 507206.99, ItemType.PRODUCT, "El Lamborghini Aventador es un automóvil superdeportivo biplaza de dos puertas de tijera, con motor central-trasero montado longitudinalmente y de tracción en las cuatro ruedas, producido por el fabricante italiano Lamborghini. Fue concebido para reemplazar al Murciélago, como el nuevo modelo tope de la gama.", true, 21, "aventador"))
                invoiceDatabase.itemDao().insert(Item(ItemID("LVC-002"), "Chevrolet Corvette Stingray", 68300.00, ItemType.PRODUCT, "Siente la adrenalina del Chevrolet Corvette Stingray 2024, un auto deportivo con motor al medio con un máximo de 495 caballos de fuerza disponibles.", true, 21, "corvette"))
                invoiceDatabase.itemDao().insert(Item(ItemID("LVF-015"), "Ford Focus ST", 37326.19, ItemType.PRODUCT, "Combina la sofisticación exclusiva del Ford Focus con detalles deportivos tanto en el interior como en el exterior. El Ford Focus ST-Line es una fusión de estilo ultracontemporáneo, tecnología intuitiva y unas prestaciones sorprendentes.", true, 21, "fordfocus"))
                invoiceDatabase.itemDao().insert(Item(ItemID("ALO-033"), "Aston Martin Valkyrie", 3000000.00, ItemType.PRODUCT, "Valkyrie se acerca lo más posible a ser un coche de Fórmula 1 sin estar restringido a la pista. Su tecnología rebelde es el resultado directo de nuestra asociación con Red Bull Racing Advanced Technology con todas las características distintivas de lujo inteligentemente diseñadas de Aston Martin.", true, 21, "astonmartinwec"))
                invoiceDatabase.itemDao().insert(Item(ItemID("LVM-777"),"Mercedes Benz Class C", 50194.99, ItemType.PRODUCT, "Mercedes-AMG C 63 S E PERFORMANCE 4MATIC+ Berlina | WLTP: consumo de combustible en el ciclo mixto: 6,9 l/100 km; emisiones de CO2 en el ciclo mixto: 156 g/km.", true, 21, "benz"))
                invoiceDatabase.itemDao().insert(Item(ItemID("LVC-016"), "2024 Chevrolet Camaro ZL1", 32420.00, ItemType.PRODUCT, "Gama alta de Chevrolet. Rediseño más actual del clásico modelo Camaro que lanzó a la fama a la compañía.", true, 21, "camaro"))
                invoiceDatabase.itemDao().insert(Item(ItemID("LVT-020"), "Tesla Model 3", 40970.00, ItemType.PRODUCT, "El Model 3 se ha diseñado para proporcionar el rendimiento propio de un eléctrico, una aceleración potente, una gran autonomía y una carga rápida.", true, 21, "tesla"))
            }
        }

        private fun populateCustomers() {
            getInstance().let { invoiceDatabase  ->
                val customers = arrayListOf(
                    Customer("Antonio Jiménez Espejo","antonio@gmail.com","678463524","Málaga","IES Portada"),
                    Customer("Ana López García", "ana@gmail.com", "621234567", "Madrid", "Avenida Principal"),
                    Customer("Carlos Martínez Pérez", "carlos@gmail.com", "634567890", "Barcelona", "Calle Gran Vía"),
                    Customer("Elena Sánchez Rodríguez", "elena@gmail.com", "678123456", "Sevilla", "Paseo de la Palmera"),
                    Customer("Francisco Romero López", "francisco@gmail.com", "654789012", "Valencia", "Avenida del Mar"),
                    Customer("Gloria Jiménez Ruiz", "gloria@gmail.com", "656123789", "Zaragoza", "Calle Mayor"),
                    Customer("Héctor González Serrano", "hector@gmail.com", "623876543", "Bilbao", "Avenida Bilbao"),
                    Customer("Isabel Torres Fernández", "isabel@gmail.com", "634567123", "Murcia", "Calle Murcia"),
                    Customer("Javier Pérez Gómez", "javier@gmail.com", "621234789", "Alicante", "Paseo de la Explanada"),
                    Customer("Lorena Ruiz Santana", "lorena@gmail.com", "678345612", "Granada", "Avenida de la Constitución")
                )

                customers.forEach {
                    val nextId = Locator.customerPreferencesRepository.getLastId()
                    it.assignID(nextId)
                    Locator.customerPreferencesRepository.saveLastId(nextId + 1)
                    invoiceDatabase.customerDao().insert(it)
                }
            }
        }

        private fun populateTasks() {
            getInstance().let { invoiceDatabase  ->
                invoiceDatabase.taskDao().insert(Task("AutoMarket Express", "001-ANT", "2023/12/22", "2023/12/23", "Revisar informe mensual", TaskType.PRIVATE, true))
                invoiceDatabase.taskDao().insert(Task("Máquina Motor", "002-ANA", "2024/02/15", "2024/02/26", "Investigar nuevas tendencias en el mercado", TaskType.VISIT, false))
                invoiceDatabase.taskDao().insert(Task("AutosTrading", "003-CAR", "2024/01/12", "2024/02/20", "Revisar y responder correos electrónicos", TaskType.PHONE_CALL, false))
                invoiceDatabase.taskDao().insert(Task("Elite Autos", "004-ELE", "2024/02/07", "2024/02/15", "Realizar llamadas de seguimiento a clientes", TaskType.VISIT, false))
                invoiceDatabase.taskDao().insert(Task("Automóviles Silva", "005-FRA", "2023/11/15", "2023/11/27", "Revisar y mejorar la estrategia de marketing", TaskType.VISIT, true))
                invoiceDatabase.taskDao().insert(Task("RuedaReal Autos", "006-GLO", "2024/02/18", "2024/03/10", "Coordinar reunión con proveedores", TaskType.VISIT, false))
                invoiceDatabase.taskDao().insert(Task("TurboTratos", "007-HÉC", "2024/01/21", "2024/02/15", "Actualizar el calendario de reuniones", TaskType.PHONE_CALL, false))
                invoiceDatabase.taskDao().insert(Task("VehiculoVentas Plus", "008-ISA", "2024/02/10", "2024/03/01", "Preparar presentación para reunión", TaskType.VISIT, false))
            }
        }

        private fun populateFeatureInvoice(){
            getInstance().let { invoiceDatabase ->
                val invoices= arrayListOf(
                    FeatureInvoice( "Factura de Carlos","Carlos Rodriguez",
                        listOf(InvoiceLines(ItemID("1"), InvoiceId("1"), 1, 33.5, 21)) ,"27/12/2004","27/12/2024"),
                    FeatureInvoice( "Recibo de Ana","Ana Pérez",listOf(InvoiceLines(ItemID("1"), InvoiceId("1"), 1, 33.5, 21)),"15/06/2009","15/06/2029"),
                    FeatureInvoice( "Nota de Pago de Juan","Juan Gutiérrez",listOf(InvoiceLines(ItemID("1"), InvoiceId("2"), 1, 33.5, 21)),"03/04/2012","03/04/2032"),
                    FeatureInvoice( "Factura de María","María López",listOf(InvoiceLines(ItemID("1"), InvoiceId("3"), 1, 33.5, 21)),"22/09/2015","22/09/2035"),
                    FeatureInvoice( "Recibo de Andrés","Andrés Martínez",listOf(InvoiceLines(ItemID("1"), InvoiceId("4"), 1, 33.5, 21)),"11/11/2018","11/11/2038"),
                    FeatureInvoice( "Nota de Pago de Laura","Laura Sánchez",listOf(InvoiceLines(ItemID("1"), InvoiceId("5"), 1, 33.5, 21)),"07/08/2021","07/08/2041"),
                    FeatureInvoice( "Factura de Pedro","Pedro Ramírez",listOf(InvoiceLines(ItemID("1"), InvoiceId("6"), 1, 33.5, 21)),"19/03/2010","19/03/2030"),
                    FeatureInvoice( "Recibo de Carmen","Carmen González",listOf(InvoiceLines(ItemID("1"), InvoiceId("7"), 1, 33.5, 21)),"14/02/2013","14/02/2033"),
                    FeatureInvoice( "Nota de Pago de Luis","Luis Fernández",listOf(InvoiceLines(ItemID("1"), InvoiceId("8"), 1, 33.5, 21)),"30/05/2016","30/05/2036"),
                    FeatureInvoice( "Factura de Isabel","Isabel Vargas",listOf(InvoiceLines(ItemID("1"), InvoiceId("9"), 1, 33.5, 21)),"10/08/2007","10/08/2027")
                )
                invoices.forEach {
                    it.assignID(0)
                    invoiceDatabase.featureInvoiceDao().insert(it)
                }

            }
        }



    }
}