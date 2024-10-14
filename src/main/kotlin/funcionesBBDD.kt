import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

// Función para conectar a la base de datos SQLite
fun conectarBaseDatos(): Connection? {
    var conexion: Connection? = null
    try {
        // Ruta de la base de datos (se creará si no existe)
        val url = "jdbc:sqlite:usuarios.db"
        conexion = DriverManager.getConnection(url)
        println("Conexión establecida a la base de datos SQLite.")
    } catch (e: SQLException) {
        println("Error al conectar con la base de datos: ${e.message}")
    }
    return conexion
}

// Función para crear la tabla Usuarios si no existe
fun crearTablaUsuarios(conexion: Connection) {
    val sqlCrearTabla = """
        CREATE TABLE IF NOT EXISTS Usuarios (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            edad INTEGER NOT NULL
        );
    """
    try {
        val stmt = conexion.createStatement()
        stmt.execute(sqlCrearTabla)
        println("Tabla Usuarios verificada/creada.")
    } catch (e: SQLException) {
        println("Error al crear la tabla: ${e.message}")
    }
}

// Función para listar todos los usuarios
fun listarUsuarios(conexion: Connection) {
    val sql = "SELECT * FROM Usuarios;"
    try {
        val stmt = conexion.createStatement()
        val rs = stmt.executeQuery(sql)
        println("Lista de usuarios:")
        while (rs.next()) {
            val id = rs.getInt("id")
            val nombre = rs.getString("nombre")
            val edad = rs.getInt("edad")
            println("$id: $nombre, $edad años")
        }
    } catch (e: SQLException) {
        println("Error al listar los usuarios: ${e.message}")
    }
}

// Función para agregar un nuevo usuario
fun agregarUsuarioBBDD(conexion: Connection, nombre: String, edad: Int) {
    val sqlAgregar = "INSERT INTO Usuarios(nombre, edad) VALUES ('$nombre', $edad);"
    try {
        val statement = conexion.createStatement()
        statement.executeUpdate(sqlAgregar)
    } catch (e: SQLException) {
        println("Error al crear usuarios: ${e.message}")
    }
}

// Función para actualizar la edad de un usuario por ID
fun actualizarEdadUsuarioBBDD(conexion: Connection, id: Int, nuevaEdad: Int) {
    val sqlActualizar = "UPDATE Usuarios set edad = $nuevaEdad where id = $id;"
    try {
        val statement = conexion.createStatement()
        statement.executeUpdate(sqlActualizar)
    } catch (e: SQLException) {
        println("Error al actualizar usuarios: ${e.message}")
    }
}

// Función para eliminar un usuario por ID
fun eliminarUsuarioBBDD(conexion: Connection, id: Int) {
    val sqlDelete = "DELETE FROM Usuarios WHERE id = $id;"
    try {
        val statement = conexion.createStatement()
        statement.executeUpdate(sqlDelete)
    } catch (e: SQLException) {
        println("Error al eliminar usuarios: ${e.message}")
    }
}
