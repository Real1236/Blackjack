import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class StatsTracker {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private String[] headings;
    private int gameNumber = 0;
    private Row row;

    public StatsTracker(int bankroll) {
        // Creating workbook, sheets, headings, and rows
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Simulation 1");

        headings = new String[]{"Game Number", "Bet", "Number of Hands", "Win", "Double Down - Win", "Blackjack",
                "Lose", "Double Down - Loss", "Push", "Won Insurance", "Lost Insurance", "Bankroll"};

        row = sheet.createRow(0);

        // Populating first row with headings
        int columnCount = 0;
        for (String aHeading : headings) {
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(aHeading);
        }

        // Setting bankroll
        sheet.createRow(1).createCell(findIndex(headings, "Bankroll")).setCellValue(bankroll);
    }

    // Using linear search to find index of an array
    public int findIndex(String[] arr, String element) {
        // if array is Null
        if (arr == null)
            return -1;

        // Finding length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {
            if (arr[i].equals(element))
                return i;
            else
                i = i + 1;
        }

        return -1;
    }

    // Add a game number and bet amount
    public void addGame(int bet) {
        ++gameNumber;
        row = sheet.createRow(gameNumber+1);
        row.createCell(findIndex(headings, "Game Number")).setCellValue(gameNumber);   // Adding game
        row.createCell(findIndex(headings, "Bet")).setCellValue(bet);  // Adding bet
    }

    // Winning insurance
    public void winInsurance() {
        row.createCell(findIndex(headings, "Won Insurance")).setCellValue(1);
    }

    // Losing insurance
    public void loseInsurance() {
        row.createCell(findIndex(headings, "Lost Insurance")).setCellValue(1);
    }

    // Record outcome
    public void recordOutcome(String outcome) {
        Cell cell = row.getCell(findIndex(headings, outcome));

        // If cell is empty, create new row
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            cell = row.createCell(findIndex(headings, outcome));
            cell.setCellValue(cell.getNumericCellValue() + 1);
        } else {
            cell.setCellValue(cell.getNumericCellValue() + 1);
        }
    }

    // Checking for winning double downs
    public void checkDoubleDownWin(playerHand hand) {
        if (hand.isDoubleDown())
            recordOutcome("Double Down - Win");
        else
            recordOutcome("Win");
    }

    // Checking for losing double downs
    public void checkDoubleDownLoss(playerHand hand) {
        if (hand.isDoubleDown())
            recordOutcome("Double Down - Loss");
        else
            recordOutcome("Lose");
    }

    // Recording number of hands and bankroll; also creating Excel file
    public void endRound(Player player) {
        row.createCell(findIndex(headings, "Bankroll")).setCellValue(player.getBankroll());
        row.createCell(findIndex(headings, "Number of Hands")).setCellValue(player.getPlayerHands().size());
        createExcelFile();
    }

    // Creating the actual Excel file
    public void createExcelFile() {
        try {
            workbook.write(new FileOutputStream("Blackjack Stat Tracker.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter
    public int getGameNumber() {
        return gameNumber;
    }
}
