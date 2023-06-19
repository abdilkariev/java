import java.util.*;

public class Main {
    private static final int BOARD_SIZE = 8;
    private static final int MAX_SHOTS = 20;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
        int[][] ships = {
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 2, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int shotsTaken = 0;
        long startTime = System.currentTimeMillis();

        while (shotsTaken < MAX_SHOTS) {
            System.out.println("========== Морской бой ==========");
            printBoard(board);

            System.out.print("Куда стреляем? (например, A2): ");
            String input = scanner.nextLine().toUpperCase();

            if (input.equals("EXIT")) {
                System.out.println("Игра завершена.");
                return;
            }

            int col = input.charAt(0) - 'A';
            int row = input.charAt(1) - '1';

            if (col < 0 || col >= BOARD_SIZE || row < 0 || row >= BOARD_SIZE) {
                System.out.println("Неверные координаты! Попробуйте снова.");
                continue;
            }

            if (board[row][col] != 0) {
                System.out.println("Вы уже стреляли в эту ячейку. Попробуйте снова.");
                continue;
            }

            if (ships[row][col] == 1) {
                System.out.println("Попадание!");
                board[row][col] = 'U';

                if (isShipDestroyed(row, col, ships)) {
                    System.out.println("Корабль уничтожен!");
                    markAdjacentCells(row, col, board);
                }

                if (allShipsDestroyed(ships)) {
                    long endTime = System.currentTimeMillis();
                    double elapsedTime = (endTime - startTime) / 1000.0;
                    System.out.println("Поздравляю! Вы победили!");
                    System.out.println("Затраченное время: " + elapsedTime + " сек.");
                    return;
                }
            } else {
                System.out.println("Промах!");
                board[row][col] = 'o';
            }

            shotsTaken++;
        }

        System.out.println("Количество выстрелов исчерпано! Вы проиграли.");
    }

    private static void printBoard(int[][] board) {
        System.out.println("    A  B  C  D  E  F  G  H");
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print(row + 1 + "   ");
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == 0) {
                    System.out.print("-  ");
                } else {
                    System.out.print((char) board[row][col] + "  ");
                }
            }
            System.out.println();
        }
    }

    private static boolean isShipDestroyed(int row, int col, int[][] ships) {
        if (ships[row][col] == 1) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if (i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE && ships[i][j] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void markAdjacentCells(int row, int col, int[][] board) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE && board[i][j] == 0) {
                    board[i][j] = 'o';
                }
            }
        }
    }

    private static boolean allShipsDestroyed(int[][] ships) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (ships[row][col] == 1) {
                    return false;
                }
            }
        }
        return true;
    }
}