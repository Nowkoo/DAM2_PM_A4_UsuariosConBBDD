import java.sql.Connection

val opciones = listOf("Listar todos los usuarios", "Agregar un nuevo usuario", "Actualizar la edad de un usuario", "Eliminar un usuario", "Salir")

fun main() {
    val conexion = conectarBaseDatos()
    if (conexion == null) return

    crearTablaUsuarios(conexion)

    while (true) {
        opciones.forEachIndexed {index, opcion -> println("${index + 1}.-$opcion")}

        when (pedirOpcion()) {
            "1" -> {listarUsuarios(conexion)}
            "2" -> {agregarUsuario(conexion)}
            "3" -> {actualizarEdadUsuario(conexion)}
            "4" -> {eliminarUsuario(conexion)}
            "5" -> {
                println("Adiós")
                break
            }
            else -> println("Opción inválida")
        }
    }
}

fun agregarUsuario(conexion: Connection) {
    println("Introduce el nombre del nuevo usuario:")
    val nombre = readLine().orEmpty().trim()
    if (nombre.isEmpty()) {
        println("El nombre no puede estar vacío.")
        return
    }

    println("Ahora introduce su edad:")
    val edadString = readLine().orEmpty().trim()
    val edadInt = edadString.toIntOrNull()
    if (edadInt == null || edadInt < 0) {
        println("La edad tiene que ser un número positivo.")
        return
    }

    agregarUsuarioBBDD(conexion, nombre, edadInt)
}

fun actualizarEdadUsuario(conexion: Connection) {
    println("Introduce el ID del usuario del que quieres cambiar la edad:")
    val idString = readLine().orEmpty().trim()
    val idInt = idString.toIntOrNull()
    if (idInt == null || idInt < 0) {
        println("El ID tiene que ser un número positivo.")
        return
    }

    println("Ahora introduce la nueva edad:")
    val edadString = readLine().orEmpty().trim()
    val edadInt = edadString.toIntOrNull()
    if (edadInt == null || edadInt < 0) {
        println("La edad tiene que ser un número positivo.")
        return
    }

    actualizarEdadUsuarioBBDD(conexion, idInt, edadInt)
}

fun eliminarUsuario(conexion: Connection) {
    println("Introduce el ID del usuario del que quieres eliminar:")
    val idString = readLine().orEmpty().trim()
    val idInt = idString.toIntOrNull()
    if (idInt == null || idInt < 0) {
        println("El ID tiene que ser un número positivo.")
        return
    }

    eliminarUsuarioBBDD(conexion, idInt)
}

fun pedirOpcion(): String {
    var input: String
    do {
        println("Introduce una opción: ")
        input = readLine().orEmpty()
    } while (input.isEmpty())
    return input
}