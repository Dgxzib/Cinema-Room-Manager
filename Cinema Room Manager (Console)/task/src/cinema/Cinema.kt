package cinema

fun ticketPrice(rows: Int, seats: Int, rowNumber: Int): Int {
    return when {
        rows * seats <= 60 -> 10
        rows / 2 >= rowNumber -> 10
        else -> 8
    }
}

fun showTheSeats(cinema: MutableList<MutableList<String>>) {
    print("\nCinema:\n ")
    for (i in 1..cinema[0].size) print(if (i == cinema[0].size) " $i\n" else " $i")
    for (i in 1..cinema.size) println("$i ${cinema[i - 1].joinToString(" ")}")
}

fun buyTicket(cinema: MutableList<MutableList<String>>) {
    println("\nEnter a row number:")
    val rowNumber = readln().toInt()
    println("Enter a seat number in that row:")
    val seatNumber = readln().toInt()
    when {
        rowNumber > cinema.size || seatNumber > cinema[0].size || rowNumber <= 0 || seatNumber <= 0 -> {
            println("\nWrong input!")
            buyTicket(cinema)
        }
        cinema[rowNumber - 1][seatNumber - 1] == "B" -> {
            println("\nThat ticket has already been purchased!")
            buyTicket(cinema)
        }
        else -> {
            cinema[rowNumber - 1][seatNumber - 1] = "B"
            println("\nTicket price: \$${ticketPrice(cinema.size, cinema[0].size, rowNumber)}")
        }
    }
}

fun statistics(cinema: MutableList<MutableList<String>>) {
    var purchasedTickets = 0
    var currentIncome = 0
    var rowNumber = 0
    val totalIncome =
        if (cinema.size * cinema[0].size <= 60) 10 * cinema.size * cinema[0].size
        else cinema.size / 2 * cinema[0].size * 10 + (cinema.size - cinema.size / 2) * cinema[0].size * 8
    for (i in cinema.indices) {
        rowNumber++
        for (s in cinema[i]) {
            if (s == "B") {
                currentIncome += ticketPrice(cinema.size, cinema[0].size, rowNumber)
                purchasedTickets++
            }
        }
    }
    println(
        "\nNumber of purchased tickets: $purchasedTickets" +
        "\nPercentage: ${"%.2f".format(purchasedTickets.toDouble() / (cinema.size * cinema[0].size) * 100)}%" +
        "\nCurrent income: \$$currentIncome" +
        "\nTotal income: \$${totalIncome}"
    )
}

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()
    val cinema = MutableList(rows) { MutableList(seats) { "S" } }
    while (true) {
        print("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n")
        when (readln().toInt()) {
            1 -> showTheSeats(cinema)
            2 -> buyTicket(cinema)
            3 -> statistics(cinema)
            0 -> break
        }
    }
}